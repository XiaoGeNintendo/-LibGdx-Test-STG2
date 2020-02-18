package com.hhs.xgn.stg.launcher;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.replay.Replay;
import com.hhs.xgn.stg.struct.GameBuilder;
import com.hhs.xgn.stg.type.Player;

public class StartScreen implements Screen{

	GameMain gm;
	GameBuilder gb;
	
	/**
	 * The state of operating. <br/>
	 * 0 = start menu <br/>
	 * 1 = character chosing <br/>
	 * 2 = difficulty chosing <br/>
	 */
	int state=0;
	
	Stage st;
	
	Image bg,title;
	Group textgroup,gpRep;
	
	Image tachi,panel;
	Label descL;
	
	String[] option=new String[]{"Start Game","Replay","Credits","Exit"};
	int opId=0,diffId=0;
	
	public StartScreen(GameMain gm,GameBuilder gb){
		this.gm=gm;
		this.gb=gb;
		
		st=new Stage();
		textgroup=new Group();
		
		bg=new Image(gm.am.get("start/bg.jpg",Texture.class));
		bg.setBounds(0, 0, VU.width+VU.rightWidth, VU.height);
		title=new Image(gm.am.get("start/title.png",Texture.class));
		VU.setTo(title, 0.5f, 0.5f);
		title.setColor(1,1,1,0);
		title.setOrigin(Align.center);
		
		Action forever=Actions.sequence(Actions.delay(60/140f-0.1f),Actions.scaleTo(1.2f, 1.2f,0.05f),Actions.scaleTo(1, 1,0.05f));
		
		title.addAction(Actions.sequence(Actions.fadeIn(2f),Actions.run(new Runnable(){

			@Override
			public void run() {
				gm.as.playBGMPrimitive("mus/title.mp3", 1);
			}
		
		}),Actions.forever(forever)));
		refreshOption();
		
		tachi=new Image(gm.am.get("start/bg.jpg",Texture.class));
		descL=VU.createLabel("Description!!");
		descL.getStyle().fontColor=Color.GREEN;
		descL.setAlignment(Align.topLeft);
		descL.setFontScale(0.5f);
		refreshCharOption();
		
		panel=new Image(gm.am.get("ui/pure.png",Texture.class));
		panel.setColor(0.8f,0.8f,0.8f,0.8f);
		panel.setBounds(0, 0, VU.width+VU.rightWidth, VU.height);
		
		
		gpRep=new Group();
		
		st.addActor(bg);
		st.addActor(title);
		st.addActor(textgroup);
		st.addActor(tachi);
		st.addActor(descL);
		st.addActor(panel);
		st.addActor(gpRep);
	}
	
	public void refreshDiffOption(){
		descL.clearActions();
		descL.setText(gb.diffs.get(diffId));
		descL.setPosition(VU.width, 500);
		descL.addAction(Actions.moveTo(VU.width, VU.height/2f,0.5f));
	}
	
	public void refreshCharOption(){
		Player pn=gb.self.get(opId);
		
		tachi.clearActions();
		descL.clearActions();
		
		tachi.setDrawable(new TextureRegionDrawable(new TextureRegion(gm.am.get(pn.in+"/front.png",Texture.class))));
		tachi.setBounds(100, 100, VU.width-200, VU.height-200);
		tachi.setScaleX(0);
		tachi.addAction(Actions.scaleTo(1, 1,0.5f));
		
		descL.setText(pn.dn+"\n\n"+pn.desc);
		descL.setPosition(-1000, VU.height-100);
		descL.addAction(Actions.moveTo(VU.width-100, VU.height-100,0.5f));
		
	}
	
	public void refreshOption(){
		String s1=option[opId];
		//clear old
		for(Actor a:textgroup.getChildren()){
			a.addAction(Actions.sequence(Actions.moveBy(VU.easyRandom(-400, 400), 500,0.5f),Actions.removeActor()));
		}
		
		for(int i=0;i<s1.length();i++){
			Label label=VU.createLabel(s1.charAt(i)+"","ink.fnt");
//			label.setPosition(VU.width+i*20, 200);
			label.getStyle().fontColor=Color.BLUE;
			label.setPosition(VU.easyRandom(-400, 400), 500);
		
			Action fe=Actions.sequence(Actions.moveBy(0, 5,0.1f),Actions.moveBy(0, -5,0.1f));
			
			label.addAction(Actions.sequence(Actions.moveTo(VU.width+i*20, 200,0.5f),Actions.delay(i/33f),Actions.forever(fe)));
			textgroup.addActor(label);
		}
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		VU.disposeAll(st);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * The absolute render mode <br/>
	 * 0 = StartScreen <br/>
	 * 1 = ReplayScreen <br/>
	 */
	public int overWorldRender=0;
	
	@Override
	public void render(float arg0) {
		//common setting
		VU.render(st);
	
		if(overWorldRender==0){
			owr0();
		}else if(overWorldRender==1){
			owr1();
		}
		
		
	}

	ArrayList<Replay> reps=new ArrayList<>();
	
	int replayId=0;
	public void loadReplay(){
		reps.clear();
		gpRep.clear();
		FileHandle fh=Gdx.files.local("replay");
		int i=0;
		
		for(FileHandle sub:fh.list()){
			try{
				Replay rep=gm.gs.fromJson(sub.readString("utf-8"), Replay.class);
				
				Label lb=VU.createLabel("Dte#"+new Date(rep.time)+" SId#"+rep.sId+" Pln#"+rep.player.dn);
				lb.setPosition(700, 300-i*30,Align.bottomLeft);
				lb.setFontScale(0.5f);
				lb.addAction(Actions.sequence(Actions.delay(i*0.1f),Actions.moveBy(-650,0,0.5f)));
				gpRep.addActor(lb);
				reps.add(rep);
				i++;
				Gdx.app.log("StartScreen::loadReplay", sub+" load successfully");
			}catch(Exception e){
				Gdx.app.error("StartScreen::loadReplay", "Cannot load "+sub+" because of "+e+". Is some weird replays in?");
			}
		}
		
	}
	
	private void owr1(){
		panel.setVisible(true);
		gpRep.setVisible(true);
		
		if(reps.size()==0){
			overWorldRender=0;
			return;
		}
		((Label)(gpRep.getChildren().get(replayId))).getStyle().fontColor=Color.BLACK;
		
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)){
			if(replayId!=reps.size()-1){
				replayId++;
				gm.as.playSound("dialog");
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			if(replayId!=0){
				replayId--;
				gm.as.playSound("dialog");
			}
		}
		
		((Label)(gpRep.getChildren().get(replayId))).getStyle().fontColor=Color.GREEN;
		
		if(Gdx.input.isKeyJustPressed(Keys.X)){
			overWorldRender=0;
			gm.as.playSound("back");
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Z)){
			gm.as.playSound("explode");
			Replay rep=reps.get(replayId);
			rep.isReplay=true;
			gm.setStage(rep.sId,rep);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.C)){
			gm.as.playSound("explode");
			Gdx.files.local("replay").list()[replayId].delete();
			loadReplay();
		}
	}
	
	private void owr0() {
		//toggle render state
		panel.setVisible(false);
		gpRep.setVisible(false);
		
		if(state==0){
			title.setVisible(true);
			textgroup.setVisible(true);
			tachi.setVisible(false);
			descL.setVisible(false);
		}else if(state==1){
			title.setVisible(false);
			textgroup.setVisible(false);
			tachi.setVisible(true);
			descL.setVisible(true);
		}else{
			title.setVisible(false);
			textgroup.setVisible(false);
			tachi.setVisible(true);
			descL.setVisible(true);
		}
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
			if(state==1){
				opId++;
				opId%=gb.self.size();
				refreshCharOption();
				gm.as.playSound("dialog");
			}else if(state==2){
				diffId++;
				diffId%=gb.diffs.size();
				refreshDiffOption();
				gm.as.playSound("dialog");
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			if(state==1){
				opId+=gb.self.size()-1;
				opId%=gb.self.size();
				refreshCharOption();
				gm.as.playSound("dialog");
			}else if(state==2){
				diffId+=gb.diffs.size()-1;
				diffId%=gb.diffs.size();
				refreshDiffOption();
				gm.as.playSound("dialog");
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)){
			
			if(state==0){
				opId++;
				opId%=option.length;
				refreshOption();
				gm.as.playSound("dialog");
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			
			if(state==0){
				opId+=option.length-1;
				opId%=option.length;
				refreshOption();
				gm.as.playSound("dialog");
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Z)){
			if(state==0){
				gm.as.playSound("explode");
				if(opId==3){
					Launcher.game.exit();
				}else if(opId==2){
					JOptionPane.showMessageDialog(null,"Test Stg 2\n"
							+ "Copyright 2019-2020 XGN from HHS\n"
							+ "Music By Zzzyt from HHS\n"
							+ "Some Art by ZUN\n"
							+ "SE generated by SFXR\n"
							+ "This project is a open-source project\n"
							+ "The author believes all the resources this project includes are of fair use.");
				}else if(opId==1){
					overWorldRender=1;
					replayId=0;
					loadReplay();
				}else{
					opId=0;
					state=1;
				}
			}else if(state==1){
				gm.as.playSound("explode");
				gm.gc.chosenPlayer=gb.self.get(opId);
				state=2;
				diffId=0;
				refreshDiffOption();
			}else if(state==2){
				gm.as.playSound("explode");
				gm.gc.chosenDifficulty=gb.diffs.get(diffId);
				gm.setStage(0);
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.X)){
			if(state!=0){
				state--;
				if(state!=1){
					opId=0;
				}else{
					refreshCharOption();
				}
				
				gm.as.playSound("back");
			}
			
		}
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

}
