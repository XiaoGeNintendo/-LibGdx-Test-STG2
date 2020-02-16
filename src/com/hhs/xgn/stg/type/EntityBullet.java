package com.hhs.xgn.stg.type;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.game.ItemPoint;
import com.hhs.xgn.stg.launcher.MainScreen;

public class EntityBullet extends Entity{
	
	public float damage;
	public boolean grazed;
	
	public EntityBullet(MainScreen ms){
		super(ms);
	}
	
	public EntityBullet(MainScreen ms,String texture,float sx,float sy){
		super(ms);
		this.texture=texture;
		this.sx=sx;
		this.sy=sy;
		damage=10f;
	}
	
	@Override
	public void doFrame() {
		if(x<-100 || x>VU.width+100 || y<-100 || y>VU.height+100){
			dead=true;
		}
	}
	
	public void onKill() {
		obj.addItem(new ItemPoint(obj, x, y, 10f));
		super.onKill();
	}
	@Override
	public void onHit(Entity ano) {
		dead=true;
	}

}
