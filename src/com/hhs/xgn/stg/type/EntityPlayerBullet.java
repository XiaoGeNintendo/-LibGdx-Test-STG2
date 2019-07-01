package com.hhs.xgn.stg.type;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;

public class EntityPlayerBullet extends Entity{
	public EntityPlayerBullet(MainScreen obj){
		super(obj,"playerbullet.png",obj.p.x,obj.p.y,10,10);
		vy=5;
	}
	
	public void doFrame() {
		if(y>VU.height){
			dead=true;
		}
	}
	
	
	public void onHit(Entity ano) {
		// TODO Auto-generated method stub
		super.onHit(ano);
		dead=true;
	}
	
	@Override
	public float getCollision() {
		// TODO Auto-generated method stub
		return 10;
	}
}
