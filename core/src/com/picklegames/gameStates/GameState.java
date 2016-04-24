package com.picklegames.gameStates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.picklegames.game.FireFighterGame;
import com.picklegames.managers.GameStateManager;

public abstract class GameState {

	protected GameStateManager gsm;
	protected FireFighterGame game;

	protected SpriteBatch batch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		game = gsm.game();
		batch = game.getBatch();
		cam = game.getCam();
		hudCam = game.getHudCam();
		init();
	}
	
	public abstract void init();
	
	public abstract void handleInput();

	public abstract void update(float dt);

	public abstract void render();

	public abstract void dispose();
}
