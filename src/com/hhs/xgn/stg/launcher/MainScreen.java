package com.hhs.xgn.stg.launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.type.Player;
import com.hhs.xgn.stg.game.EnemySelfAim;
import com.hhs.xgn.stg.game.ItemPower;
import com.hhs.xgn.stg.type.Entity;
import com.hhs.xgn.stg.type.EntityEnemy;
import com.hhs.xgn.stg.type.EntityEnemyBullet;
import com.hhs.xgn.stg.type.EntityItem;
import com.hhs.xgn.stg.type.EntityPlayerBullet;

public class MainScreen implements Screen {

	public GameMain gm;
	
	public AssetManager am;

	public ArrayList<EntityEnemy> groupEnemy=new ArrayList<>();
	public ArrayList<EntityPlayerBullet> groupPlayerBullet=new ArrayList<>();
	public ArrayList<EntityEnemyBullet> groupEnemyBullet=new ArrayList<>();
	public ArrayList<Player> groupPlayer=new ArrayList<>();
	public ArrayList<EntityItem> groupItem=new ArrayList<>();
	
	public Player p;
	public SpriteBatch sb;
	
	public Stage ui;
	public Label everything;
	public Group instant;
	
	public int renderMode;
	public Stage escMenu;
	public Label escEv;
	
	public void addPlayerBullet(float x,float y) {
		EntityPlayerBullet epb=new EntityPlayerBullet(this);
		epb.x=x;
		epb.y=y;
		groupPlayerBullet.add(epb);
	}

	public void addEnemyBullet(EntityEnemyBullet eeb){
		groupEnemyBullet.add(eeb);
	}
	
	public void addEnemy(EntityEnemy ee) {
		groupEnemy.add(ee);
	}
	

	public void addItem(EntityItem item) {
		groupItem.add(item);
	}

	
	String[] resources=new String[]{
			"bullet.png",
			"enemy.png",
			"player1.png",
			"player2.png",
			"player3.png",
			"playerbullet.png",
			"heart.png",
			"power.bmp",
			"pure.png",
			"point.bmp"
	};
	
	public MainScreen(GameMain gm){
		this.gm=gm;
		
		am=new AssetManager();
		for(String res:resources){
			am.load(res,Texture.class);
		}
		am.finishLoading();
		
		sb=new SpriteBatch();
		
		p=new Player(this);
		
		//Render UI
		ui=new Stage();
		
		everything=VU.createLabel("");
		everything.setPosition(0,0,Align.bottomLeft);
		everything.getStyle().fontColor=new Color(1,0,0,0.5f);
		everything.setFontScale(0.5f);
		
		ui.addActor(everything);
		
		instant=new Group();
		ui.addActor(instant);
		
		//Esc Menu
		escMenu=new Stage();
		escEv=VU.createLabel("Game is paused!\nPress Esc to continue!");
		escEv.getStyle().fontColor=Color.GREEN;
		
		escEv.setFontScale(0.5f);
		escEv.setPosition(0, 100);
		escMenu.addActor(escEv);
		
		groupPlayer.add(p);
	}
	
	@Override
	public void dispose() {
		VU.disposeAll(sb,am,ui);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	int frameC;
	
	@Override
	public void render(float arg0) {
		
		frameC++;
		
		if(p.deadTime!=0){
			VU.clear(0, 0, 0, 1);
			p.deadTime--;
			
			for(EntityEnemyBullet eeb:groupEnemyBullet){
				eeb.onKill();
			}
			
			groupEnemyBullet.clear();
			for(EntityEnemy ee:groupEnemy){
				ee.onKill();
			}
			
			groupEnemy.clear();
			
		}else{
			VU.clear(1,1,1,1);	
		}
		
		//Check Esc key
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			if(renderMode==1){
				renderMode=0;
			}else{
				renderMode=1;
			}
		}
		
		//First frame everything
		if(renderMode==0){
			for(EntityEnemy ee:groupEnemy){
				ee.onFrame();
			}
			for(EntityPlayerBullet epb:groupPlayerBullet){
				epb.onFrame();
			}
			for(EntityEnemyBullet eeb:groupEnemyBullet){
				eeb.onFrame();
			}
			for(EntityItem eeb:groupItem){
				eeb.onFrame();
			}
			p.onFrame();
		}
		
		//Then check collision
		checkCol(groupEnemy,groupPlayerBullet);
		checkCol(groupEnemyBullet,groupPlayer);
		checkCol(groupEnemy,groupPlayer);
		checkCol(groupItem,groupPlayer);
		
		//check dead
		checkDead(groupEnemy);
		checkDead(groupPlayerBullet);
		checkDead(groupEnemyBullet);
		checkDead(groupItem);
		
		//UI Component Update
		String disText="HP:"+p.hp+"\nSpell:"+p.spell+"\n"+p.atk+"P"+p.def+"D\nGraze:"+p.graze+"\nPoint:"+p.point;
		if(p.y>VU.height/3*2){
			disText+="\n-=Auto Collect On=-";
		}
		everything.setText(disText);
		everything.setPosition(0,90,Align.bottomLeft);
		
		//Graze
		for(EntityEnemyBullet eeb:groupEnemyBullet){
			if(!eeb.grazed && getDist(eeb, p)<=p.getCollision()*2){
				eeb.grazed=true;
				
				p.graze++;
				p.point+=20;
				
				Label hplost=VU.createLabel("graze");
				hplost.getStyle().fontColor=Color.GRAY;
				hplost.setFontScale(0.5f);
				hplost.addAction(Actions.sequence(Actions.parallel(Actions.moveBy(0, 100,5),Actions.alpha(0,5)),Actions.removeActor()));
				hplost.setPosition(eeb.x, eeb.y,Align.center);
				instant.addActor(hplost); 
			}
		}
		//Then draw
		sb.begin();
		
		checkRen(groupEnemy);
		checkRen(groupPlayerBullet);
		
		sb.draw(am.get(p.texture,Texture.class), p.x-p.sx/2, p.y-p.sy/2,p.sx,p.sy);
		
		checkRen(groupEnemyBullet);
		checkRen(groupItem);
		
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
			sb.draw(am.get("heart.png",Texture.class), p.x-p.getCollision()/2, p.y-p.getCollision()/2,p.getCollision(),p.getCollision());
			for(Entity e:groupEnemy){
				sb.draw(am.get("heart.png",Texture.class), e.x-e.getCollision()/2, e.y-e.getCollision()/2,e.getCollision(),e.getCollision());
			}
		}
		
		sb.end();
		
		ui.act();
		ui.draw();
		
		if(renderMode==1){
			//Esc
			sb.begin();
			sb.setColor(0.78f,0.78f,0.78f,0.78f);
			sb.draw(am.get("pure.png",Texture.class), 0, 0,VU.width,VU.height);
			sb.setColor(Color.WHITE);
			sb.end();
			escMenu.act();
			escMenu.draw();
		}
		
		//Process Level Information
		if(frameC>=120){
			EnemySelfAim esa=new EnemySelfAim(this, p.x, VU.height+100);
			addEnemy(esa);
			frameC-=120;
		}
	}

	public void checkDead(ArrayList<? extends Entity> a){
		for(int i=0;i<a.size();i++){
			if(a.get(i).dead){
				a.remove(i);
				i--;
			}
		}
	}
	
	public void checkRen(ArrayList<? extends Entity> a){
		for(Entity p:a){
			sb.draw(am.get(p.texture,Texture.class), p.x-p.sx/2, p.y-p.sy/2,p.sx,p.sy);
		}
	}
	
	public void checkCol(ArrayList<? extends Entity> a,ArrayList<? extends Entity> b){
		for(Entity x:a){
			for(Entity y:b){
				if(x.dead || y.dead){
					continue;
				}
				
				if(getDist(x,y)<=x.getCollision()+y.getCollision()){
					x.onHit(y);
					y.onHit(x);
				}
			}
		}
	}
	
	public double getDist(Entity x, Entity y) {
		// TODO Auto-generated method stub
		return Math.sqrt(Math.pow(y.x-x.x, 2)+Math.pow(y.y-x.y,2));
	}
	
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void killEnemy(EntityEnemy entityEnemy) {
		for(EntityEnemy e:groupEnemy){
			if(e==entityEnemy){
				e.onKill();
				break;
			}
		}
		
		groupEnemy.remove(entityEnemy);
	}

	
}
