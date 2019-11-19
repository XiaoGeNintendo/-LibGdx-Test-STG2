package com.hhs.xgn.stg.type;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;

public class EntityItem extends Entity {
	public boolean playerMode;
	
	public EntityItem(MainScreen obj,float x,float y){
		super(obj);
		this.x=x;
		this.y=y;
		ay=-0.01f;
		vy=0.5f;
	}
	
	@Override
	public float getCollision() {
		return 5;
	}
	
	public void doFrame(){
		if(playerMode){
			float angle=VU.calcAngle(x,y,obj.p.x,obj.p.y);
			
			vx=VU.getVX(10, angle);
			vy=VU.getVY(10, angle);
			ax=0;
			ay=0;
		}
		
		if(x<-100 || y<-100 || x>VU.width+100 || y>VU.height+100){
			dead=true;
		}
		if(obj.getDist(this, obj.p)<=50 || obj.p.y>VU.height/3*2){
			//change to player mode
			
			playerMode=true;
		}
	}
	
	public void collectItem(){
		dead=true;
	}
	
	public void onHit(Entity e){
		if(e instanceof Player){
			obj.audio.playSound("collect",0.05f);
			collectItem();
		}
	}
}
