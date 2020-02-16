package com.hhs.xgn.stg.launcher;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
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
	Group textgroup;
	
	Image tachi;
	Label descL;
	
	String[] option=new String[]{"Start Game","Credits","Exit"};
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
		
		st.addActor(bg);
		st.addActor(title);
		st.addActor(textgroup);
		st.addActor(tachi);
		st.addActor(descL);
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

	@Override
	public void render(float arg0) {
		VU.render(st);
		
		//toggle render state
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
		
		//key mapping
		
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
				if(opId==2){
					Launcher.game.exit();
				}else if(opId==1){
					JOptionPane.showMessageDialog(null,"Test Stg 2\n"
							+ "Copyright 2019-2020 XGN from HHS\n"
							+ "Music By Zzzyt from HHS\n"
							+ "Some Art by ZUN\n"
							+ "SE generated by SFXR\n"
							+ "This project is a open-source project\n"
							+ "The author believes all the resources this project includes are of fair use.");
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
