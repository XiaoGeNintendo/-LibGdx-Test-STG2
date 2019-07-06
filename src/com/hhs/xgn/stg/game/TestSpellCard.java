package com.hhs.xgn.stg.game;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityEnemy;
import com.hhs.xgn.stg.type.EntityEnemyBullet;
import com.hhs.xgn.stg.type.SpellCard;

public class TestSpellCard extends SpellCard {
	public TestSpellCard(MainScreen ms){
		super(ms,1000,20*1000,"Star Spell\n[HIDDEN STARS IN FOUR SEASONS]");
	}
	
	int frameC=0;
	@Override
	public void onFrame() {
//		System.out.println("Running SPell"+frameC);
		frameC++;
		if(frameC%60==0){
			for(int i=0;i<=360;i+=2){
//				System.out.println("Created bullet");
				EntityEnemyBullet eeb=new EntityEnemyBullet(obj);
				eeb.texture="bullet.png";
				eeb.sx=8;
				eeb.sy=8;
				
				double r=Math.abs(Math.sin(Math.toRadians(5*i+frameC/60*11)))*50;
				eeb.setPosition(Math.sin(Math.toRadians(i))*r+obj.boss.x,Math.cos(Math.toRadians(i))*r+obj.boss.y);
				eeb.vx=VU.getVX(0.2f, 270-i+180);
				eeb.vy=VU.getVY(0.2f, 270-i+180);
				obj.addEnemyBullet(eeb);
			}
		}
		super.onFrame();
	}
}
