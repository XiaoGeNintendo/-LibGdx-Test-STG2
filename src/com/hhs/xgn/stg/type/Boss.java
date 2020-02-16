package com.hhs.xgn.stg.type;

import java.lang.annotation.AnnotationTypeMismatchException;
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
import com.hhs.xgn.stg.struct.RenderMode;

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

	float kx, ky,initx,inity;

	public String in;
	
	public Boss(MainScreen obj, String internalName, float sx, float sy,int ANIMDELAY,int[] ANIMMAX, int ANIMMODE,String name, float x, float y,
			SpellCard... cards) {
		super(obj);
		this.kx = sx;
		this.ky = sy;
		this.in = internalName;
		this.name = name;
		this.x = x;
		this.y = y;
		this.initx=x;
		this.inity=y;
		this.ANIMDELAY=ANIMDELAY;
		this.ANIMMAX=ANIMMAX;
		this.ANIMMODE=ANIMMODE;
		
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

			Image im = new Image(obj.gm.am.get("ui/spells.png", Texture.class));
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
		//restore
		x=initx;
		y=inity;
		
		// do some animation

		
		if (currentSpellPointer != -1) {
			getSpell().onEnd();

			obj.gm.as.playSound("explode");
			// System.out.println(obj.spells+" "+currentSpellPointer);
			if (getSpell().isNonspell() || getSpell() instanceof SpellCardAction) {

			} else {
				obj.spells.get(obj.spells.size() - 1).addAction(Actions.sequence(
						Actions.parallel(Actions.alpha(0, 1), Actions.scaleBy(5f, 5f, 1f)), Actions.removeActor()));
				obj.spells.remove(obj.spells.size() - 1);

				//add bonus
				if (!getSpell().isNonspell()) {
					if ((currentTime <= 0 && !getSpell().isTimeSpell) || bonus == false) {
						// bouns failed
						Image failed = new Image(VU.splitUI(obj.gm.am, "bonusfail"));

						failed.setPosition(20, VU.height / 2);
						failed.setScale(0, 1);
						failed.addAction(Actions.sequence(Actions.scaleTo(1, 1, 0.5f), Actions.alpha(0, 5),
								Actions.removeActor()));
						obj.instant.addActor(failed);
					} else {
						// bonus ok
						Image ok = new Image(VU.splitUI(obj.gm.am, "getbonus"));

						ok.setPosition(0, VU.height / 2);
						ok.setScale(0, 1);
						ok.addAction(Actions.sequence(Actions.scaleTo(1, 1, 0.5f), Actions.alpha(0, 5),
								Actions.removeActor()));
						obj.instant.addActor(ok);

						float _bonus = calcCurrentBonus();

						obj.p.point += _bonus;
						Label ok2 = VU
								.createLabel("Break time:" + (getSpell().time - currentTime)/60*1000 + "ms\nBonus:" + _bonus);
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
			Image ta = new Image(obj.gm.am.get(in+"/tachie.png", Texture.class));
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

		}
	}

	public SpellCard getSpell() {
		return nowCard;
	}

	public long last;

	/**
	 * Current Animation Status. As Touhou tradition: <br/>
	 * 0 = standing still <br/>
	 * 1 = moving left <br/>
	 * 2 = moving right/cast 
	 */
	public int animStatus;
	public int animCount;
	/**
	 * A constant. How many frames between texture change. <br/>
	 * But can be changed as like.
	 */
	public int ANIMDELAY=8;
	/**
	 * A constant. The Total Number of Animations this has.
	 */
	public int[] ANIMMAX=new int[]{};
	
	/**
	 * @see com.hhs.xgn.stg.struct.RenderMode
	 */
	public int ANIMMODE=0;
	public int animDelay=ANIMDELAY;
	
	/**
	 * Used for moving detection
	 */
	public float lastx,lasty;
	
	/**
	 * Indicates if a X-flip is needed
	 */
	public boolean flip;
	
	/**
	 * Multi-using variable
	 */
	private int animDelta=0;
	
	public int calcAnimDelta(){
		if(lastx-x>=1e-2){
			return 1;
		}else if(x-lastx>=1e-2){
			return 2;
		}else{
			return 0;
		}
	}
	
	/**
	 * Animation Control
	 */
	public void doAnime(){
		
		//change animation status
		flip=false;
		
		if(animDelta!=calcAnimDelta()){
			animCount=0;
		}
		
		animDelta=calcAnimDelta();
		
//		System.out.println(lastx+" "+x+" "+(lastx-x));
		if(ANIMMODE==RenderMode.STOP_MOVES_CAST){
			if(getSpell()!=null && getSpell().declareCastAnimation){
				animStatus=2;
			}else{
				if(lastx-x>=1e-2){
					animStatus=1;
					flip=true;
				}else if(x-lastx>=1e-2){
					animStatus=1;
					flip=false;
				}else{
					animStatus=0;
					flip=false;
				}
			}
			
			if(animStatus==2 && !(getSpell()!=null && getSpell().declareCastAnimation)){
				animStatus=0;
			}
				
//			System.out.println(animStatus+" "+animCount);
			animDelay--;
			if(animDelay==0){
				animDelay=ANIMDELAY;
				if(animStatus==2){
					//this is cast
					animCount+=animDelta;
					if(animCount==ANIMMAX[animStatus] || animCount==-1){
						animCount-=animDelta;
						animDelta=-animDelta;
					}
				}else if(animStatus==0){
					//this is standing
					animCount++;
					if(animCount==ANIMMAX[animStatus]){
						animCount=0;
					}
				}else{
					//this is moving
					animCount++;
					if(animCount==ANIMMAX[animStatus]){
						animCount--;
					}
				}
			}
		}
		
		
		
		texture=in+"/enemy"+animStatus+"_"+animCount+".png";
	}
	
	@Override
	public void doFrame() {
		
		doAnime();

		lastx=x;
		lasty=y;
		
		animTime--;
		if (animTime > 0) {
			if (animTime % 1 == 0) {
				// create new stuff
				Image im = new Image(obj.gm.am.get("ui/pure.png", Texture.class));
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
		
		if(!(getSpell() instanceof SpellCardAction)){
			currentTime--;
			if (currentTime <= 300 && currentTime % 60 == 0) {
				obj.gm.as.playSound("timeup", 0.2f);
			}
			if (currentTime <= 0) {
				nextSpellCard();
			}
		}
		
	}

	@Override
	public void onHit(Entity ano) {
		if(getSpell() instanceof SpellCardAction || isAppearing() || getSpell().isTimeSpell){
			return;
		}
		currentHp -= 1;
		obj.p.point += 12;
			
		if (currentHp <= 0) {
			nextSpellCard();
		}
	}

	/**
	 * The current spell bonus at the given time
	 * @return
	 */
	public int calcCurrentBonus() {
		if(getSpell().isNonspell() || !bonus){
			return 0;
		}
		return (int) (((getSpell().isTimeSpell?getSpell().time:currentTime) / (float) getSpell().time) * getSpell().maxBonus);
	}
}
