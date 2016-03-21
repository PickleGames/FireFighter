package com.picklegames.levelStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class Level2 extends LevelState {

	private TweenManager tweenManager;
	private BitmapFont font;

	public Level2(LevelStateManager lsm) {
		super(lsm);
		init();

	}

	private float segment = Gdx.graphics.getWidth() / 10;

	@Override
	public void init() {
		tweenManager = new TweenManager();
		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());

		font = new BitmapFont();

	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	private float timeElapsed;


	@Override
	public void update(float dt) {
		timeElapsed += dt;

		if (timeElapsed >= 3) {
			for (ParticleEffect p : lsm.getTe().getEffectList()) {
				Tween.to(p, ParticleEffectTweenAccessor.GRAVITY, 2).target(-500, -1000).ease(TweenEquations.easeInCubic).start(tweenManager);
			}

		}

		tweenManager.update(dt);

	}

	@Override
	public void render() {

		font.draw(batch, "Level 2, time: " + timeElapsed, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
