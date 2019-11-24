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
	 * Whether to show music Name <br/>
	 * null for no
	 */
	public String musicName;
	
	
	/**
	 * Assist var
	 */
	public boolean first;
	
	public Dialog(String speaker, String word, String music,String musName) {
		this.speaker = speaker;
		this.word = word;
		this.music = music;
		first=true;
		musicName=musName;
	}
	
	
}
