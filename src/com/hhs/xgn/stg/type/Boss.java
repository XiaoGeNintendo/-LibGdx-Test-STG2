package com.hhs.xgn.stg.type;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hhs.xgn.gdx.util.VU;
import com.hhs.xgn.stg.launcher.MainScreen;

public class Boss extends Entity {

	public ArrayList<SpellCard> spells = new ArrayList<>();
	public int currentSpellPointer;

	public String name;

	public SpellCard nowCard;

	int animTime = 300;

	public boolean bonus = false;

	public boolean isAppearing() {
		return animTime > 0;
	}

	float kx, ky;

	public String tachie;

	public Boss(MainScreen obj, String texture, float sx, float sy, String tachie, String name, float x, float y,
			SpellCard... cards) {
		super(obj);
		this.kx = sx;
		this.ky = sy;
		this.texture = texture;
		this.name = name;
		this.x = x;
		this.y = y;
		this.tachie = tachie;

		nowCard = null;
		currentSpellPointer = -1;
		for (SpellCard sc : cards) {
			this.spells.add(sc);
		}
		nextSpellCard();

		sx = sy = 0;

		for (Image s : obj.spells) {
			s.addAction(Actions.removeActor());
		}

		int has=0;
		
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].isNonspell() || cards[i] instanceof SpellCardAction) {
				continue;
			}

			Image im = new Image(obj.am.get("ui/spells.png", Texture.class));
			im.setOrigin(Align.center);
			im.setPosition(has * 16, VU.height - 37);
			im.setSize(16, 16);
			im.getColor().a=0;
			im.addAction(Actions.sequence(Actions.delay(5),Actions.fadeIn(0.3f)));
			obj.spells.add(im);
			obj.instant.addActor(im);
			
			has++;
		}
	}

	@Override
	public float getCollision() {
		return 16;
	}

	public float currentHp;
	public int currentTime;

	public void nextSpellCard() {
		// do some animation

		if (currentSpellPointer != -1) {
			getSpell().onEnd();

			obj.audio.playSound("explode");
			// System.out.println(obj.spells+" "+currentSpellPointer);
			if (getSpell().isNonspell() || getSpell() instanceof SpellCardAction) {

			} else {
				obj.spells.get(obj.spells.size() - 1).addAction(Actions.sequence(
						Actions.parallel(Actions.alpha(0, 1), Actions.scaleBy(5f, 5f, 1f)), Actions.removeActor()));
				obj.spells.remove(obj.spells.size() - 1);

				if (!getSpell().isNonspell()) {
					if (currentTime <= 0 || bonus == false) {
						// bouns failed
						Image failed = new Image(VU.splitUI(obj.am, "bonusfail"));

						failed.setPosition(20, VU.height / 2);
						failed.setScale(0, 1);
						failed.addAction(Actions.sequence(Actions.scaleTo(1, 1, 0.5f), Actions.alpha(0, 5),
								Actions.removeActor()));
						obj.instant.addActor(failed);
					} else {
						// bonus ok
						Image ok = new Image(VU.splitUI(obj.am, "getbonus"));

						ok.setPosition(0, VU.height / 2);
						ok.setScale(0, 1);
						ok.addAction(Actions.sequence(Actions.scaleTo(1, 1, 0.5f), Actions.alpha(0, 5),
								Actions.removeActor()));
						obj.instant.addActor(ok);

						float _bonus = (currentTime / (float) getSpell().time) * 1e5f;

						obj.p.point += _bonus;
						Label ok2 = VU
								.createLabel("Break time:" + (getSpell().time - currentTime) + "ms\nBonus:" + _bonus);
						ok2.setFontScale(0.75f);
						ok2.getStyle().fontColor = Color.PURPLE;
						ok2.setPosition(0, VU.height / 2 - 50);
						ok2.addAction(Actions.sequence(Actions.alpha(0, 5), Actions.removeActor()));
						obj.instant.addActor(ok2);
					}
				}
			}

		}

		currentSpellPointer++;
		bonus = true;
		if (currentSpellPointer == spells.size()) {
			dead = true;
			return;
		}

		nowCard = spells.get(currentSpellPointer);
		currentHp = getSpell().hp;
		currentTime = getSpell().time;

		obj.clearBullet();

		if (!getSpell().isNonspell()) {
			// is a spell, we need to display information
			Image ta = new Image(obj.am.get(tachie, Texture.class));
			ta.setPosition(-200, 0);
			ta.setWidth(200);
			// ta.setColor(0,0,0,0.5f);
			ta.getColor().a = 0.8f;
			ta.addAction(Actions.sequence(Actions.moveBy(200, 0, 1, Interpolation.circleIn), Actions.moveBy(50, 0, 5),
					Actions.moveBy(VU.width, 0, 1, Interpolation.circleOut), Actions.removeActor()));

			obj.instant.addActor(ta);

			Label sn = VU.createLabel(getSpell().name, "pixel.fnt");
			sn.setPosition(250, 50);
			sn.setFontScale(0.4f);
			sn.getStyle().fontColor = new Color(0, 0, 1, 0.8f);

			sn.addAction(Actions.sequence(Actions.moveBy(-200, 0, 1, Interpolation.circleIn), Actions.moveBy(-50, 0, 5),
					Actions.moveBy(-VU.width, 0, 1, Interpolation.circleOut), Actions.removeActor()));

			obj.instant.addActor(sn);

			// //ten desires
			// for(float i=2f;i>=1.5f;i-=0.1f){
			// Label warn=VU.createLabel("Spell Card Attack");
			// warn.setPosition(VU.width-120,-300);
			// warn.getStyle().fontColor=new Color(1,0,0,1f);
			// warn.setFontScale(0.4f);
			// warn.addAction(Actions.sequence(
			// Actions.moveTo(VU.width-120, VU.height-100,i),
			// Actions.delay(2f-i),
			// Actions.moveBy(-300, 0,2),
			// Actions.removeActor()
			// ));
			// obj.instant.addActor(warn);
			// }
		}
	}

	public SpellCard getSpell() {
		return nowCard;
	}

	public long last;

	@Override
	public void doFrame() {
		animTime--;
		if (animTime > 0) {
			if (animTime % 1 == 0) {
				// create new stuff
				Image im = new Image(obj.am.get("ui/pure.png", Texture.class));
				im.setColor(Color.BLACK);
				im.setPosition(VU.easyRandom(-400, 400), VU.easyRandom(-400, 400));
				im.addAction(Actions.sequence(Actions.moveTo(x, y, VU.easyRandom(0.1f, animTime / 60f)),
						Actions.removeActor()));

				obj.instant.addActor(im);
			}
			return;
		}
		if (animTime == 0) {
			last = System.currentTimeMillis();

			sx = kx;
			sy = ky;
		}

		// System.out.println("Running"+currentTime);
		getSpell().onFrame();
		currentTime--;
		if (currentTime <= 300 && currentTime % 60 == 0) {
			obj.audio.playSound("timeup", 0.2f);
		}
		if (currentTime <= 0) {
			nextSpellCard();
		}
	}

	@Override
	public void onHit(Entity ano) {
		if (ano instanceof EntityPlayerBullet || ano == null) { // for bomb=null
			currentHp -= 1;
			obj.p.point += 12;

			if (currentHp <= 0) {
				nextSpellCard();
			}

		}
	}
}
