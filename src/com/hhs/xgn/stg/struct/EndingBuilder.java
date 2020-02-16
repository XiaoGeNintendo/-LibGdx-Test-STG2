package com.hhs.xgn.stg.struct;

import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.Player;

public abstract class EndingBuilder extends StageBuilder{

	@Override
	/**
	 * No need to fill
	 */
	public void onTick(MainScreen ms, float tt) {}

	/**
	 * Return the String[] indicating the text of the ending <br/>
	 * special command: <br/>
	 * "!background.png" will change background to "background.png" <br/>
	 * "~music.wav" will change bgm to "music.wav" <br/> 
	 * @param p
	 * @return
	 */
	public abstract String[] getText(Player p);
	
	@Override
	/**
	 * No need to fill
	 */
	public String getStageName(MainScreen ms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * No need to be filled
	 */
	public String getStageMusic(){
		return null;
	}

}
