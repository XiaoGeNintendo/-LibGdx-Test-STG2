package com.hhs.xgn.stg.launcher;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.google.gson.Gson;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.game.TestGameBuilder;
import com.hhs.xgn.stg.struct.EndingBuilder;
import com.hhs.xgn.stg.struct.GameBuilder;
import com.hhs.xgn.stg.struct.GameChosen;
import com.hhs.xgn.stg.struct.StageBuilder;
import com.hhs.xgn.stg.type.AudioSystem;
import com.hhs.xgn.stg.type.Player;

public class GameMain extends Game{

	public Screen mainScr,stScr,edScr;
	public GameBuilder gb;
	public GameChosen gc=new GameChosen();

	public AssetManager am;
	public AudioSystem as;
	
	public Gson gs=new Gson();
	
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
		Gdx.app.log("LoadAsset", "Now at:"+fh);
		
		if(fh.isDirectory()){
			for(FileHandle s:fh.list()){
				loadRes(s);
			}
		}else{
			if(fh.extension().matches("jpg|png|bmp")){
				Gdx.app.log("LoadAsset", "Loading:"+(fh+"").substring(7));
				
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
		
		returnToMenu();
		
//		setStage(new TestStageBuilder(),new GameChosen(new PlayerZYQ(null), "Debug"));
	}
	
	public void setStage(int id,Player inherit){
//		if(mainScr!=null){
//			mainScr.dispose();
//		}
		
		as.stopMusic();
		
		StageBuilder sb=gb.stage.get(id);
		if(sb instanceof EndingBuilder){
			edScr=new EndingScreen(this,(EndingBuilder)sb,inherit);
			setScreen(edScr);
		}else{
			mainScr=new MainScreen(this, sb,gc,id,inherit);
			setScreen(mainScr);
		}
	}
	
	public void setStage(int id){
		setStage(id,null);
	}
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		super.dispose();
		
		VU.disposeAll(mainScr,stScr);
		VU.disposeAll(am,as);
	}
	
	public void returnToMenu() {
//		if(stScr!=null){
//			stScr.dispose();
//		}
//		if(mainScr!=null){
//			mainScr.dispose();
//		}
//		if(edScr!=null){
//			edScr.dispose();
//		}
//		
		as.stopMusic();
		stScr=new StartScreen(this, gb);
		setScreen(stScr);
	}
}
