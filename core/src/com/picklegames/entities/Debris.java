package com.picklegames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.Box2D.B2DVars;

public class Debris extends Entity {

	private Texture dFresh;
	private Texture dCrack;
	private Texture dBreak;
	private TextureRegion[] texR;
	private int currentFrame = 0;

	private float health = 3;
	private boolean breakAnimationDone = false;

	public Debris(Body body) {
		super(body);

		FireFighterGame.res.loadTexture("image/Objects/debris.png", "dFresh");
		FireFighterGame.res.loadTexture("image/Objects/debris_crack.png", "dCrack");
		FireFighterGame.res.loadTexture("image/Objects/debris_break.png", "dBreak");

		dFresh = FireFighterGame.res.getTexture("dFresh");
		dCrack = FireFighterGame.res.getTexture("dCrack");
		dBreak = FireFighterGame.res.getTexture("dBreak");

		texR = TextureRegion.split(dFresh, 64, 64)[0];
		width = texR[0].getRegionWidth() * 3;
		height = texR[0].getRegionHeight() * 3;

		animation.setFrames(texR);

		debrisState = DebrisState.FRESH;

	}

	public enum DebrisState {

		FRESH, CRACK, BREAK
	}

	public DebrisState debrisState;

	public void update(float dt) {
		super.update(dt);

		if (debrisState.equals(DebrisState.CRACK)) {
			animation.setCurrentFrame(currentFrame);

		} else if (debrisState.equals(DebrisState.BREAK)) {
			if (animation.hasPlayedOnce()) {
				breakAnimationDone = true;
			}
		}
		//System.out.println(debrisState);
		if (getHealth() <= 0 && !debrisState.equals(DebrisState.BREAK)) {
			debrisState = DebrisState.BREAK;
			resetAnimation();
		}

	}

	public void doHit() {
		if(getHealth() > 0 && debrisState.equals(DebrisState.FRESH)) {
			debrisState = DebrisState.CRACK;
			resetAnimation();
		}
		if (debrisState.equals(DebrisState.CRACK)) {
			if (currentFrame < texR.length) {
				health--;
				currentFrame++;
			}
		}
	}

	public void render(SpriteBatch batch) {
		batch.draw(animation.getFrame(), body.getPosition().x * B2DVars.PPM - width / 2,
				body.getPosition().y * B2DVars.PPM - height / 2, width, height);
	}

	public void resetAnimation() {

		if (debrisState.equals(DebrisState.FRESH)) {
			texR = TextureRegion.split(dFresh, 64, 64)[0];
			animation.setFrames(texR);
		} else if (debrisState.equals(DebrisState.CRACK)) {
			texR = TextureRegion.split(dCrack, 64, 64)[0];
			animation.setFrames(texR);
		} else {
			texR = TextureRegion.split(dBreak, 64, 64)[0];
			animation.setFrames(texR, 1 / 8f);
		}
	}

	public boolean isInRadius(float x2, float y2, float radius) {
		return findDistance(x2, y2) < radius;
	}

	public float findDistance(float x2, float y2) {
		return (float) Math
				.sqrt(Math.abs(Math.pow((x2 - body.getPosition().x), 2) + Math.pow((y2 - body.getPosition().y), 2)));

	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public boolean isBreakAnimationDone() {
		return breakAnimationDone;
	}

	public void dipose() {
		super.dispose();
	}
}
