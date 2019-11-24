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
	 * Should change a BGM? <br/>
	 * null - don't change music, <br/>
	 * "-" - stop music, <br/> 
	 * "<" - plays stage music <br/>
	 * anything else - play music <br/>
	 */
	public String music;
	
	/**
	 * Assist var
	 */
	public boolean first;
	
	
	public Dialog(String speaker, String word, String music) {
		this.speaker = speaker;
		this.word = word;
		this.music = music;
		first=true;
	}
	
	
}
