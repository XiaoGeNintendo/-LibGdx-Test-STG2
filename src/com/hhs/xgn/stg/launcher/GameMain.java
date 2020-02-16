package com.hhs.xgn.stg.launcher;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.game.TestGameBuilder;
import com.hhs.xgn.stg.struct.GameBuilder;
import com.hhs.xgn.stg.struct.GameChosen;
import com.hhs.xgn.stg.struct.StageBuilder;
import com.hhs.xgn.stg.type.AudioSystem;

public class GameMain extends Game{

	public Screen mainScr,stScr;
	public GameBuilder gb;
	public GameChosen gc=new GameChosen();

	public AssetManager am;
	public AudioSystem as;
	String[] resources=new String[]{
			"start/bg.jpg",
			"start/title.png",
			"zyq/front.png",
			"entity/bullet.png",
			"entity/enemy.png",
			"zyq/player1.png",
			"zyq/player2.png",
			"zyq/player3.png",
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
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		am=new AssetManager();
		for(String s:resources){
			am.load(s,Texture.class);
		}
		am.finishLoading();
		as=new AudioSystem();
		
		gb=new TestGameBuilder();
		
		stScr=new StartScreen(this,gb);
		
		setScreen(stScr);
		
//		setStage(new TestStageBuilder(),new GameChosen(new PlayerZYQ(null), "Debug"));
	}
	
	public void setStage(StageBuilder sb){
		mainScr=new MainScreen(this, sb,gc);
		setScreen(mainScr);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		super.dispose();
		
		VU.disposeAll(mainScr,stScr);
		
	}
}
