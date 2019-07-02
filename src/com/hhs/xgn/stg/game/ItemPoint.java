package com.hhs.xgn.stg.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;
import com.hhs.xgn.stg.type.EntityItem;

public class ItemPoint extends EntityItem {

	float value;
	public ItemPoint(MainScreen obj, float x, float y,float value) {
		super(obj, x, y);
		texture="point.bmp";
		sx=sy=16;
		this.value=value;
	}

	@Override
	public void collectItem() {
		obj.p.point+=value;
		
		Label l=VU.createLabel(value+"");
		l.setFontScale(0.5f);
		l.getStyle().fontColor=Color.BLUE;
		l.addAction(Actions.sequence(Actions.parallel(Actions.moveBy(0, 100,5),Actions.alpha(0,5)),Actions.removeActor()));
		l.setPosition(x, y,Align.center);
		
		obj.instant.addActor(l);
		
		super.collectItem();
	}
}
