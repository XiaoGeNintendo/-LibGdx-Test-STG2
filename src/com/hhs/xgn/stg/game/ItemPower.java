package com.hhs.xgn.stg.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityItem;

public class ItemPower extends EntityItem {

	float value;
	public ItemPower(MainScreen obj, float x, float y,float value) {
		super(obj, x, y);
		texture="entity/power.bmp";
		sx=sy=16;
		this.value=value;
	}

	@Override
	public void collectItem() {
		obj.p.atk=Math.min(obj.p.atk+value,5); //example of setting the max power
		
		obj.p.point+=10;
		
		Label l=VU.createLabel("+"+value+" Atk");
		l.setFontScale(0.5f);
		l.getStyle().fontColor=Color.YELLOW;
		l.addAction(Actions.sequence(Actions.parallel(Actions.moveBy(0, 100,5),Actions.alpha(0,5)),Actions.removeActor()));
		l.setPosition(x, y,Align.center);
		
		obj.instant.addActor(l);
		
		super.collectItem();
	}
}
