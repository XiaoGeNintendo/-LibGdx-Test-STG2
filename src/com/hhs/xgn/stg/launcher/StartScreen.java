package com.hhs.xgn.stg.launcher;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.struct.GameBuilder;
import com.hhs.xgn.stg.type.AudioSystem;

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
	AssetManager am;
	
	String[] resources=new String[]{
			"start/bg.jpg",
			"start/title.png",
	};
	
	Image bg,title;
	Group textgroup;
	
	AudioSystem as;
	
	String[] option=new String[]{"Start Game","Credits","Exit"};
	int opId=0;
	
	public StartScreen(GameMain gm,GameBuilder gb){
		this.gm=gm;
		this.gb=gb;
		
		st=new Stage();
		am=new AssetManager();
		as=new AudioSystem();
		textgroup=new Group();
		
		for(String path:resources){
			am.load(path,Texture.class);
		}
		am.finishLoading();
		
		bg=new Image(am.get("start/bg.jpg",Texture.class));
		bg.setBounds(0, 0, VU.width+VU.rightWidth, VU.height);
		title=new Image(am.get("start/title.png",Texture.class));
		VU.setTo(title, 0.5f, 0.5f);
		title.setColor(1,1,1,0);
		title.setOrigin(Align.center);
		
		Action forever=Actions.sequence(Actions.delay(0.4f),Actions.scaleTo(1.2f, 1.2f,0.05f),Actions.scaleTo(1, 1,0.05f));
		
		title.addAction(Actions.sequence(Actions.fadeIn(2f),Actions.run(new Runnable(){

			@Override
			public void run() {
				as.playBGMPrimitive("mus/title.mp3", 1);
			}
		
		}),Actions.forever(forever)));
		
		refreshOption();
		
		st.addActor(bg);
		st.addActor(title);
		st.addActor(textgroup);
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
		VU.disposeAll(st,as);
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
		// TODO Auto-generated method stub
		VU.render(st);
		
		if(state!=0){
			title.setVisible(false);
			textgroup.setVisible(false);
		}else{
			title.setVisible(true);
			textgroup.setVisible(true);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)){
			
			if(state==0){
				opId++;
				opId%=option.length;
				refreshOption();
				as.playSound("dialog");
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			
			if(state==0){
				opId+=option.length-1;
				opId%=option.length;
				refreshOption();
				as.playSound("dialog");
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Z)){
			if(state==0){

				as.playSound("explode");
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
					//TODO choose character
					opId=0;
					state=1;
				}
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.X)){
			if(state!=0){
				state--;
				opId=0;
				as.playSound("back");
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
