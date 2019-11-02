package com.hhs.xgn.stg.type;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;

public class EntityEnemy extends Entity{
	public float hp;
	
	public EntityEnemy(MainScreen obj,String texture,float hp,float x,float y,float sx,float sy){
		super(obj,texture,x,y,sx,sy);
		this.hp=hp;
	}
	
	public float getCollision() {
		return Math.min(sx, sy);
	}
	
	@Override
	public void doFrame(){
		if(x<-100 || x>VU.width+100 || y<-100 || y>VU.height+100){
			dead=true;
		}
	}
	
	@Override
	public void onHit(Entity ano) {
		if(ano instanceof EntityPlayerBullet){
			
			hp-=1;
			obj.p.point+=10;
			
			if(hp<=0){
				dead=true;
				onKill();
			}
			
			Label hplost=VU.createLabel(hp+"");
			hplost.setFontScale(0.5f,0.5f);
			hplost.getStyle().fontColor=Color.RED;
			hplost.addAction(Actions.sequence(Actions.alpha(0,2),Actions.removeActor()));
			hplost.setPosition(x+VU.easyRandom(-getCollision()/2,getCollision()/2), y+VU.easyRandom(-getCollision()/2,getCollision()/2),Align.center);
			obj.instant.addActor(hplost);
		}
	}
}
