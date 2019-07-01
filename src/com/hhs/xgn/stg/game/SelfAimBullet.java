package com.hhs.xgn.stg.game;

import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityEnemyBullet;

public class SelfAimBullet extends EntityEnemyBullet{

	float time;
	public SelfAimBullet(MainScreen ms,float time,float x,float y) {
		super(ms);
		texture="bullet.png";
		sx=sy=16;
		damage=10;
		this.time=time;
		this.x=x;
		this.y=y;
		
		vx=(ms.p.x-x)/time;
		vy=(ms.p.y-y)/time;
	}
	
}
