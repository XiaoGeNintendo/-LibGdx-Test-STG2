package com.hhs.xgn.stg.type;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
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
	
	public boolean bonus=false;
	
	public boolean isAppearing(){
		return animTime>0;
	}
	float kx,ky;
	
	public String tachie;
	
	public Boss(MainScreen obj,String texture,float sx,float sy,String tachie,String name,float x,float y,SpellCard... cards) {
		super(obj);
		this.kx=sx;
		this.ky=sy;
		this.texture=texture;
		this.name=name;
		this.x=x;
		this.y=y;
		this.tachie=tachie;
		
		
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
			if(!getSpell().isNonspell()){
				if(currentTime<=0 || bonus==false){
					//bouns failed
					Label failed=VU.createLabel("Bonus Failed");
					failed.setPosition(20, VU.height/2);
					failed.addAction(Actions.sequence(Actions.alpha(0,5),Actions.removeActor()));
					obj.instant.addActor(failed);
				}else{
					//bonus ok

					Label ok=VU.createLabel("Get Spellcard Bonus!!");
					ok.setFontScale(0.8f);
					ok.setPosition(20, VU.height/2);
					ok.addAction(Actions.sequence(Actions.alpha(0,5),Actions.removeActor()));
					obj.instant.addActor(ok);
				}
			}
		}
				
		bonus=true;
		if(currentSpellPointer==spells.size()){
			dead=true;
			return;
		}
		
		
		nowCard=spells.get(currentSpellPointer);
		currentHp=getSpell().hp;
		currentTime=getSpell().time;
		
		obj.clearBullet();
		
		if(!getSpell().isNonspell()){
			//is a spell, we need to display information
			Image ta=new Image(obj.am.get(tachie,Texture.class));
			ta.setPosition(-200, 0);
			ta.setWidth(200);
//			ta.setColor(0,0,0,0.5f);
			ta.getColor().a=0.8f;
			ta.addAction(Actions.sequence(
							Actions.moveBy(200,0,1,Interpolation.circleIn),
							Actions.moveBy(50,0,5),
							Actions.moveBy(200, 0,1,Interpolation.circleOut),
							Actions.removeActor()
						));
			
			obj.instant.addActor(ta);
			
			Label sn=VU.createLabel(getSpell().name);
			sn.setPosition(250, 50);
			sn.setFontScale(0.4f);
			sn.getStyle().fontColor=new Color(0,0,1,0.8f);
			
			sn.addAction(Actions.sequence(
							Actions.moveBy(-200,0,1,Interpolation.circleIn),
							Actions.moveBy(-50,0,5),
							Actions.moveBy(-400, 0,1,Interpolation.circleOut),
							Actions.removeActor()
						));
			
			obj.instant.addActor(sn);
			
		}
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
