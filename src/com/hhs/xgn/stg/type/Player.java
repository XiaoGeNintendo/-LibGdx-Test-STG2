package com.hhs.xgn.stg.type;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.game.ItemPower;
import com.hhs.xgn.stg.launcher.MainScreen;

/**
 * The super class of all Player instance
 * @author XGN
 *
 */
public class Player extends Entity implements Cloneable{
	
	/**
	 * The number of frames Player have. <br/>
	 * Resource stored at in/playerX.png
	 */
	int frameCount;
	
	int nowframe=0;
	
	public float atk,def,hp;

	public int spell;
	
	public int deadTime;
	
	public int graze,point;
	
	/**
	 * The interal name for the player. Used to show graphics
	 */
	public String in;
	/**
	 * The display name and description
	 */
	public String dn,desc;
	
	/**
	 * Did the character use XuGuan(credit)
	 */
	public boolean xu;
	
	@Override
	public void onHit(Entity ano) {
		if(ano instanceof EntityBullet){
			EntityBullet eeb=(EntityBullet) ano;
			float realD=Math.max(0, eeb.damage-def);
			hp-=realD;
			
			deadTime=10;
			for(int i=0;i<5;i++){
				obj.addItem(new ItemPower(obj, x+VU.easyRandom(-50, 50), y+30, Math.max(0.4f,(atk-1)/5-0.1f)));
			}
			atk=1;
			spell=3;
			
			obj.bonusFailed();
			
			x=VU.width/2;
			y=0;
			
		}
	}
	
	@Override
	public Player clone(){
		try{
			return (Player)super.clone();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	/**
	 * Recomment to implement
	 */
	public float getCollision() {
		return 4;
	}
	
	public Player(MainScreen obj,String interName,String displayName,String description,int frameCount){
		super(obj,interName+"/player1.png",VU.width/2,VU.height/5,32,32);
		hp=100;
		atk=def=1;
		spell=3;
		in=interName;
		dn=displayName;
		desc=description;
		this.frameCount=frameCount;
	}
	
	int curFrame;
	
	@Override
	/**
	 * This should NOT be overrode.
	 */
	public void doFrame() {
		curFrame++;
		if(curFrame>=10){
			nowframe++;
			if(nowframe>=frameCount){
				nowframe-=frameCount;
			}
			
			texture=in+"/player"+(nowframe+1)+".png";
			curFrame=0;
		}
		
		op();
	}
	
	/**
	 * This MUST be overrode
	 */
	public void op(){
		//waiting to be overrode by son.
	}
}
