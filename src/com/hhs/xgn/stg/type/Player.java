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

public class Player extends Entity{
	
	final int frameCount=3;
	
	int nowframe=0;
	
	public float atk,def,hp;

	public int spell;
	
	public int deadTime;
	
	public int graze,point;
	
	@Override
	public void onHit(Entity ano) {
		if(ano instanceof EntityEnemyBullet){
			EntityEnemyBullet eeb=(EntityEnemyBullet) ano;
			float realD=Math.max(0, eeb.damage-def);
			hp-=realD;
			
			deadTime=10;
			for(int i=0;i<5;i++){
				obj.addItem(new ItemPower(obj, x+VU.easyRandom(-50, 50), y+30, Math.max(0.4f,(atk-1)/5-0.1f)));
			}
			atk=1;
			

			Label hplost=VU.createLabel(realD+"");
			hplost.getStyle().fontColor=Color.GREEN;
			hplost.addAction(Actions.sequence(Actions.alpha(0,2),Actions.removeActor()));
			hplost.setPosition(x+VU.easyRandom(-getCollision()/2,getCollision()/2), y+VU.easyRandom(-getCollision()/2,getCollision()/2),Align.center);
			obj.instant.addActor(hplost);
			
			x=VU.width/2;
			y=0;
			
		}
	}
	@Override
	public float getCollision() {
		return 4;
	}
	
	public Player(MainScreen obj){
		super(obj,"player1.png",VU.width/2,VU.height/5,32,32);
		hp=100;
		atk=def=1;
		spell=3;
	}
	
	int curFrame;
	
	@Override
	public void doFrame() {
		curFrame++;
		if(curFrame>=10){
			nowframe++;
			if(nowframe>=frameCount){
				nowframe-=frameCount;
			}
			
			texture="player"+(nowframe+1)+".png";
			curFrame=0;
		}
		
		op();
	}
	
	public final static float SPEED=2.5f;
	public final static int DELAY=1;
	
	private int wait;
	
	private void op(){
		wait++;
		
		float SPEED=Player.SPEED;
		
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
			SPEED/=2; //shift speed
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			y+=SPEED;
			y=Math.min(y, VU.height); 
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			y-=SPEED;
			y=Math.max(y, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			x-=SPEED;
			x=Math.max(x, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			x+=SPEED;
			x=Math.min(x, VU.width);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			if(wait>DELAY){
				wait=0;
				
				
				int xx=(int) Math.floor(atk);
				
				for(int i=0;i<xx/2;i++){
					obj.addPlayerBullet(x-5*i, y);
					obj.addPlayerBullet(x+5*i, y);
				}
				
				if(xx%2==1){
					obj.addPlayerBullet(x,y);
				}
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
			if(deadTime==0 && spell>0){
				deadTime=180;
				spell--;
			}
		}
	}
}
