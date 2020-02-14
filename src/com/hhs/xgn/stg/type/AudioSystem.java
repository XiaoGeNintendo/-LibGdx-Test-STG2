package com.hhs.xgn.stg.type;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class AudioSystem implements Disposable{
	public float SEVolume;
	public float BGMVolume;
	
	public HashMap<String,Sound> sound=new HashMap<String,Sound>();
	
	public HashMap<String,Music> music=new HashMap<String,Music>();
	
	public Music nowPlaying;
	
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
		if(!sound.containsKey(path)){
			sound.put(path, Gdx.audio.newSound(Gdx.files.internal(path)));
		}
		return sound.get(path);
	}
	
	@Override
	public void dispose(){
		for(Entry<String,Sound> en:sound.entrySet()){
			en.getValue().dispose();
		}
		for(Entry<String,Music> en:music.entrySet()){
			en.getValue().dispose();
		}
	}

	public void stopMusic() {
		if(nowPlaying!=null){
			nowPlaying.stop();
		}
	}
	
	/**
	 * Play a bgm at "mus/"+name+".wav" <br/>
	 * @param string
	 */
	public void playBGM(String name,float vol) {
		playBGMPrimitive("mus/"+name+".wav", vol);
	}

	/**
	 * Must be internal path
	 */
	public Music loadMusic(String path){
		if(!music.containsKey(path)){
			music.put(path, Gdx.audio.newMusic(Gdx.files.internal(path)));
		}
		return music.get(path);
	}

	public void playBGMPrimitive(String name, float vol) {
		stopMusic();
		nowPlaying=loadMusic(name);
		nowPlaying.play();
		nowPlaying.setLooping(true);
		nowPlaying.setVolume(vol*BGMVolume);
		
	}

	
}
