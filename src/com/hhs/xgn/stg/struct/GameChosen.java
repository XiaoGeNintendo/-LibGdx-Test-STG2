package com.hhs.xgn.stg.struct;

import com.hhs.xgn.stg.type.Player;

public class GameChosen {
	public Player chosenPlayer;
	public String chosenDifficulty;
	public GameChosen(){
		
	}
	
	public GameChosen(Player a,String b){
		chosenPlayer=a;
		chosenDifficulty=b;
	}
}
