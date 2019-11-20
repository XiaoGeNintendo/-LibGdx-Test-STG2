package com.hhs.xgn.stg.launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
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
import com.hhs.xgn.stg.type.SpellCardAction;
import com.hhs.xgn.stg.game.EnemySelfAim;
import com.hhs.xgn.stg.game.ItemPower;
import com.hhs.xgn.stg.game.TestNonSpellCard;
import com.hhs.xgn.stg.game.TestRandomCard;
import com.hhs.xgn.stg.game.TestSpellCard;
import com.hhs.xgn.stg.type.AudioSystem;
import com.hhs.xgn.stg.type.Boss;
import com.hhs.xgn.stg.type.Dialog;
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
	public Label timeLeft;
	
	public Label spellScroll;
	
	public int renderMode;
	public Stage escMenu;
	public Label escEv;
	
	public Stage rightUI;
	
	public ArrayList<Image> spells=new ArrayList<>();
	
	public AudioSystem audio;
	
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
			"entity/bullet.png",
			"entity/enemy.png",
			"player/player1.png",
			"player/player2.png",
			"player/player3.png",
			"entity/playerbullet.png",
			"ui/heart.png",
			"entity/power.bmp",
			"ui/pure.png",
			"entity/point.bmp",
			"art/reimu.png",
			"bg/background.png",
			"ui/ui_bg.png",
			"ui/spells.png",
			"ui/ui_com.png",
			"bg/frogscbg.png",
			"bg/bomb.png"
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
		
		audio=new AudioSystem();
		
		//Render UI
		ui=new Stage();
		rightUI=new Stage();
		
		everything=VU.createLabel("");
		everything.setPosition(VU.width,VU.height-90);
		everything.getStyle().fontColor=new Color(1,0,0,0.8f);
//		everything.setFontScale(0.5f);
		
		bossName=VU.createLabel("Stage Test","ink.fnt");
		bossName.getStyle().fontColor=Color.RED;
		
		bossName.setPosition(0, VU.height,Align.topLeft);
		bossName.setFontScale(0.5f);
		
		timeLeft=VU.createLabel("000.0s","ink.fnt");
		VU.setTo(timeLeft, 0.5f, 0.95f);
		timeLeft.getStyle().fontColor=Color.RED;
		
		
		spellScroll=VU.createLabel("","pixel.fnt");
		spellScroll.setPosition(0,VU.height-75);
//		spellScroll.getStyle().fontColor=new Color(0,0,1);
		spellScroll.setFontScale(0.6f);
		
		ui.addActor(bossName);
		rightUI.addActor(everything);
		ui.addActor(spellScroll);
		ui.addActor(timeLeft);
		
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
		VU.disposeAll(sb,am,ui,audio);
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
	
	final float FONT_WIDTH=26;
	
	public void checkInvisible(){
		if(p.deadTime!=0){
			VU.clear(0, 0, 0, 1);
			p.deadTime--;
			
			clearBullet();
			if(renderBoss && !boss.isAppearing()){
//				for(int i=0;i<10;i++){
//					boss.onHit(null);
//				}
//				boss.onHit(new Entity(this));
//				boss.currentHp-=p.atk;
			}
		}else{
			VU.clear(1,1,1,1);	
		}
	}
	
	public void renderBG(){
		//render background
		
		//least priority
		sb.begin();
		sb.draw(am.get("bg/background.png",Texture.class), 0,-backgroundC%VU.height,VU.width,VU.height);
		sb.draw(am.get("bg/background.png",Texture.class), 0,-backgroundC%VU.height-VU.height,VU.width,VU.height);
		sb.draw(am.get("bg/background.png",Texture.class), 0,-backgroundC%VU.height+VU.height,VU.width,VU.height);
		sb.end();
		
		//priority 2
		if(renderBoss && boss.getSpell().useSpecialRender){
			sb.begin();
			boss.getSpell().renderBg(sb,backgroundC);
			sb.end();
		}
		
		//max priority
		if(p.deadTime>0){
			sb.begin();
			for(int i=-4;i<=50;i++){
				for(int j=-4;j<=10;j++){
					sb.draw(am.get("bg/bomb.png",Texture.class), backgroundC%100+i*100,j*50-backgroundC%100,100,50);
				}
			}
			
			sb.end();
		}
		
	}
	
	public boolean isShowingDialog(){
		return renderBoss && !boss.isAppearing() && boss.getSpell() instanceof SpellCardAction;
	}
	
	public SpellCardAction getAction(){
		if(!isShowingDialog()){
			return null;
		}
		return ((SpellCardAction)boss.getSpell());
	}
	
	public int getDialogPointer(){
		return getAction().pointer;
	}
	
	public Dialog getDialog(){
		return getAction().arr.get(getDialogPointer());
	}
	
	public void addDialog(Dialog d){
		
	}
	
	/**
	 * Proceed dialog and all related information
	 */
	public void showDialog(){
		if(!isShowingDialog()){
			return;
		}
		System.out.println("In dialog");
		if(getDialogPointer()==0){
			Dialog d=getDialog();
			addDialog(d);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Z)){
			System.out.println("Press Z");
			getAction().pointer++;
			if(getAction().pointer==getAction().arr.size()){
				boss.nextSpellCard();
			}
		}
	}
	
	@Override
	public void render(float arg0) {
		
		frameC++;
		
		if(renderMode==0){
			backgroundC++;
		}
		
		checkInvisible();
		renderBG();
		//Check Esc key
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			if(renderMode==1){
				renderMode=0;
			}else{
				renderMode=1;
			}
		}
		
		//Show Dialog Block if Boss Action is present
		showDialog();
		
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
		everything.setText(disText);
		everything.setPosition(VU.width,VU.height-90,Align.bottomLeft);
		
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
			sb.draw(am.get("ui/heart.png",Texture.class), p.x-p.getCollision(), p.y-p.getCollision(),p.getCollision()*2,p.getCollision()*2);
			for(Entity e:groupEnemy){
				sb.draw(am.get("ui/heart.png",Texture.class), e.x-e.getCollision(), e.y-e.getCollision(),e.getCollision()*2,e.getCollision()*2);
			}
			for(Entity e:groupEnemyBullet){
				sb.draw(am.get("ui/heart.png",Texture.class), e.x-e.getCollision(), e.y-e.getCollision(),e.getCollision()*2,e.getCollision()*2);
			}
		}
		
		sb.end();
		
		if(renderMode==0){
			ui.act();	
		}
		ui.draw();
		
		if(!renderBoss || boss.isAppearing() || boss.getSpell().isNonspell() || boss.getSpell() instanceof SpellCardAction){
			spellScroll.setVisible(false);
		}else{
			spellScroll.setVisible(true);
			if(renderMode==0){
				spellScroll.moveBy(-1, 0);
				if(spellScroll.getX()+spellScroll.getText().length*FONT_WIDTH<0){
					spellScroll.setX(VU.width+10);
				}
			}
		}
		
		if(renderBoss && !boss.isAppearing()){
			//render the boss name and bar
			String td=boss.name+"\n";
			String ttd=String.format("%.1fs",boss.currentTime/60f );

//			for(int i=0;i<boss.spells.size()-boss.currentSpellPointer;i++){
//				td+="";
//			}
			bossName.setText(td);
			
			if(boss.getSpell() instanceof SpellCardAction){
				timeLeft.setText("vvv");
				spellScroll.setText("");
			}else{
				timeLeft.setText(ttd);
				
				spellScroll.setText(boss.getSpell().name.replace("\n", ""));
				
				sb.begin();
				sb.setColor(0.9f,0.08f,0.05f,0.8f);
				
				sb.draw(am.get("ui/pure.png",Texture.class), 50, VU.height-50,(VU.width-100)*(boss.currentHp/boss.getSpell().hp),10);
				sb.setColor(Color.WHITE);
				sb.end();
			}
			
		}else{
			bossName.setText("Stage Test\n");
			timeLeft.setText("");
		}
		
		//Draw UI Bg
		sb.begin();
		sb.draw(am.get("ui/ui_bg.png",Texture.class), VU.width, 0, VU.rightWidth, VU.height);
		sb.end();
		
		rightUI.act();
		rightUI.draw();
		
		if(renderMode==1){
			//Esc
			sb.begin();
			
			sb.setColor(0.22f,0.22f,0.22f,0.78f);
			
			
			sb.draw(am.get("ui/pure.png",Texture.class), 0, 0,VU.width,VU.height);
			sb.setColor(Color.WHITE);
			sb.end();
			escMenu.act();
			escMenu.draw();
		}
		
		//Process Level Information
		if(backgroundC==1){
			audio.playBGM("boss",1f);
		}
		if(renderBoss==false){
			boss=new Boss(this, "entity/enemy.png", 128, 64, "art/reimu.png","Test Boss",VU.width/2f,300,
					new SpellCardAction(this,
										new Dialog("bg/frogscbg.png", "何言ってるのよ早苗とも神奈子とも遊んだんでしょ？\n私だけ無視して巫女が務まるとでも思ってるの？", null),
										new Dialog("bg/reimu.png", "もしかして、前に早苗や神奈子と戦ったりしたのって……", null),
										new Dialog("bg/frogscbg.png","そう、ただの神遊び、つまりお祭り\n今日は私の弾幕お祭りの番よ！","boss")
										),
					new TestNonSpellCard(this),
					new TestSpellCard(this),
					new TestRandomCard(this),
					new SpellCardAction(this,
										new Dialog("bg/frogscbg.png","あはははは。天晴れだわ一王国を築いたこの私が、人間に負けるとは","-")
									   )
					);
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
