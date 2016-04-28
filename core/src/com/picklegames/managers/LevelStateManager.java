package com.picklegames.managers;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.entities.Lamp;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.CameraStyles;
import com.picklegames.handlers.TransitionEffect;
import com.picklegames.levelStates.Level0;
import com.picklegames.levelStates.Level1;
import com.picklegames.levelStates.Level2;
import com.picklegames.levelStates.Level3;
import com.picklegames.levelStates.Level4;
import com.picklegames.levelStates.Level5;
import com.picklegames.levelStates.Level6;
import com.picklegames.levelStates.Level7;
import com.picklegames.levelStates.Level8;
import com.picklegames.levelStates.Level9;
import com.picklegames.levelStates.LevelState;
import com.picklegames.levelStates.Tutorial;

//WE FUCKED
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class LevelStateManager {

	private FireFighterGame game;
	private GameStateManager gsm;
	private Stack<LevelState> levelStates;

	private TransitionEffect te;
	private TweenManager tweenManager;
	private CameraStyles camStyle;
	
	public CameraStyles getCamStyle() {
		return camStyle;
	}

	public void setCamStyle(CameraStyles camStyle) {
		this.camStyle = camStyle;
	}

	private Lamp player;

	public Lamp getPlayer() {
		return player;
	}
	
	public static final int Tutorial = 00;
	public static final int Level_0 = 01;
	public static final int Level_1 = 12;
	public static final int Level_2 = 23;
	public static final int Level_3 = 34;
	public static final int Level_4 = 45;
	public static final int Level_5 = 56;
	public static final int Level_6 = 67;
	public static final int Level_7 = 78;
	public static final int Level_8 = 89;
	public static final int Level_9 = 910;

	public LevelStateManager(FireFighterGame game, GameStateManager gsm) {
		this.game = game;
		this.setGsm(gsm);
		levelStates = new Stack<LevelState>();

		te = new TransitionEffect(game.getCam());
		tweenManager = new TweenManager();
		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());

		player = new Lamp();
		camStyle = new CameraStyles();
		pushState(Level_0);

//		player.setBody(CreateBox2D.createBox(game.getWorld(), 100, 100, player.getWidth() / 2, player.getHeight() / 8,
//				new Vector2(0, -player.getHeight() / 3), BodyType.DynamicBody, "lamp", B2DVars.BIT_PLAYER,
//				B2DVars.BIT_GROUND));
		



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
		
		if (state == Tutorial){
			return new Tutorial(this);
		}else if (state == Level_0) {
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
		} else if (state == Level_7){
			return new Level7(this);
		} else if (state == Level_8){
			return new Level8(this);
		}else if (state == Level_9){
			return new Level9(this);
		}

		return null;

	}

	public void setState(int state) {
		popState();
		pushState(state);
		game.getCam().setToOrtho(false, FireFighterGame.V_WIDTH / FireFighterGame.SCALE, FireFighterGame.V_HEIGHT / FireFighterGame.SCALE);
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
