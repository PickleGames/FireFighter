package com.picklegames.levelStates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class Level1 extends LevelState{
	
	private ArrayList<ParticleEffect> effectList;

	private TweenManager tweenManager;
	private BitmapFont font; 
	public Level1(LevelStateManager lsm) {
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
		
		if(timeElapsed >= 4){
			lsm.setTeActivated(true);
		}
		if(timeElapsed >= 6){
			lsm.setState(LevelStateManager.Level_2);
			
		}
		
//		if(Gdx.input.isKeyPressed(Keys.SPACE)){
//			effect.start();
//			Tween.set(effect, ParticleEffectTweenAccessor.HEIGHT).target(0).start(tweenManager);
//			Tween.to(effect, ParticleEffectTweenAccessor.HEIGHT, 1).target(2000).ease(TweenEquations.easeInCubic).start(tweenManager);
//
//		}
//		
		
		
	}

	@Override
	public void render() {
		font.draw(batch, "Level 1, time: " + timeElapsed, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		font.dispose();
		
		
	}

}
