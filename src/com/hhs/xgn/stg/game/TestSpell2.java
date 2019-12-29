package com.hhs.xgn.stg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityEnemy;
import com.hhs.xgn.stg.type.EntityEnemyBullet;
import com.hhs.xgn.stg.type.SpellCard;

public class TestSpell2 extends SpellCard {
	
	@Override
	public void renderBg(SpriteBatch sb,int t) {
		Texture txt=obj.am.get("bg/frogscbg.png");
		
		t=t%100;
		for(int i=-20;i<=10;i++){
			for(int j=-20;j<=10;j++){
				float x=t+i*50;
				float y=t+j*100;
				sb.draw(txt, x, y,50,100);
			}
		}
	}
	
	public TestSpell2(MainScreen ms){
		super(ms,20000,120*60,"¡¸Placeholder¡¹",true,false,1e5f);
	}
	
	int frameC=0;
	@Override
	public void onFrame() {
//		System.out.println("Running SPell"+frameC);
		frameC++;
		
		if(frameC%1==0){
			EntityEnemyBullet eeb=new EntityEnemyBullet(obj);
			
			eeb.setPosition(getX(), getY());
			float angle=VU.easyRandom(0, 360);
			eeb.vx=VU.getVX(1, angle);
			eeb.vy=VU.getVY(1, angle);
			eeb.texture="entity/bullet.png";
			eeb.sx=eeb.sy=16;
			
			obj.addEnemyBullet(eeb);
		}
		
		if(frameC%120==0 && frameC%240!=0){
			castMagicWarning();
		}
		
		if(frameC%240==0){
			castMagic();
			
			for(EntityEnemyBullet eeb:obj.groupEnemyBullet){
				eeb.ax=-eeb.vx/100f;
				eeb.ay=-eeb.vy/100f;
				eeb.vx=eeb.vy=0;
			}
		}
		
		super.onFrame();
	}
	
	@Override
	public void onEnd(){
		obj.addItem(new ItemPoint(obj, obj.boss.x-10, obj.boss.y, 5000));
		obj.addItem(new ItemPoint(obj, obj.boss.x+10, obj.boss.y, 5000));
	}
}
