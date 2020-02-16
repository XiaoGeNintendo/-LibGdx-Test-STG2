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
										new Dialog("bg/frogscbg.png", "���ԤäƤ�Τ�����Ȥ������ӤȤ��[�����Ǥ��磿\n˽�����oҕ������Ů���դޤ�ȤǤ�˼�äƤ�Σ�", "-",null),
										new Dialog("art/reimu.png", "�⤷�����ơ�ǰ������������Ӥȑ�ä��ꤷ���Τäơ���", null,null),
										new Dialog("bg/frogscbg.png","���������������[�ӡ��Ĥޤꤪ����\n���դ�˽�Ώ�Ļ������η��裡","boss","Battle Against A True Hero"),
										new Dialog("art/reimu.png","�⤷�����ơ�ǰ������������Ӥȑ�ä��ꤷ���Τäơ����⤷�����ơ�ǰ������������Ӥȑ�ä��ꤷ���Τäơ����⤷�����ơ�ǰ������������Ӥȑ�ä��ꤷ���Τäơ����⤷�����ơ�ǰ������������Ӥȑ�ä��ꤷ���Τäơ���",null,null)
										),
					new TestNonSpellCard(ms),
					new TestSpellCard(ms),
					new MovingNonSpell(ms),
					new TestRandomCard(ms),
					new TestSpell2(ms),
					new SpellCardAction(ms,
										new Dialog("bg/frogscbg.png","���ϤϤϤϡ���������һ������B��������˽�������g��ؓ����Ȥ�",null,"XZM Theme"),
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
