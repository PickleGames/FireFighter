package com.picklegames.levelStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Bounce;

public class Level2 extends LevelState {

	private TweenManager tweenManager;
	private BitmapFont font;

	public Level2(LevelStateManager lsm) {
		super(lsm);
		init();

	}


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
				System.out.println("min " + p.getEmitters().get(0).getGravity().getHighMin());
				System.out.println("max " + p.getEmitters().get(0).getGravity().getHighMax());
				//p.getEmitters().get(0).getGravity().setHighMin(-500);
				//p.getEmitters().get(0).getGravity().setHighMax(-1000);
				Tween.to(p, ParticleEffectTweenAccessor.GRAVITY, 1f).target(-500, -1000).ease(TweenEquations.easeNone).start(tweenManager);
				Tween.to(p, ParticleEffectTweenAccessor.LIFE, 1f).delay(0f).target(0, 0).ease(TweenEquations.easeNone).start(tweenManager);
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
