package com.hhs.xgn.stg.game;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityBullet;

public class ZJSBullet extends EntityBullet {

	public ZJSBullet(MainScreen ms,float angle) {
		super(ms);
		texture="entity/playerbullet.png";
		sx=sy=8;
		
		vx=VU.getVX(8, angle);
		vy=VU.getVY(8, angle);
	}

}
