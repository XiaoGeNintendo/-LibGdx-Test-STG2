package com.hhs.xgn.stg.type;

import com.hhs.xgn.stg.launcher.MainScreen;

public class Entity {
	public float vx,vy;
	public float x,y;
	public float ax,ay;
	
	/**
	 * Size of the texture to br rendered
	 */
	public float sx,sy;
	
	public String texture;
	
	public MainScreen obj;
	
	public boolean dead;
	
	public Entity(MainScreen obj){
		this.obj=obj;
	}
	
	public Entity(MainScreen obj,String texture,float x,float y,float sx,float sy){
		this.obj=obj;
		this.texture=texture;
		this.x=x;
		this.y=y;
		this.sx=sx;
		this.sy=sy;
	}
	
	public float getCollision(){
		return 0f;
	}
	
	public void onHit(Entity ano){
		
	}
	
	public void updateSpeed(){
		x+=vx;
		y+=vy;
		vx+=ax;
		vy+=ay;
	}
	
	public void setPosition(float nx,float ny){
		x=nx;
		y=ny;
	}
	public void setPosition(double nx,double ny){
		x=(float)nx;
		y=(float)ny;
	}
	
	public void onFrame(){
		updateSpeed();
		doFrame();
	}
	
	/**
	 * Frame Logic should be write here
	 */
	public void doFrame(){
		
	}
	
	
	public void onDel(){
		dead=true;
	}
	
	public void onKill(){
		dead=true;
	}
}
