package com.picklegames.gameStates;

import com.picklegames.managers.GameStateManager;
import com.picklegames.managers.LevelStateManager;

public class Play extends GameState{

	private LevelStateManager lsm;
	
	public Play(GameStateManager gsm) {
		super(gsm);
		lsm = new LevelStateManager(game, gsm);
	}

	@Override
	public void handleInput() {

		
	}

	@Override
	public void update(float dt) {

		lsm.update(dt);
	}

	@Override
	public void render() {
		lsm.render(batch);
		
	}

	@Override
	public void dispose() {

		
	}

}
