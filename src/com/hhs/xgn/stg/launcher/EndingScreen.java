package com.hhs.xgn.stg.launcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.replay.Replay;
import com.hhs.xgn.stg.struct.EndingBuilder;
import com.hhs.xgn.stg.type.Player;

public class EndingScreen implements Screen {

	public GameMain gm;
	public EndingBuilder eb;
	public Player pl;
	public String[] txt;
	
	public Stage st;
	public Label lb;
	public Image bg;
	
	public int tid;
	
	public EndingScreen(GameMain gm,EndingBuilder eb,Replay rep) {
		this.gm=gm;
		this.eb=eb;
		
		if(rep.isReplay){
			Gdx.app.error("EndingScree", "[FATAL]Why a replay will come here?");
			Launcher.game.exit();
		}else{
			this.pl=(rep.player==null?gm.gc.chosenPlayer.clone():rep.player);
		}
		
		
		this.txt=eb.getText(this.pl);
		
		st=new Stage();
		
		lb=VU.createLabel("","pixel.fnt");
		lb.setAlignment(Align.topLeft);
		lb.setPosition(50, 400);
		lb.setWidth(500);
		lb.getStyle().fontColor=Color.WHITE;
		lb.setWrap(true);
		
		bg=new Image(gm.am.get("ui/pure.png",Texture.class));
		bg.setBounds(0, 0, VU.width+VU.rightWidth, VU.height);
		
		st.addActor(bg);
		st.addActor(lb);
		
		processText(1);
		
//		gm.as.playBGM(eb.getStageMusic(), 1);
	}
	
	@Override
	public void dispose() {
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

	public void processText(int delta){
		if(tid==txt.length){
			gm.returnToMenu();
			return;
		}
		if(tid==-1){
			tid++;
			processText(1);
			return;
		}
		
		String s=txt[tid];
		if(s.startsWith("!")){
			bg.clearActions();
			bg.addAction(Actions.sequence(
					Actions.fadeOut(0.5f),
					Actions.run(new Runnable() {
						
						@Override
						public void run() {
							bg.setDrawable(new TextureRegionDrawable(new TextureRegion(gm.am.get(s.substring(1),Texture.class))));
						}
					}),
					Actions.fadeIn(0.5f)));
			tid+=delta;
			processText(delta);
		}else if(s.startsWith("~")){
			gm.as.playBGMPrimitive(s.substring(1), 1);
			tid+=delta;
			processText(delta);
		}else{

			gm.as.playSound("dialog");
			lb.clearActions();
			lb.addAction(Actions.sequence(
					Actions.fadeOut(0.5f),
					Actions.run(new Runnable() {
						
						@Override
						public void run() {
							lb.setText(s);
						}
					}),
					Actions.fadeIn(0.5f)));
		}
	}
	
	@Override
	public void render(float arg0) {
		VU.render(st);
		
		if(Gdx.input.isKeyJustPressed(Keys.Z)){
			tid++;
			processText(1);
		}
		if(Gdx.input.isKeyJustPressed(Keys.X)){
			tid=Math.max(tid-1,0);
			processText(-1);
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
