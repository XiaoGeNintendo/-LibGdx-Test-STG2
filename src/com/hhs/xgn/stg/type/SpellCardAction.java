package com.hhs.xgn.stg.type;

import java.util.ArrayList;

import com.hhs.xgn.stg.launcher.MainScreen;

/**
 * Spell card action is a special type of spell card where you are able to do actions. <br/>
 * Like talking and playing boss-related BGM should be proceeded here <br/>
 * It has an independent system
 * @author XGN
 *
 */
public class SpellCardAction extends SpellCard {
	
	public ArrayList<Dialog> arr=new ArrayList<>();
	public int pointer=0;
	
	public SpellCardAction(MainScreen obj) {
		super(obj, 0,0,"",false);
	}
	
}
