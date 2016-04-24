package com.picklegames.gameStates;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.picklegames.TweenAccessor.SpriteTweenAccessor;
import com.picklegames.game.FireFighterGame;
import com.picklegames.managers.GameStateManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

public class SplashScreen extends GameState{
	private Sprite logo;
	
	public SplashScreen(GameStateManager gsm) {
		super(gsm);		
	}

	@Override
	public void init() {
		Tween.registerAccessor(Sprite.class, new SpriteTweenAccessor());
		
		FireFighterGame.res.loadTexture("image/CompanyLogo.png", "logo");
		logo = new Sprite(FireFighterGame.res.getTexture("logo"));
		logo.setSize(cam.viewportWidth, cam.viewportHeight);
		logo.setAlpha(0);
		
		Tween.to(logo, SpriteTweenAccessor.ALPHA, 4f).target(1).ease(TweenEquations.easeNone).start(gsm.getTweenManager());
		Tween.to(logo, SpriteTweenAccessor.ALPHA, 4f).target(0).delay(4.5f).ease(TweenEquations.easeNone).start(gsm.getTweenManager());
	}
	
	@Override
	public void handleInput() {
		
	}
	
	float timeElapsed;
	@Override
	public void update(float dt) {
		timeElapsed += dt;
		if(timeElapsed >= 10){
			gsm.setState(GameStateManager.MENU);
		}
		
	}

	@Override
	public void render() {
		batch.begin();
		logo.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		logo.getTexture().dispose();
		
	}


}
