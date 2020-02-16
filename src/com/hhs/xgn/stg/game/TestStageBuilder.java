package com.hhs.xgn.stg.game;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.struct.StageBuilder;
import com.hhs.xgn.stg.type.Boss;
import com.hhs.xgn.stg.type.Dialog;
import com.hhs.xgn.stg.type.SpellCardAction;

public class TestStageBuilder extends StageBuilder {

	int tick;
	
	@Override
	public void onTick(MainScreen ms, float tt) {
		if(ms.backgroundC==1){
			ms.gm.as.playBGM("normal",1f);
			ms.displaySongName("XZM Theme");
		}
		
		tick++;
		
		
		//stage middle?? 
		if(tick<=1200){
			if(tick%120==0){
				EnemySelfAim esa=new EnemySelfAim(ms, ms.p.x, VU.height+100);
				ms.addEnemy(esa);
			}
		}else{
			if(ms.renderBoss){
				return;
			}
			ms.boss=new Boss(ms, "entity/enemy.png", 128, 64, "art/reimu.png","Test Boss",VU.width/2f,300,
					new SpellCardAction(ms,
										new Dialog("bg/frogscbg.png", "何言ってるのよ早苗とも神奈子とも遊んだんでしょ？\n私だけ無視して巫女が務まるとでも思ってるの？", "-",null),
										new Dialog("art/reimu.png", "もしかして、前に早苗や神奈子と戦ったりしたのって……", null,null),
										new Dialog("bg/frogscbg.png","そう、ただの神遊び、つまりお祭り\n今日は私の弾幕お祭りの番よ！","boss","Battle Against A True Hero"),
										new Dialog("art/reimu.png","もしかして、前に早苗や神奈子と戦ったりしたのって……もしかして、前に早苗や神奈子と戦ったりしたのって……もしかして、前に早苗や神奈子と戦ったりしたのって……もしかして、前に早苗や神奈子と戦ったりしたのって……",null,null)
										),
					new TestNonSpellCard(ms),
					new TestSpellCard(ms),
					new MovingNonSpell(ms),
					new TestRandomCard(ms),
					new TestSpell2(ms),
					new SpellCardAction(ms,
										new Dialog("bg/frogscbg.png","あはははは。天晴れだわ一王国を築いたこの私が、人間に負けるとは",null,"XZM Theme"),
										new Dialog("art/reimu.png","TODO: Ending","<","XZM Theme")
									   )
					);
			ms.renderBoss=true;
		}
	}

	@Override
	public String getStageName(MainScreen ms) {
		// TODO Auto-generated method stub
		return "Test Stage "+ms.backgroundC;
	}

	@Override
	public String getStageMusic() {
		// TODO Auto-generated method stub
		return "normal";
	}

}
