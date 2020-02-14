package com.hhs.xgn.stg.game;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityBullet;
import com.hhs.xgn.stg.type.SpellCard;

public class MovingNonSpell extends SpellCard {
	public MovingNonSpell(MainScreen ms){
		super(ms,1000,1000,"",false);
	}
	
	int frameC=0;
	boolean left=true; //currently going left
	
	@Override
	public void onFrame() {
		frameC++;
		
		if(frameC<=200){
			return;
		}
		
		if(frameC%180==0){
			left=!left;
		}
		
		if(frameC%5==0){
			SelfAimBullet sab=new SelfAimBullet(obj, 60, getX(), getY());
			obj.addEnemyBullet(sab);
		}
		
		if(left){
			obj.boss.x--;
		}else{
			obj.boss.x++;
		}
		super.onFrame();
	}
	
	@Override
	public void onEnd(){
		obj.addItem(new ItemPower(obj, obj.boss.x, obj.boss.y, 5));
	}
}
