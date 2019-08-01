package com.hhs.xgn.stg.launcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hhs.xgn.gdx.util.VU;

public class Launcher {
	public static LwjglApplicationConfiguration config;
	public static LwjglApplication game;
	
	public static void main(String[] args) {
		config=new LwjglApplicationConfiguration();
		
		config.width=VU.width+200;
		config.height=VU.height;
		
		config.fullscreen=false;
		config.resizable=false;
		
		game=new LwjglApplication(new GameMain(),config);
	}
}
