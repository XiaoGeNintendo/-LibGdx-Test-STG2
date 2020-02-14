package com.hhs.xgn.stg.launcher;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.struct.GameBuilder;
import com.hhs.xgn.stg.type.AudioSystem;

public class StartScreen implements Screen{

	Launcher fa;
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
			"start/pg.png",
			"start/title.png",
	};
	
	Image bg,title;
	Group textgroup;
	
	AudioSystem as;
	
	public StartScreen(Launcher fa,GameBuilder gb){
		this.fa=fa;
		this.gb=gb;
		
		st=new Stage();
		am=new AssetManager();
		as=new AudioSystem();
		
		for(String path:resources){
			am.load(path,Texture.class);
		}
		am.finishLoading();
		
		bg=new Image(am.get("start/bg.png",Texture.class));
		bg.setBounds(0, 0, VU.width+VU.rightWidth, VU.height);
		title=new Image(am.get("start/title.png",Texture.class));
		VU.setTo(title, 0.5f, 0.6f);
		
		title.addAction(Actions.sequence(Actions.fadeIn(5f),Actions.run(new Runnable(){

			@Override
			public void run() {
				as.playBGM("mus/title.wav",1);
			}
		
		})));
		
		st.addActor(bg);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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
