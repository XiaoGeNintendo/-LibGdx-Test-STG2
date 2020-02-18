package com.hhs.xgn.stg.struct;

import com.hhs.xgn.stg.launcher.MainScreen;

/**
 * The scripts to define stage behaviour
 * @author XGN
 *
 */
public abstract class StageBuilder implements Cloneable{
	
	/**
	 * Need to be implemented. <br/>
	 * @param ms - the screen obj to operate with
	 * @param tt - the time delta
	 */
	public abstract void onTick(MainScreen ms,float tt);
	
	public abstract String getStageName(MainScreen ms);
	
	public abstract String getStageMusic();
	
	@Override
	public StageBuilder clone() {
		try{
			return (StageBuilder)super.clone();
		}catch(Exception e){
			return null;
		}
	}
}
