package com.hhs.xgn.stg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.Player;

public class PlayerZYQ extends Player{
	
	public PlayerZYQ(MainScreen obj) {
		super(obj,"zyq","ZYQ","A fast boy?"
				+ "\nCan move super fast"
				+ "\nHigh-speed Bullet:Random Shot"
				+ "\nSlow-speed Bullet:Concentrated Shot"
				+ "\nMove speed: Fast",3);
		
	}

	private int wait;
	private final static float SPEED=4;
	private final static float DELAY=2;
	
	@Override
	public void op() {
		wait++;
		
		float SPEED=PlayerZYQ.SPEED;
		
		boolean ss=Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
		if(ss){
			SPEED/=2; //shift speed
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			y+=SPEED;
			y=Math.min(y, VU.height); 
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			y-=SPEED;
			y=Math.max(y, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			x-=SPEED;
			x=Math.max(x, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			x+=SPEED;
			x=Math.min(x, VU.width);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			
			if(!obj.isShowingDialog() && wait>DELAY){
				wait=0;
				
				obj.audio.playSound("shoot",0.05f);
				
				if(ss){
					int xx=(int) Math.floor(atk);
					
					for(int i=0;i<xx+1;i++){
						obj.addPlayerBullet(x-10*i, y,new ZYQBullet(obj,true));
						obj.addPlayerBullet(x+10*i, y,new ZYQBullet(obj,true));
						
					}
					
				}else{
					int xx=(int) Math.floor(atk);
					
					for(int i=0;i<xx+1;i++){
						obj.addPlayerBullet(x, y,new ZYQBullet(obj,false));
					}
				}
				
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
			if(!obj.isShowingDialog() && deadTime==0 && spell>0){
				obj.bonusFailed();
				
				obj.audio.playSound("bomb",1f);
				deadTime=180;
				spell--;
				
			}
		}
	}
}
