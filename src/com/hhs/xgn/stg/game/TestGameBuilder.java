package com.hhs.xgn.stg.game;

import com.hhs.xgn.stg.struct.GameBuilder;

public class TestGameBuilder extends GameBuilder {
	public TestGameBuilder(){
		self.add(new PlayerZYQ(null));
		self.add(new PlayerZJS(null));
		diffs.add("Easy");
		diffs.add("Lunatic");
//		stage.add(new TestStageBuilder());
		stage.add(new TestEndingBuilder());
	}
}
