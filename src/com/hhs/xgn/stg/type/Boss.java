package com.hhs.xgn.stg.type;

import java.util.ArrayList;
import java.util.Arrays;

import com.hhs.xgn.stg.launcher.MainScreen;

public class Boss extends Entity{

	public ArrayList<SpellCard> spells=new ArrayList<>();
	public int currentSpellPointer;
	
	public String name;
	
	public SpellCard nowCard;
	
	public Boss(MainScreen obj,String texture,float sx,float sy,String name,float x,float y,SpellCard... cards) {
		super(obj);
		this.sx=sx;
		this.sy=sy;
		this.texture=texture;
		this.name=name;
		this.x=x;
		this.y=y;
		
		last=System.currentTimeMillis();
		nowCard=null;
		currentSpellPointer=-1;
		for(SpellCard sc:cards){
			this.spells.add(sc);
		}
		nextSpellCard();
		
	}
	
	@Override
	public float getCollision() {
		return 16;
	}
	public float currentHp;
	public long currentTime;
	
	public void nextSpellCard(){
		currentSpellPointer++;
		if(currentSpellPointer==spells.size()){
			dead=true;
			return;
		}
		nowCard=spells.get(currentSpellPointer);
		currentHp=getSpell().hp;
		currentTime=getSpell().time;
		
		//do some animation
	}
	
	public SpellCard getSpell(){
		return nowCard;
	}
	
	public long last;
	
	@Override
	public void doFrame(){
//		System.out.println("Running"+currentTime);
		getSpell().onFrame();
		long now=System.currentTimeMillis();
		currentTime-=now-last;
		last=now;
		if(currentTime<=0){
			nextSpellCard();
		}
	}
	
	@Override
	public void onHit(Entity ano){
		if(ano instanceof EntityPlayerBullet){
			currentHp-=obj.p.atk;
			obj.p.point+=obj.p.atk*1.2f;
			
			if(currentHp<=0){
				nextSpellCard();
			}
			
			
		}
	}
}
