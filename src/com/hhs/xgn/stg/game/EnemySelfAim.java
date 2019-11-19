package com.hhs.xgn.stg.game;

import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityEnemy;

public class EnemySelfAim extends EntityEnemy {

	public EnemySelfAim(MainScreen obj,float x, float y) {
		super(obj, "entity/enemy.png", 40, x, y, 32,32);
		vy=-1;
	}

	
	public float getCollision() {
		return 10;
	}
	
	@Override
	public void onKill() {
		
		obj.addItem(new ItemPower(obj, x, y,0.5f));
		super.onKill();
	}
	
	int fc=0;
	public void doFrame(){
		fc++;
		if(fc>=60){
			obj.addEnemyBullet(new SelfAimBullet(obj, 60, x, y));
			fc=0;
		}
	}
}
