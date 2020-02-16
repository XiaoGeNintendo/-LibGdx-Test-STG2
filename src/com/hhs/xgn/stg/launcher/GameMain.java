package com.hhs.xgn.stg.launcher;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
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
	
	void loadRes(FileHandle fh){
		System.out.println("Now at:"+fh);
		if(fh.isDirectory()){
			for(FileHandle s:fh.list()){
				loadRes(s);
			}
		}else{
			if(fh.extension().matches("jpg|png|bmp")){
				System.out.println("Loading:"+(fh+"").substring(7));
				am.load((fh+"").substring(7),Texture.class);
			}
		}
	}
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		am=new AssetManager();
		
		loadRes(Gdx.files.internal("assets"));
		
		am.finishLoading();
		as=new AudioSystem();
		
		gb=new TestGameBuilder();
		
		stScr=new StartScreen(this,gb);
		
		setScreen(stScr);
		
//		setStage(new TestStageBuilder(),new GameChosen(new PlayerZYQ(null), "Debug"));
	}
	
	public void setStage(int id){
		if(mainScr!=null){
			mainScr.dispose();
		}
		
		mainScr=new MainScreen(this, gb.stage.get(id),gc,id);
		setScreen(mainScr);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		super.dispose();
		
		VU.disposeAll(mainScr,stScr);
		
	}
}
