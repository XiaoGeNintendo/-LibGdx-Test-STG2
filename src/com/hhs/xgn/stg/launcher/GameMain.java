package com.hhs.xgn.stg.launcher;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.hhs.xgn.stg.game.PlayerZYQ;
import com.hhs.xgn.stg.game.TestStageBuilder;
import com.hhs.xgn.stg.game.ZYQBullet;
import com.hhs.xgn.stg.struct.GameBuilder;
import com.hhs.xgn.stg.struct.GameChosen;
import com.hhs.xgn.stg.struct.StageBuilder;

public class GameMain extends Game{

	Screen mainScr,stScr;
	GameBuilder gb;
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
//		gb=new TestGameBuilder();
		
//		stScr=new StartScreen(this,gb);
		
//		setScreen(stScr);
		
		setStage(new TestStageBuilder(),new GameChosen(new PlayerZYQ(null), "Debug"));
	}
	
	public void setStage(StageBuilder sb,GameChosen gc){
		mainScr=new MainScreen(this, sb,gc);
		setScreen(mainScr);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		super.dispose();
		
		mainScr.dispose();
	}
}
