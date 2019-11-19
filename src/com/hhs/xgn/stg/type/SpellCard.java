package com.hhs.xgn.stg.type;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hhs.xgn.stg.launcher.MainScreen;

public class SpellCard {
	
	public float hp;
	public int time;
	public String name;
	public MainScreen obj;
	
	public boolean useSpecialRender;
	
	public void renderBg(SpriteBatch sb,int frame){
		
	}
	
	public SpellCard(MainScreen obj,float hp,int time,String name,boolean specialRender){
		this.obj=obj;
		this.hp=hp;
		this.time=time;
		this.name=name;
		this.useSpecialRender=specialRender;
	}
	
	public void onEnd(){
		
	}
	public void onFrame(){
		
	}

	public float getX(){
		return obj.boss.x;
	}
	
	public float getY(){
		return obj.boss.y;
	}
	
	public boolean isNonspell() {
		// TODO Auto-generated method stub
		return name.equals("");
	}
}
