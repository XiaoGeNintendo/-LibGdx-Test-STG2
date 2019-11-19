package com.hhs.xgn.stg.game;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityEnemyBullet;
import com.hhs.xgn.stg.type.EntityItem;
import com.hhs.xgn.stg.type.SpellCard;

public class TestNonSpellCard extends SpellCard {
	public TestNonSpellCard(MainScreen ms){
		super(ms,1000,300,"",false);
	}
	
	int frameC=0;
	@Override
	public void onFrame() {
//		System.out.println("Running SPell"+frameC);
		frameC++;
		if(frameC%60==0){
			for(int i=frameC/6;i<=frameC/6+360;i+=10){
//				System.out.println("Created bullet");
				EntityEnemyBullet eeb=new EntityEnemyBullet(obj);
				eeb.x=obj.boss.x;
				eeb.y=obj.boss.y;
				
				eeb.vx=VU.getVX(1,i);
				eeb.vy=VU.getVY(1,i);
//				System.out.println(eeb.vx+" "+eeb.vy);
				eeb.texture="bullet.png";
				eeb.sx=eeb.sy=8;
				
				obj.addEnemyBullet(eeb);
			}
		}
		super.onFrame();
	}
	
	@Override
	public void onEnd(){
		obj.addItem(new ItemPower(obj, obj.boss.x, obj.boss.y, 5));
	}
}
