package com.hhs.xgn.stg.replay;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.hhs.xgn.stg.type.Player;

public class Replay {
	/**
	 * The player that is playing the game.
	 */
	public Player player;
	
	/**
	 * false = now recording replay <br/>
	 * true = now playing replay
	 */
	public boolean isReplay;
	
	/**
	 * The game tick replay is current on
	 */
	int gameTick;
	
	/**
	 * keys[x]= the bitmask of keys that is pressed on X tick
	 */
	public ArrayList<Integer> keys=new ArrayList<>();

	/**
	 * Used credits. Cannot record replay
	 */
	public boolean fail;

	public long time;
	
	public int sId;
	
	public Replay(Player p) {
		this.player=p;
		this.isReplay=false;
	}

	public void update(int gt){ 
		this.gameTick=gt;
		while(keys.size()<=gt){
			keys.add(0);
		}
	}
	
	public void record(){
		if(fail){
			return;
		}
		if(isReplay){
			return;
		}
		
		int x=0;
		for(int i=0;i<tgc.length;i++){
			int y=(Gdx.input.isKeyPressed(tgc[i])?1:0);
			x|=(1<<i)&y;
		}
		
		keys.set(gameTick, x);
	}
	public static final int Z=0;
	public static final int UP=1;
	public static final int DOWN=2;
	public static final int LEFT=3;
	public static final int RIGHT=4;
	public static final int X=5;
	public static final int[] tgc=new int[]{Keys.Z,Keys.UP,Keys.DOWN,Keys.LEFT,Keys.RIGHT,Keys.X};
	
	public boolean isKeyPressed(int kCode){
		if(isReplay){
			return (keys.get(gameTick)&(1<<kCode))==0;
		}else{
			return Gdx.input.isKeyPressed(tgc[kCode]); 
		}
	}
	
	public boolean isKeyJustPressed(int kCode){
		if(isReplay){
			boolean b1=(keys.get(gameTick)&(1<<kCode))==0;
			boolean b2=gameTick==0 || (keys.get(gameTick-1)&(1<<kCode))==0;
			
			return (b1&&!b2);
		}else{
			return Gdx.input.isKeyJustPressed(tgc[kCode]); 
		}
	}
}
