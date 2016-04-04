package com.picklegames.managers;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.TransitionEffect;
import com.picklegames.levelStates.Level1;
import com.picklegames.levelStates.Level2;
import com.picklegames.levelStates.Level3;
import com.picklegames.levelStates.Level4;
import com.picklegames.levelStates.LevelState;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class LevelStateManager {

	private FireFighterGame game;
	private GameStateManager gsm;
	private Stack<LevelState> levelStates;

	private TransitionEffect te;
	private boolean isTeActivated = false;
	private TweenManager tweenManager;
	
	public static final int Level_1 = 12;
	public static final int Level_2 = 23;
	public static final int Level_3 = 34;
	public static final int Level_4 = 45;

	public LevelStateManager(FireFighterGame game, GameStateManager gsm) {
		this.game = game;
		this.setGsm(gsm);
		levelStates = new Stack<LevelState>();

		te = new TransitionEffect();
		tweenManager = new TweenManager();
		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());
		
		pushState(Level_4);
	}

	public FireFighterGame game() {
		return game;
	}

	public void update(float dt) {
		levelStates.peek().update(dt);
		
		tweenManager.update(dt);
		if (isTeActivated) {
			te.update(dt);
		}
	}

	public void render() {
		
		levelStates.peek().render();
		
		
		game.getBatch().begin();
		if (isTeActivated) {
			te.render(game.getBatch());
		}
		game.getBatch().end();
	

	}

	private LevelState getState(int state) {
		if (state == Level_1) {
			return new Level1(this);
		} else if (state == Level_2) {
			return new Level2(this);
		} else if (state == Level_3){
			return new Level3(this);
		}else if (state == Level_4){
			return new Level4(this);
		}
		return null;

	}

	public void setState(int state) {
		popState();
		pushState(state);
	}

	public void pushState(int state) {
		levelStates.push(getState(state));
	}

	public void popState() {
		LevelState g = levelStates.pop();
		g.dispose();
	}

	public Stack<LevelState> getLevelStates() {
		return levelStates;
	}

	public boolean isTeActivated() {
		return isTeActivated;
	}

	public void setTeActivated(boolean isTeActivated) {
		this.isTeActivated = isTeActivated;
	}
	
	public TransitionEffect getTe() {
		return te;
	}

	public TweenManager getTweenManager() {
		return tweenManager;
	}

	public GameStateManager getGsm() {
		return gsm;
	}

	public void setGsm(GameStateManager gsm) {
		this.gsm = gsm;
	}


}
