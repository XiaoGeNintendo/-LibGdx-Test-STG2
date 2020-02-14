package com.hhs.xgn.stg.struct;

import java.util.ArrayList;

import com.hhs.xgn.stg.type.Player;

/**
 * A structure class to build a game 
 * @author XGN
 *
 */
public abstract class GameBuilder {
	
	/**
	 * Stages
	 */
	public ArrayList<StageBuilder> stage;
	/**
	 * Players
	 */
	public ArrayList<Player> self;
	/**
	 * Difficulties
	 */
	public ArrayList<String> diffs;
	
	public GameBuilder(){
		stage=new ArrayList<>();
		self=new ArrayList<>();
		diffs=new ArrayList<>();
		
	}
}
