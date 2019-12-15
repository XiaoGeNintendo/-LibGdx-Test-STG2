package com.hhs.xgn.stg.game;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.struct.StageBuilder;
import com.hhs.xgn.stg.type.Boss;
import com.hhs.xgn.stg.type.Dialog;
import com.hhs.xgn.stg.type.SpellCardAction;

public class TestStageBuilder extends StageBuilder {

	@Override
	public void onTick(MainScreen ms, float tt) {
		if(ms.backgroundC==1){
			ms.audio.playBGM("normal",1f);
			ms.displaySongName("XZM Theme");
		}
		
		if(ms.renderBoss==false){
			ms.boss=new Boss(ms, "entity/enemy.png", 128, 64, "art/reimu.png","Test Boss",VU.width/2f,300,
					new SpellCardAction(ms,
										new Dialog("bg/frogscbg.png", "採冱ってるのよ壼仲とも舞栂徨とも�[んだんでしょ��\n暴だけ�o��して臨溺が�佞泙襪箸任睨爾辰討襪裡�", "-",null),
										new Dialog("art/reimu.png", "もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´", null,null),
										new Dialog("bg/frogscbg.png","そう、ただの舞�[び、つまりお疾り\n書晩は暴の�ﾄ擦�疾りの桑よ��","boss","Battle Against A True Hero"),
										new Dialog("art/reimu.png","もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´",null,null)
										),
					new TestNonSpellCard(ms),
					new TestSpellCard(ms),
					new TestRandomCard(ms),
					new SpellCardAction(ms,
										new Dialog("bg/frogscbg.png","あはははは。爺這れだわ匯藍忽を�Bいたこの暴が、繁�gに��けるとは","<","XZM Theme")
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
