package com.hhs.xgn.stg.type;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;

public class EntityEnemyBullet extends Entity{
	
	public float damage;
	
	public EntityEnemyBullet(MainScreen ms){
		super(ms);
	}
	
	@Override
	public void doFrame() {
		if(x<-100 || x>VU.width+100 || y<-100 || y>VU.height+100){
			dead=true;
		}
	}
	
	@Override
	public void onHit(Entity ano) {
		if(ano instanceof Player){
			dead=true;
		}
	}
}
