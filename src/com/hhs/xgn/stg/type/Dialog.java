package com.hhs.xgn.stg.type;

/**
 * A part of the dialog in an action
 * @author XGN
 *
 */
public class Dialog {
	/**
	 * Speaker Art Position 
	 */
	public String speaker="";
	/**
	 * What's the content
	 */
	public String word="No message";
	/**
	 * Should change a BGM? null for no,any name for yes
	 */
	public String music;
	
	
	public Dialog(String speaker, String word, String music) {
		this.speaker = speaker;
		this.word = word;
		this.music = music;
	}
	
	
}
