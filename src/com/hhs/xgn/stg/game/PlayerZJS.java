package com.hhs.xgn.stg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.replay.Replay;
import com.hhs.xgn.stg.type.Player;

public class PlayerZJS extends Player{
	
	public PlayerZJS(MainScreen obj) {
		super(obj,"zjs","ZJS","Governor"
				+ "\nCan shoot normally"
				+ "\nHigh-speed Bullet:Normal Shot"
				+ "\nSlow-speed Bullet:NB Shot"
				+ "\nMove speed: Normal",3);
		
	}

	private int wait;
	private int tick;
	private final static float SPEED=3;
	private final static float DELAY=1;
	
	@Override
	public void op() {
		wait++;
		tick++;
		
		float SPEED=PlayerZJS.SPEED;
		
		boolean ss=obj.rep.isKeyPressed(Replay.SHIFT);
		if(ss){
			SPEED/=2; //shift speed
		}
		
		if(obj.rep.isKeyPressed(Replay.UP)){
			y+=SPEED;
			y=Math.min(y, VU.height); 
		}
		if(obj.rep.isKeyPressed(Replay.DOWN)){
			y-=SPEED;
			y=Math.max(y, 0);
		}
		if(obj.rep.isKeyPressed(Replay.LEFT)){
			x-=SPEED;
			x=Math.max(x, 0);
		}
		if(obj.rep.isKeyPressed(Replay.RIGHT)){
			x+=SPEED;
			x=Math.min(x, VU.width);
		}
		if(obj.rep.isKeyPressed(Replay.Z)){
			
			if(!obj.isShowingDialog() && wait>DELAY){
				wait=0;
				
				obj.gm.as.playSound("shoot",0.05f);
				
				if(ss){
					int xx=(int) Math.floor(atk);
					
					for(int i=0;i<25*xx;i++){
						obj.addPlayerBullet(x, y, new ZJSBullet(obj,(180f/5/xx*i+tick)%180));
					}
					
				}else{
					int xx=(int) Math.floor(atk);
					
					for(int i=0;i<25*xx;i++){
						obj.addPlayerBullet(x, y, new ZJSBullet(obj,360f/5/xx*i+tick));
					}
				}
				
			}
		}
		if(obj.rep.isKeyJustPressed(Replay.X)){
			if(!obj.isShowingDialog() && deadTime==0 && spell>0){
				obj.bonusFailed();
				
				obj.gm.as.playSound("bomb",1f);
				deadTime=180;
				spell--;
				
			}
		}
	}
}
