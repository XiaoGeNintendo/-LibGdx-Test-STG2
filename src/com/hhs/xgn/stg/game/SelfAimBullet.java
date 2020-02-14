package com.hhs.xgn.stg.game;

import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityBullet;

public class SelfAimBullet extends EntityBullet{

	float time;
	public SelfAimBullet(MainScreen ms,float time,float x,float y) {
		super(ms);
		texture="entity/bullet.png";
		sx=sy=16;
		damage=10;
		this.time=time;
		this.x=x;
		this.y=y;
		
		vx=(ms.p.x-x)/time;
		vy=(ms.p.y-y)/time;
	}
	
}
