package com.hhs.xgn.stg.game;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityEnemy;
import com.hhs.xgn.stg.type.EntityEnemyBullet;
import com.hhs.xgn.stg.type.SpellCard;

public class TestRandomCard extends SpellCard {
	
	public TestRandomCard(MainScreen ms){
		super(ms,1000,20*60,"Flower Spell\n[Cannot Be Seen Forever]",false,true,1e7f);
	}
	
	int frameC=0;
	@Override
	public void onFrame() {
//		System.out.println("Running SPell"+frameC);
		frameC++;
		if(frameC%1==0){
		
			EntityEnemyBullet eeb=new EntityEnemyBullet(obj);
			eeb.texture="entity/bullet.png";
			eeb.sx=10;
			eeb.sy=10;
			
			float angle=VU.easyRandom(0, 360);
			eeb.vx=VU.getVX(3, angle);
			eeb.vy=VU.getVY(3, angle);
			eeb.setPosition(getX(),getY());
			obj.addEnemyBullet(eeb);
		
		}
		super.onFrame();
	}
	
	@Override
	public void onEnd(){
		obj.addItem(new ItemPoint(obj, obj.boss.x-10, obj.boss.y, 5000));
		obj.addItem(new ItemPoint(obj, obj.boss.x+10, obj.boss.y, 5000));
	}
}
