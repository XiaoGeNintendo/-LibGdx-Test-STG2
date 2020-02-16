package com.hhs.xgn.stg.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;

public class SpellCard {
	
	public float hp;
	public int time;
	public String name;
	public MainScreen obj;
	
	public boolean useSpecialRender;
	
	/**
	 * Enable time spell: the spell can only be completed by reaching time
	 */
	public boolean isTimeSpell;
	/**
	 * Enable splits: show different stages in a spell 
	 */
	public int[] splits;
	
	/**
	 * The bonus player will get if breaks in 0ms
	 */
	public float maxBonus=1e5f;
	/**
	 * Set to true if the boss should be in cast animation
	 */
	public boolean declareCastAnimation;
	
	public void renderBg(SpriteBatch sb,int frame){
		
	}
	
	/**
	 * A function to cast a visual magic
	 */
	public void castMagic(){
		int density=100;
		obj.gm.as.playSound("enemybomb");
		for(int i=0;i<density;i++){
			Image im=new Image(obj.gm.am.get("ui/pure.png",Texture.class));
			im.setPosition(getX(), getY());
			
			im.addAction(Actions.sequence(Actions.moveTo(VU.easyRandom(0, VU.width), VU.easyRandom(0, VU.height),1),
										  Actions.alpha(0,1),
										  Actions.removeActor()));
			
			obj.instant.addActor(im);
		}
	}
	
	/**
	 * A function to cast a visual magic warning
	 */
	public void castMagicWarning(){
		int density=100;
		obj.gm.as.playSound("enemybootup");
		for(int i=0;i<density;i++){
			Image im=new Image(obj.gm.am.get("ui/pure.png",Texture.class));
			im.setPosition(VU.easyRandom(0, VU.width), VU.easyRandom(0, VU.height));
			
			im.addAction(Actions.sequence(Actions.moveTo(getX(), getY(),1),
										  Actions.alpha(0,1),
										  Actions.removeActor()));
			
			obj.instant.addActor(im);
		}
	}
	/**
	 * Full Constructor V2
	 */
	public SpellCard(MainScreen ms,float hp, int time, String name, boolean useSpecialRender, boolean isTimeSpell,float maxBonus,int... splits) {
		this.obj=ms;
		this.hp = hp;
		this.time = time;
		this.name = name;
		this.useSpecialRender = useSpecialRender;
		this.isTimeSpell = isTimeSpell;
		this.splits = splits;
		this.maxBonus=maxBonus;
	}

	/**
	 * Easy constructor V2
	 */
	public SpellCard(MainScreen ms,float hp, int time, String name,boolean useSpecialRender, boolean isTimeSpell) {
		this.obj=ms;
		this.hp = hp;
		this.time = time;
		this.name = name;
		this.useSpecialRender = useSpecialRender;
		this.isTimeSpell = isTimeSpell;
		this.splits=new int[0];
	}
	
	/**
	 * Easy constructor V3
	 */
	public SpellCard(MainScreen ms,float hp, int time, String name,boolean useSpecialRender, boolean isTimeSpell,float maxBonus) {
		this.obj=ms;
		this.hp = hp;
		this.time = time;
		this.name = name;
		this.useSpecialRender = useSpecialRender;
		this.isTimeSpell = isTimeSpell;
		this.splits=new int[0];
		this.maxBonus=maxBonus;
	}
	/**
	 * Old constructor
	 * @param obj
	 * @param hp
	 * @param time
	 * @param name
	 * @param specialRender
	 */
	public SpellCard(MainScreen obj,float hp,int time,String name,boolean specialRender){
		this.obj=obj;
		this.hp=hp;
		this.time=time;
		this.name=name;
		this.useSpecialRender=specialRender;
		this.isTimeSpell=false;
		this.splits=new int[0];
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
