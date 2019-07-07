package com.hhs.xgn.stg.type;

import com.hhs.xgn.stg.launcher.MainScreen;

public class SpellCard {
	
	public float hp;
	public int time;
	public String name;
	
	public MainScreen obj;
	public SpellCard(MainScreen obj,float hp,int time,String name){
		this.obj=obj;
		this.hp=hp;
		this.time=time;
		this.name=name;
	}
	
	public void onEnd(){
		
	}
	public void onFrame(){
		
	}

	public boolean isNonspell() {
		// TODO Auto-generated method stub
		return name.equals("");
	}
}
