package com.picklegames.managers;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.entities.Lamp;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.TransitionEffect;
import com.picklegames.handlers.Box2D.B2DVars;
import com.picklegames.handlers.Box2D.CreateBox2D;
import com.picklegames.levelStates.Level0;
import com.picklegames.levelStates.Level1;
import com.picklegames.levelStates.Level2;
import com.picklegames.levelStates.Level3;
import com.picklegames.levelStates.Level4;
import com.picklegames.levelStates.Level5;
import com.picklegames.levelStates.Level6;
import com.picklegames.levelStates.LevelState;

//WE FUCKED
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class LevelStateManager {

	private FireFighterGame game;
	private GameStateManager gsm;
	private Stack<LevelState> levelStates;

	private TransitionEffect te;
	private TweenManager tweenManager;

	private Lamp player;

	public Lamp getPlayer() {
		return player;
	}

	public static final int Level_0 = 01;
	public static final int Level_1 = 12;
	public static final int Level_2 = 23;
	public static final int Level_3 = 34;
	public static final int Level_4 = 45;
	public static final int Level_5 = 56;
	public static final int Level_6 = 67;
	

	public LevelStateManager(FireFighterGame game, GameStateManager gsm) {
		this.game = game;
		this.setGsm(gsm);
		levelStates = new Stack<LevelState>();

		te = new TransitionEffect();
		tweenManager = new TweenManager();
		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());

		player = new Lamp();
		player.setBody(CreateBox2D.createBox(game.getWorld(), 100, 100, player.getWidth() / 2, player.getHeight() / 8,
				new Vector2(0, -player.getHeight() / 3), "lamp", B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));
		player.createWeapon();

		pushState(Level_1);


	}

	public FireFighterGame game() {
		return game;
	}

	public void update(float dt) {
		levelStates.peek().update(dt);
		te.update(dt);
		tweenManager.update(dt);

	}

	public void render(SpriteBatch batch) {

		levelStates.peek().render();

		batch.begin();
		te.render(batch);
		batch.end();

	}

	private LevelState getState(int state) {
		if (state == Level_0) {
			return new Level0(this);
		} else if (state == Level_1) {
			return new Level1(this);
		} else if (state == Level_2) {
			return new Level2(this);
		} else if (state == Level_3) {
			return new Level3(this);
		} else if (state == Level_4) {
			return new Level4(this);
		} else if (state == Level_5) {
			return new Level5(this);
		} else if (state == Level_6) {
			return new Level6(this);
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
