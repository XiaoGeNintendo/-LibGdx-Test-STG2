package com.hhs.xgn.stg.replay;

import java.util.ArrayList;
import java.util.Random;

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
	
	public Replay(){
		
	}
	public Replay(Player p) {
//		this.player=p;
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
		
//		System.out.print("REC "+gameTick+":");
		int x=0;
		for(int i=0;i<tgc.length;i++){
			int y=(Gdx.input.isKeyPressed(tgc[i])?1:0);
//			System.out.print(y);
			x|=(1<<i)*y;
		}
//		System.out.println("=>"+x);
		keys.set(gameTick, x);
	}
	
	public static final int Z=0;
	public static final int UP=1;
	public static final int DOWN=2;
	public static final int LEFT=3;
	public static final int RIGHT=4;
	public static final int X=5;
	public static final int SHIFT=6;
	public static final int[] tgc=new int[]{Keys.Z,Keys.UP,Keys.DOWN,Keys.LEFT,Keys.RIGHT,Keys.X,Keys.SHIFT_LEFT};
	
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
	
	/**
	 * The RNG seed <br/>
	 * All randomness related to danmuku should use inherit random
	 */
	public long rng;
	public Random rnd;
	
	public void registerRNG(long seed) {
		if(isReplay){
			return;
		}
		Gdx.app.log("Replay", "RNG seed set to "+seed);
		rnd=new Random(seed);
		rng=seed;
	}
	
	public int callRNG(int bound){
		int val=rnd.nextInt(bound);
		Gdx.app.debug("RNG", "A new int was drawn:"+val+" in bound "+bound);
		return val;
	}
	
	/**
	 * Returns a double rnd althought it's name is called callRNGFloat
	 * @param f
	 * @return
	 */
	public double callRNGF(double f) {
		double val=rnd.nextDouble()*f;
		Gdx.app.debug("RNG-Double", "A new double was drawn:"+val+" in bound "+f);
		return val;
	}
}
