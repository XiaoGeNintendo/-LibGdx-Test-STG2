package com.hhs.xgn.stg.launcher;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.type.Player;
import com.hhs.xgn.stg.type.SpellCardAction;
import com.hhs.xgn.stg.struct.GameChosen;
import com.hhs.xgn.stg.struct.StageBuilder;
import com.hhs.xgn.stg.type.Boss;
import com.hhs.xgn.stg.type.Dialog;
import com.hhs.xgn.stg.type.Entity;
import com.hhs.xgn.stg.type.EntityEnemy;
import com.hhs.xgn.stg.type.EntityBullet;
import com.hhs.xgn.stg.type.EntityItem;

public class MainScreen implements Screen {

	public GameMain gm;

	public ArrayList<EntityEnemy> groupEnemy=new ArrayList<>();
	public ArrayList<EntityBullet> groupPlayerBullet=new ArrayList<>();
	public ArrayList<EntityBullet> groupEnemyBullet=new ArrayList<>();
	public ArrayList<Player> groupPlayer=new ArrayList<>();
	public ArrayList<EntityItem> groupItem=new ArrayList<>();
	
	public Boss boss;
	public boolean renderBoss;
	
	public Player p;
	/**
	 * The difficulty and player setting.
	 */
	public GameChosen gc;
	
	public SpriteBatch sb;
	
	public Stage ui;
	public Label everything;
	public Group instant;
	public Label bossName;
	public Label timeLeft;
	public Label bonusLeft;
	
	public Label spellScroll;
	
	public int renderMode;
	public Stage escMenu;
	public Label escEv;
	
	public Stage rightUI;
	
	public ArrayList<Image> spells=new ArrayList<>();
	
	public ArrayList<Actor> dialogActors=new ArrayList<>();
	
	public StageBuilder builder;
	
	/**
	 * Used to generate next stage
	 */
	public int sId;
	
	public void addPlayerBullet(float x,float y,EntityBullet obj){
		obj.x=x;
		obj.y=y;
		groupPlayerBullet.add(obj);
	}
	
	public void addEnemyBullet(EntityBullet eeb){
		groupEnemyBullet.add(eeb);
	}
	
	public void addEnemy(EntityEnemy ee) {
		groupEnemy.add(ee);
	}
	

	public void addItem(EntityItem item) {
		groupItem.add(item);
	}

	public MainScreen(GameMain gm,StageBuilder builder,GameChosen gc,int sId){
		this.builder=builder;
		
		this.gm=gm;
		this.gc=gc;
		this.sId=sId;
		
		sb=new SpriteBatch();
		
		p=gc.chosenPlayer;
		
		p.obj=this;
		
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
		
		bonusLeft=VU.createLabel("Bonus: 00000000","ink.fnt");
		VU.setTo(bonusLeft, 0.9f, 0.95f);
		bonusLeft.getStyle().fontColor=Color.GREEN;
		bonusLeft.setFontScale(0.5f);
		
		spellScroll=VU.createLabel("","pixel.fnt");
		spellScroll.setPosition(0,VU.height-75);
//		spellScroll.getStyle().fontColor=new Color(0,0,1);
		spellScroll.setFontScale(0.6f);
		
		ui.addActor(bossName);
		rightUI.addActor(everything);
		ui.addActor(spellScroll);
		ui.addActor(timeLeft);
		ui.addActor(bonusLeft);
		
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
		VU.disposeAll(sb,gm.am,ui,gm.as);
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
	
	public int backgroundC;
	
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
		sb.draw(gm.am.get("bg/background.png",Texture.class), 0,-backgroundC%VU.height,VU.width,VU.height);
		sb.draw(gm.am.get("bg/background.png",Texture.class), 0,-backgroundC%VU.height-VU.height,VU.width,VU.height);
		sb.draw(gm.am.get("bg/background.png",Texture.class), 0,-backgroundC%VU.height+VU.height,VU.width,VU.height);
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
					sb.draw(gm.am.get("bg/bomb.png",Texture.class), backgroundC%100+i*100,j*50-backgroundC%100,100,50);
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
	

	final float dialog_time=0.5f;
	public void clearDialog(){
		if(dialogActors.size()==3){

			dialogActors.get(0).addAction(Actions.sequence(Actions.moveTo(-200, 0,dialog_time),Actions.removeActor()));
			dialogActors.get(1).addAction(Actions.sequence(Actions.moveTo(500, 0,dialog_time),Actions.removeActor()));
			dialogActors.get(2).addAction(Actions.sequence(Actions.moveTo(500, 0,dialog_time),Actions.removeActor()));
			dialogActors.clear();
		}
	}
	
	public void addDialog(Dialog d){
		
		//clear old actors
		clearDialog();
		
		//add new actors
		Image st=new Image(gm.am.get(d.speaker,Texture.class));
		st.setPosition(-200,0);
		
		Image box=new Image(gm.am.get("ui/pure.png",Texture.class));
		box.setColor(0.3f, 0.4f, 0.6f, 0.8f);
		box.setHeight(100);
		box.setWidth(VU.width);
		box.setPosition(500,0);
		
		Label lb=VU.createLabel(d.word,"pixel.fnt");
		lb.setPosition(500,0);
		lb.setFontScale(0.5f);
		lb.setWidth(VU.width);
		lb.setWrap(true);
		
		//add animation
		st.addAction(Actions.moveTo(0,0,dialog_time));
		box.addAction(Actions.moveTo(0,0,dialog_time));
		lb.addAction(Actions.moveTo(0,0,dialog_time));
		
		instant.addActor(st);
		instant.addActor(box);
		instant.addActor(lb);
		
		dialogActors.add(st);
		dialogActors.add(box);
		dialogActors.add(lb);
		
		//handle music
		if(d.music!=null){
			if(d.music.equals("-")){
				gm.as.stopMusic();
			}else if(d.music.equals("<")){
				gm.as.playBGM(builder.getStageMusic(),1f);
			}else{
				gm.as.playBGM(d.music, 1f);
				if(d.musicName!=null){
					displaySongName(d.musicName);
				}
			}
		}
		
	}
	
	/**
	 * Proceed dialog and all related information
	 */
	public void showDialog(){
		if(!isShowingDialog()){
			return;
		}
//		System.out.println("In dialog");
		if(getDialogPointer()==0 && getDialog().first){
			Dialog d=getDialog();
			addDialog(d);
			getDialog().first=false;
		}
		
		//in esc mode
		if(renderMode==1){
			return;
		}
		if(Gdx.input.isKeyJustPressed(Keys.Z)){
//			System.out.println("Press Z");
			getAction().pointer++;
			gm.as.playSound("dialog",0.1f);
			if(getAction().pointer==getAction().arr.size()){
				boss.nextSpellCard();
				
				clearDialog();
				return;
			}
			
			Dialog d=getDialog();
			addDialog(d);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.X)){
			if(getAction().pointer==0){
				gm.as.playSound("fail",0.1f);
			}else{
				getAction().pointer--;
				gm.as.playSound("back",0.1f);

				Dialog d=getDialog();
				addDialog(d);
			}
		}
	}
	
	@Override
	public void render(float arg0) {
		
		frameC++;
		
		if(renderMode==0){
			backgroundC++;
		}
		
		if(renderMode==0){
			checkInvisible();
		}
		
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
			for(EntityBullet epb:groupPlayerBullet){
				epb.onFrame();
			}
			for(EntityBullet eeb:groupEnemyBullet){
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
			for(EntityBullet x:groupPlayerBullet){
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
		String disText=String.format("HP:%.1f\nBomb:%d\nPower:%.1f\nGraze:%d\nPoint:%d", p.hp,p.spell,p.atk,p.graze,p.point);
		everything.setText(disText);
		everything.setPosition(VU.width,VU.height-90,Align.bottomLeft);
		
		//Graze
		for(EntityBullet eeb:groupEnemyBullet){
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
			sb.draw(gm.am.get(boss.texture,Texture.class),boss.x-boss.sx/2, boss.y-boss.sy/2,boss.sx,boss.sy,0,0,(int)boss.sx,(int)boss.sy,boss.flip,false);
		}
		
		sb.draw(gm.am.get(p.texture,Texture.class), p.x-p.sx/2, p.y-p.sy/2,p.sx,p.sy);
		
		checkRen(groupItem);
		checkRen(groupEnemyBullet);
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
			sb.draw(gm.am.get("ui/heart.png",Texture.class), p.x-p.getCollision(), p.y-p.getCollision(),p.getCollision()*2,p.getCollision()*2);
			for(Entity e:groupEnemy){
				sb.draw(gm.am.get("ui/heart.png",Texture.class), e.x-e.getCollision(), e.y-e.getCollision(),e.getCollision()*2,e.getCollision()*2);
			}
			for(Entity e:groupEnemyBullet){
				sb.draw(gm.am.get("ui/heart.png",Texture.class), e.x-e.getCollision(), e.y-e.getCollision(),e.getCollision()*2,e.getCollision()*2);
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
			String btd=String.format("Bonus:%08d",boss.calcCurrentBonus());
			
			bossName.setText(td);
			
			if(boss.getSpell() instanceof SpellCardAction){
				timeLeft.setText("vvv");
				spellScroll.setText("");
			}else{
				timeLeft.setText(ttd);
				
				spellScroll.setText(boss.getSpell().name.replace("\n", ""));
				
				//set bonus text
				if(boss.getSpell().isNonspell()){
					bonusLeft.setText("");
				}else{
					bonusLeft.setText(btd);
				}
				
				//draw life bar
				if(!boss.getSpell().isTimeSpell){
					sb.begin();
					sb.setColor(0.9f,0.08f,0.05f,0.8f);
					sb.draw(gm.am.get("ui/pure.png",Texture.class), 50, VU.height-50,(VU.width-100)*(boss.currentHp/boss.getSpell().hp),10);
					sb.setColor(Color.WHITE);
					
					
					//draw life piece
					int len=boss.getSpell().splits.length;
					for(int i=0;i<len;i++){
						if(boss.getSpell().splits[i]>=boss.currentHp){
							continue;
						}
						sb.setColor(0,i/1.0f/len,1-i/1.0f/len,1);
						sb.draw(gm.am.get("ui/pure.png",Texture.class), 50+(VU.width-100)*(boss.getSpell().splits[i]/boss.getSpell().hp), VU.height-50,3,10);
					}
					sb.setColor(Color.WHITE);
					sb.end();
				}
				
			}
			
		}else{
			bossName.setText(builder.getStageName(this));
			timeLeft.setText("");
			bonusLeft.setText("");
		}
		
		//Draw UI Bg
		sb.begin();
		sb.draw(gm.am.get("ui/ui_bg.png",Texture.class), VU.width, 0, VU.rightWidth, VU.height);
		sb.end();
		
		rightUI.act();
		rightUI.draw();
		
		if(renderMode==1){
			//Esc
			sb.begin();
			
			sb.setColor(0.22f,0.22f,0.22f,0.78f);
			
			
			sb.draw(gm.am.get("ui/pure.png",Texture.class), 0, 0,VU.width,VU.height);
			sb.setColor(Color.WHITE);
			sb.end();
			escMenu.act();
			escMenu.draw();
		}
		
		//Process Level Information
		if(renderMode==0){
			builder.onTick(this,arg0);
		}
		
		
		
//		if(frameC>=120){
//			EnemySelfAim esa=new EnemySelfAim(this, p.x, VU.height+100);
//			addEnemy(esa);
//			frameC-=120;
//		}
	}

	/**
	 * A full song name display using render method only
	 * @param name
	 */
	public void displaySongName(String name){
		name="MUSIC:"+name;
		
		float tot=0;
		
		for(int i=0;i<name.length();i++){
			Label lb=VU.createLabel(name.charAt(i)+"","pixel.fnt");
			lb.setPosition(-100, -100);
			final float x=0.1f;
			
			float sz=0;
			if(name.charAt(i)<=128){
				sz=FONT_WIDTH/2;
			}else{
				sz=FONT_WIDTH;
			}
			
			tot+=sz;
			lb.addAction(Actions.sequence(
						Actions.delay(x*i),
						Actions.moveTo(tot, VU.height/2,x),
						Actions.delay((name.length()-i)*x+1),
						Actions.moveBy(-300, 0,0.2f),
						Actions.delay(1),
						Actions.moveBy(-300, 0,0.2f),
						Actions.removeActor()));
			
			instant.addActor(lb);
		}
	}
	
	//clear bullet and enemy
	public void clearBullet() {
		for(EntityBullet eeb:groupEnemyBullet){
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
			sb.draw(gm.am.get(p.texture,Texture.class), p.x-p.sx/2, p.y-p.sy/2,p.sx,p.sy);
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
