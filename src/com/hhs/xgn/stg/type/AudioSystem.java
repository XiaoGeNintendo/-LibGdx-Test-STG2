package com.hhs.xgn.stg.type;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.hhs.xgn.gdx.util.VU;

public class AudioSystem {
	public float SEVolume;
	public float BGMVolume;
	
	public HashMap<String,Sound> music=new HashMap<String,Sound>();
	
	public AudioSystem(){
		SEVolume=1;
		BGMVolume=1;
	}
	
	/**
	 * Plays a sound at "snd/"+name+".wav" at full volume
	 * @param name
	 */
	public void playSound(String name){
		playSound(name,1f);
	}
	
	public void playSound(String name,float vol){
		loadSound("snd/"+name+".wav").play(vol*SEVolume);
	}
	
	
	/**
	 * Must be internal path
	 */
	public Sound loadSound(String path){
		if(!music.containsKey(path)){
			music.put(path, Gdx.audio.newSound(Gdx.files.internal(path)));
		}
		return music.get(path);
	}
	
	public void dispose(){
		for(Entry<String,Sound> en:music.entrySet()){
			en.getValue().dispose();
		}
	}

}
