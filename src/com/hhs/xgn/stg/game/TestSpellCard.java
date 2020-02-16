package com.hhs.xgn.stg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityEnemy;
import com.hhs.xgn.stg.type.EntityBullet;
import com.hhs.xgn.stg.type.SpellCard;

public class TestSpellCard extends SpellCard {
	
	@Override
	public void renderBg(SpriteBatch sb,int t) {
		Texture txt=obj.gm.am.get("bg/frogscbg.png");
		
		t=t%100;
		for(int i=-20;i<=10;i++){
			for(int j=-20;j<=10;j++){
				float x=t+i*50;
				float y=t+j*100;
				sb.draw(txt, x, y,50,100);
			}
		}
	}
	
	public TestSpellCard(MainScreen ms){
		super(ms,1000,20*60,"ÍÁ×ÅÉñ¡¸¥±¥í¤Á¤ã¤óïLÓê¤ËØ“¤±¤º¡¹",true,false,1e5f,200,400,600,800);
		declareCastAnimation=true;
	}
	
	int frameC=0;
	@Override
	public void onFrame() {
//		System.out.println("Running SPell"+frameC);
		frameC++;
		if(frameC%300==0){
			for(int i=0;i<=360;i+=3){
//				System.out.println("Created bullet");
				EntityBullet eeb=new EntityBullet(obj);
				eeb.texture="entity/bullet.png";
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
	
	@Override
	public void onEnd(){
		obj.addItem(new ItemPoint(obj, obj.boss.x-10, obj.boss.y, 5000));
		obj.addItem(new ItemPoint(obj, obj.boss.x+10, obj.boss.y, 5000));
	}
}
