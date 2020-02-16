package com.hhs.xgn.stg.game;


import com.hhs.xgn.stg.struct.EndingBuilder;
import com.hhs.xgn.stg.type.Player;

public class TestEndingBuilder extends EndingBuilder {

	@Override
	public String[] getText(Player p) {
		if(p.xu){
			return new String[]{
				"!bg/ending.png",
				"~mus/ending.wav",
				"Boss: You are too xun!",
				"",
				"Try completing the level without using credit!"
			};
		}else{
			return new String[]{
				"!bg/ending.png",
				"~mus/ending.wav",
				"Boss: too strong!",
				"",
				"Congratulation!\nYou cleared the game!",
				"~mus/title.mp3",
				"Game by XGN,Music by Zzzyt,HDD292",
				"Some resource from the Internet",
				"Thanks for your playing!"
			}; 
		}
	}


	

}
