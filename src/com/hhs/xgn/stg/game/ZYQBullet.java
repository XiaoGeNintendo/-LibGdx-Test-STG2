package com.hhs.xgn.stg.game;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityBullet;

public class ZYQBullet extends EntityBullet {

	public ZYQBullet(MainScreen ms,boolean lowspeed) {
		super(ms);
		texture="entity/playerbullet.png";
		sx=sy=8;
		if(!lowspeed){
			float rng= VU.easyRandom(0, 180);
			vx=VU.getVX(5,rng);
			vy=VU.getVY(5, rng);
		}else{
			vy=5;
		}
	}

}
