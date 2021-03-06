package com.hhs.xgn.stg.game;

import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.struct.RenderMode;
import com.hhs.xgn.stg.struct.StageBuilder;
import com.hhs.xgn.stg.type.Boss;
import com.hhs.xgn.stg.type.Dialog;
import com.hhs.xgn.stg.type.SpellCardAction;

public class TestStageBuilder extends StageBuilder {

	int tick,bossc;
	
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
			bossc++;
			if(bossc==2){
				
				ms.setStage(1,ms.p);
				return;
			}
			
			ms.boss=new Boss(ms, "test_boss", 64, 79, 8,new int[]{4,4,4},RenderMode.STOP_MOVES_CAST,"Test Boss",VU.width/2f,300,
					new SpellCardAction(ms,
										new Dialog("bg/frogscbg.png", "採冱ってるのよ壼仲とも舞栂徨とも�[んだんでしょ��\n暴だけ�o��して臨溺が�佞泙襪箸任睨爾辰討襪裡�", "-",null),
										new Dialog("art/reimu.png", "もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´", null,null),
										new Dialog("bg/frogscbg.png","そう、ただの舞�[び、つまりお疾り\n書晩は暴の�ﾄ擦�疾りの桑よ��","boss","Battle Against A True Hero"),
										new Dialog("art/reimu.png","もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´もしかして、念に壼仲や舞栂徨と�蕕辰燭蠅靴燭里辰董�´",null,null)
										),
					new TestNonSpellCard(ms),
					new TestSpellCard(ms),
					new MovingNonSpell(ms),
					new TestRandomCard(ms),
					new TestSpell2(ms),
					new SpellCardAction(ms,
										new Dialog("bg/frogscbg.png","あはははは。爺這れだわ匯藍忽を�Bいたこの暴が、繁�gに��けるとは",null,"XZM Theme"),
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
