package com.picklegames.levelStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;

public class Level0 extends LevelState {
	private ParticleEffect pe;

	public Level0(LevelStateManager lsm) {
		super(lsm);

	}

	@Override
	public void init() {
		pe = new ParticleEffect();
		pe.load(Gdx.files.internal("Particles/SuperHotFire.par"), Gdx.files.internal(""));
		pe.getEmitters().first().setPosition(cam.viewportWidth / 2, cam.viewportHeight / 2);

		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());
	}

	@Override
	public void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			pe.start();
		}

	}

	private float timeElapsed;

	@Override
	public void update(float dt) {
		handleInput();

		timeElapsed += dt;
		if (timeElapsed >= 3) {
			//Tween.to(pe, ParticleEffectTweenAccessor.GRAVITY, 2f).target(-1000, -2000)
			//		.ease(TweenEquations.easeOutExpo).start(lsm.getTweenManager());
			
			// pe.getEmitters().first().duration = 0;
		}

		System.out.println(timeElapsed);
//		System.out.println("gravity :" + pe.getEmitters().first().getGravity().getHighMax());
//		System.out.println("duration: " + pe.getEmitters().first().duration);
		System.out.println("isFinished: " + pe.isComplete());
		pe.update(dt);

	}

	@Override
	public void render() {
		batch.begin();
		pe.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
