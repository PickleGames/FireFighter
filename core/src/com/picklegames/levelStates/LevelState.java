package com.picklegames.levelStates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.picklegames.game.FireFighterGame;
import com.picklegames.managers.LevelStateManager;

public abstract class LevelState {

	protected LevelStateManager lsm;
	protected FireFighterGame game;

	protected SpriteBatch batch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;

	public LevelState(LevelStateManager lsm) {
		this.lsm = lsm;
		game = lsm.game();
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
