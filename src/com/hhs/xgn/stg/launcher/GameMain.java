package com.hhs.xgn.stg.launcher;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.hhs.xgn.stg.game.TestStageBuilder;

public class GameMain extends Game{

	Screen mainScr;
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		mainScr=new MainScreen(this,new TestStageBuilder());
		
		setScreen(mainScr);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		super.dispose();
		
		mainScr.dispose();
	}
}
