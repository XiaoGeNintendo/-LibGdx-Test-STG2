package com.hhs.xgn.stg.type;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;

public class EntityItem extends Entity {
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
		if(x<-100 || y<-100 || x>VU.width+100 || y>VU.height+100){
			dead=true;
		}
		if(obj.getDist(this, obj.p)<=50 || obj.p.y>VU.height/3*2){
			//change to player mode
			
			vx=(obj.p.x-x)/30;
			vy=(obj.p.y-y)/30;
			while(Math.sqrt(vx*vx+vy*vy)<=5){
				vx*=2;
				vy*=2;
			}
			
			ax=0;
			ay=0;
		}
	}
	
	public void collectItem(){
		dead=true;
	}
	
	public void onHit(Entity e){
		if(e instanceof Player){
			collectItem();
		}
	}
}
