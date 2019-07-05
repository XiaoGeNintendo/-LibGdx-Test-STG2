package com.hhs.xgn.stg.type;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;

public class Boss extends Entity{

	public ArrayList<SpellCard> spells=new ArrayList<>();
	public int currentSpellPointer;
	
	public String name;
	
	public SpellCard nowCard;
	
	int animTime=300;
	
	public boolean isAppearing(){
		return animTime>0;
	}
	float kx,ky;
	public Boss(MainScreen obj,String texture,float sx,float sy,String name,float x,float y,SpellCard... cards) {
		super(obj);
		this.kx=sx;
		this.ky=sy;
		this.texture=texture;
		this.name=name;
		this.x=x;
		this.y=y;
		
		
		nowCard=null;
		currentSpellPointer=-1;
		for(SpellCard sc:cards){
			this.spells.add(sc);
		}
		nextSpellCard();
		
		sx=sy=0;
		
	}
	
	@Override
	public float getCollision() {
		return 16;
	}
	public float currentHp;
	public long currentTime;
	
	public void nextSpellCard(){
		currentSpellPointer++;
		
		//do some animation
		if(currentSpellPointer!=0){
			if(currentTime<=0){
				//bouns failed
				Label failed=VU.createLabel("Bonus Failed");
				failed.setPosition(20, VU.height/2);
				failed.addAction(Actions.sequence(Actions.alpha(0,5),Actions.removeActor()));
				obj.instant.addActor(failed);
			}
		}
				
		if(currentSpellPointer==spells.size()){
			dead=true;
			return;
		}
		
		
				
		nowCard=spells.get(currentSpellPointer);
		currentHp=getSpell().hp;
		currentTime=getSpell().time;
		
		
	}
	
	public SpellCard getSpell(){
		return nowCard;
	}
	
	public long last;
	
	@Override
	public void doFrame(){
		animTime--;
		if(animTime>0){
			if(animTime%1==0){
				//create new stuff
				Image im=new Image(obj.am.get("pure.png",Texture.class));
				im.setColor(Color.CYAN);
				im.setPosition(VU.easyRandom(-300, 300), VU.easyRandom(-300, 300));
				im.addAction(Actions.sequence(
							Actions.moveTo(x, y,VU.easyRandom(0.1f, animTime/60f)),
							Actions.removeActor()));
				
				obj.instant.addActor(im);
			}
			return;
		}
		if(animTime==0){
			last=System.currentTimeMillis();

			sx=kx;
			sy=ky;
		}
		
		
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
