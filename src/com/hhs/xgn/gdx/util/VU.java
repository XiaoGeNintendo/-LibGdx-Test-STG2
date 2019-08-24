package com.hhs.xgn.gdx.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

/**
 * The Util Class.
 * @author XGN
 */

public class VU {
	
	/**Global width*/
	public static int width=420;
	/**Global height*/
	public static int height=400;
	/**RIght UI Width*/
	public static int rightWidth=250;
	
	public static void setMiddle(Actor a,int ww,int hh){
		a.setSize(ww, hh);
		a.setPosition(width/2-a.getWidth()/2,height/2-a.getHeight()/2);	
	}
	
	/**
	 * Set the actor to the hor of width and ver of height <br/>
	 * like setTo(a,0.5f,0.5f)=setMiddle(a)
	 * @param a
	 * @param hor
	 * @param ver
	 */
	public static void setTo(Actor a,float hor,float ver){
		float expectX=(width*hor);
		float expectY=(height*ver);
		a.setPosition(expectX-a.getWidth()/2, expectY-a.getHeight()/2);
	}
	
	public static Label createLabel(String s){
		return createLabel(s,"zjs.fnt");
	}
	
	static HashMap<String,BitmapFont> fontmap=new HashMap<>();
	
	public static Label createLabel(String s,String font){
		LabelStyle ls=new LabelStyle();
		
		BitmapFont bf=fontmap.get(font);
		
		if(bf==null){
			bf=new BitmapFont(Gdx.files.internal("font/"+font));
			fontmap.put(font, bf);
		}
		ls.font=bf;
		ls.fontColor=Color.BLACK;
		Label lb=new Label(s,ls);
		return lb;
	}
	
	public static Button createButton(Texture down,Texture up,int sx,int sy){
		ButtonStyle bs=new ButtonStyle();
		bs.down=new TextureRegionDrawable(new TextureRegion(down));
		bs.up=new TextureRegionDrawable(new TextureRegion(up));
		Button b=new Button(bs);
		b.setSize(sx, sy);
		return b;
	}
	
	public static Button createButton(Texture down,Texture up,int x,int y,int sx,int sy){
		ButtonStyle bs=new ButtonStyle();
		bs.down=new TextureRegionDrawable(new TextureRegion(down));
		bs.up=new TextureRegionDrawable(new TextureRegion(up));
		Button b=new Button(bs);
		b.setPosition(x, y);
		b.setSize(sx, sy);
		return b;
	}
	
	public static void clear(float r,float g,float b,float a){
		Gdx.gl.glClearColor(r,g,b,a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static void setMiddle(Actor actor) {
		setMiddle(actor,(int) actor.getWidth(), (int)actor.getHeight());
	}

	/**
	 * Easy render the stage
	 * @param s
	 */
	public static void render(Stage s) {
		VU.clear(0, 0, 0, 1);
		s.act();
		s.draw();
		
	}

	/**
	 * Easy render the stage with rgba
	 * @param s
	 */
	public static void render(Stage s,float r,float g,float b,float a) {
		VU.clear(r, g, b, a);
		s.act();
		s.draw();
		
	}
	
	public static void disposeAll(Screen... ss) {
		for(Screen s:ss){
			//System.out.println("DisposeAll:"+s);
			if(s!=null){
				s.dispose();
			}
		}
	}
	
	public static void disposeAll(Disposable... ss) {
		for(Disposable s:ss){
			//System.out.println("DisposeAll:"+s);
			if(s!=null){
				s.dispose();
			}
		}
	}

	/**
	 * Check if a point is in a actor
	 * @param x
	 * @param y
	 * @param actor
	 * @return
	 */
	public static boolean pointInActor(float x, float y, Actor actor) {
		Rectangle r=new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		return (r.contains(x,y));
		
	}

	/**
	 * Doesn't work. Don't use
	 * @param obj
	 */
	@SuppressWarnings("unused")
	@Deprecated
	public static void setNull(Object... obj) {
		for(Object o:obj){
			o=null;
		}
	}

	public static void addActor(Stage stage, Actor... actors) {
		for(Actor a:actors){
			stage.addActor(a);
		}
	}

	public static float easyRandom(float l, float r) {
		Random rad=new Random();
		return rad.nextFloat()*(r-l)+l;
	}

	public static float getVX(float speed, float angle) {
		return (float) (Math.cos(Math.toRadians(angle))*speed);
	}
	
	public static float getVY(float speed, float angle) {
		return (float) (Math.sin(Math.toRadians(angle))*speed);
	}

	public static ArrayList<RegionInfo> ris;
	
	/**
	 * In order to fit LuaStg <br/>
	 * uses Lua name scheme
	 */
	public static void LoadImage(String a,String b,int c,int d,int e,int f){
		ris.add(new RegionInfo(b,a,c,d,e,f));
	}
	
	public static void initRIS(){
		ris=new ArrayList<>();
		LoadImage("bonusfail","ui_com",0,64,256,64);
		LoadImage("getbonus","ui_com",0,128,396,64);
		LoadImage("extend","ui_com",0,192,160,64);
		LoadImage("power","ui_com",0,12,84,32);
		LoadImage("graze","ui_com",86,12,74,32);
		LoadImage("point","ui_com",160,12,120,32);
		LoadImage("life","ui_com",288,0,16,15);
		LoadImage("lifeleft","ui_com",304,0,16,15);
		LoadImage("bomb","ui_com",320,0,16,16);
		LoadImage("bombleft","ui_com",336,0,16,16);
		LoadImage("kill_time","ui_com",232,200,152,56);
	}
	
	/**
	 * Must be present!
	 * @param base
	 * @param name
	 * @return
	 */
	public static RegionInfo searchForRegion(String base,String name){
		for(RegionInfo ri:ris){
			if(ri.belongs.equals(base) && ri.name.equals(name)){
				return ri;
			}
		}
		
		throw new RuntimeException("Cannot find region");
	}
	
	public static TextureRegion splitUI(AssetManager am, String string) {
		if(ris==null){
			initRIS();
		}
		
		Texture t=am.get("ui_com.png",Texture.class);
		
		
		RegionInfo ri=searchForRegion("ui_com",string);
		
		return new TextureRegion(t,ri.x,ri.y,ri.width,ri.height);
	}
	
}
