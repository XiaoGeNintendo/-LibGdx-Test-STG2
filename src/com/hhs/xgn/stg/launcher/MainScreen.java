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
import com.hhs.xgn.stg.game.TestNonSpellCard;
import com.hhs.xgn.stg.game.TestSpellCard;
import com.hhs.xgn.stg.type.Boss;
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
	
	public Boss boss;
	public boolean renderBoss;
	
	public Player p;
	public SpriteBatch sb;
	
	public Stage ui;
	public Label everything;
	public Group instant;
	public Label bossName;
	public Label spellScroll;
	
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
			"point.bmp",
			"reimu.png",
			"background.png"
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
		
		bossName=VU.createLabel("Stage Test");
		bossName.setPosition(0, VU.height,Align.topLeft);
		bossName.setFontScale(0.5f);
		
		spellScroll=VU.createLabel("");
		spellScroll.setPosition(0,VU.height-75);
		spellScroll.getStyle().fontColor=new Color(0,0,1,0.8f);
		spellScroll.setFontScale(0.4f);
		
		ui.addActor(bossName);
		ui.addActor(everything);
		ui.addActor(spellScroll);
		
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
	
	int backgroundC;
	
	@Override
	public void render(float arg0) {
		
		frameC++;
		
		if(renderMode==0){
			backgroundC++;
		}
		
		if(p.deadTime!=0){
			VU.clear(0, 0, 0, 1);
			p.deadTime--;
			
			clearBullet();
			if(renderBoss && !boss.isAppearing()){
				boss.currentHp-=p.atk;
				
			}
		}else{
			VU.clear(1,1,1,1);	
		}
		
		//render background
		sb.begin();
		sb.draw(am.get("background.png",Texture.class), 0,-backgroundC%VU.height,VU.width,VU.height);
		sb.draw(am.get("background.png",Texture.class), 0,-backgroundC%VU.height-VU.height,VU.width,VU.height);
		sb.draw(am.get("background.png",Texture.class), 0,-backgroundC%VU.height+VU.height,VU.width,VU.height);
		sb.end();
		
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
			
			if(renderBoss){
				boss.onFrame();
			}
		}
		
		//Then check collision
		checkCol(groupEnemy,groupPlayerBullet);
		checkCol(groupEnemyBullet,groupPlayer);
		checkCol(groupEnemy,groupPlayer);
		checkCol(groupItem,groupPlayer);
		
		if(renderBoss && !boss.isAppearing()){
			for(EntityPlayerBullet x:groupPlayerBullet){
				if(x.dead || boss.dead){
					continue;
				}
				
				if(getDist(x,boss)<=x.getCollision()+boss.getCollision()){
					x.onHit(boss);
					boss.onHit(x);
				}
			}
		}
		
		//check dead
		checkDead(groupEnemy);
		checkDead(groupPlayerBullet);
		checkDead(groupEnemyBullet);
		checkDead(groupItem);
		
		if(renderBoss && boss.dead){
			renderBoss=false;
			boss=null;
		}
		
		//UI Component Update
		String disText="HP:"+p.hp+"\nSpell:"+p.spell+"\n"+p.atk+"P"+p.def+"D\nGraze:"+p.graze+"\nPoint:"+p.point;
		if(p.y>VU.height/3*2){
			disText+="\n-=Auto Collect On=-";
		}
		everything.setText(disText);
		everything.setPosition(0,90,Align.bottomLeft);
		
		//Graze
		for(EntityEnemyBullet eeb:groupEnemyBullet){
			if(!eeb.grazed && getDist(eeb, p)<=p.getCollision()*4){
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
		
		if(renderBoss){
			sb.draw(am.get(boss.texture,Texture.class), boss.x-boss.sx/2, boss.y-boss.sy/2,boss.sx,boss.sy);
		}
		sb.draw(am.get(p.texture,Texture.class), p.x-p.sx/2, p.y-p.sy/2,p.sx,p.sy);
		
		checkRen(groupItem);
		checkRen(groupEnemyBullet);
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
			sb.draw(am.get("heart.png",Texture.class), p.x-p.getCollision(), p.y-p.getCollision(),p.getCollision()*2,p.getCollision()*2);
			for(Entity e:groupEnemy){
				sb.draw(am.get("heart.png",Texture.class), e.x-e.getCollision(), e.y-e.getCollision(),e.getCollision()*2,e.getCollision()*2);
			}
			for(Entity e:groupEnemyBullet){
				sb.draw(am.get("heart.png",Texture.class), e.x-e.getCollision(), e.y-e.getCollision(),e.getCollision()*2,e.getCollision()*2);
			}
		}
		
		sb.end();
		
		if(renderMode==0){
			ui.act();	
		}
		ui.draw();
		
		if(!renderBoss || boss.isAppearing() || boss.getSpell().isNonspell()){
			spellScroll.setVisible(false);
		}else{
			spellScroll.setVisible(true);
			if(renderMode==0){
				spellScroll.moveBy(-1, 0);
				if(spellScroll.getX()+spellScroll.getText().length*8<0){
					spellScroll.setX(VU.width+10);
				}
			}
		}
		
		if(renderBoss && !boss.isAppearing()){
			//render the boss name and bar
			String td=boss.name;
			td+=String.format(" !!!%.1f sec!!!",boss.currentTime/60f );
			td+="\n";

			for(int i=0;i<boss.spells.size()-boss.currentSpellPointer;i++){
				td+="*";
			}
			bossName.setText(td);
			
			spellScroll.setText(boss.getSpell().name.replace("\n", ""));
			
			
			sb.begin();
			sb.setColor(0.9f,0.08f,0.05f,0.8f);
			
			sb.draw(am.get("pure.png",Texture.class), 50, VU.height-50,(VU.width-100)*(boss.currentHp/boss.getSpell().hp),10);
			sb.setColor(Color.WHITE);
			sb.end();
		}else{
			bossName.setText("Stage Test");
		}
		
		if(renderMode==1){
			//Esc
			sb.begin();
			
			sb.setColor(0.22f,0.22f,0.22f,0.78f);
			
			
			sb.draw(am.get("pure.png",Texture.class), 0, 0,VU.width,VU.height);
			sb.setColor(Color.WHITE);
			sb.end();
			escMenu.act();
			escMenu.draw();
		}
		
		//Process Level Information
		if(renderBoss==false){
			boss=new Boss(this, "enemy.png", 128, 64, "reimu.png","Test Boss",VU.width/2f,300,new TestNonSpellCard(this),new TestSpellCard(this));
			renderBoss=true;
		}
		
//		if(frameC>=120){
//			EnemySelfAim esa=new EnemySelfAim(this, p.x, VU.height+100);
//			addEnemy(esa);
//			frameC-=120;
//		}
	}

	//clear bullet and enemy
	public void clearBullet() {
		for(EntityEnemyBullet eeb:groupEnemyBullet){
			eeb.onKill();
		}
		
		groupEnemyBullet.clear();
		for(EntityEnemy ee:groupEnemy){
			ee.onKill();
		}
		
		groupEnemy.clear();
		
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

	public void bonusFailed() {

		if(renderBoss && !boss.isAppearing()){
			boss.bonus=false;
		}
	}

	
}
