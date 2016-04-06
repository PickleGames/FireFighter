package com.picklegames.levelStates;


import com.picklegames.game.FireFighterGame;

import com.picklegames.handlers.Dialogue;
import com.picklegames.managers.LevelStateManager;

public class Level3 extends LevelState{
	
	private Dialogue d;
	
	public Level3(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		init();
		FireFighterGame.res.loadTexture("image/Character/momFace.png", "mommy");
		FireFighterGame.res.loadTexture("image/Character/youFace.png", "you");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		d = new Dialogue("dialogue/dialogue.txt");
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
		d.update(dt);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		batch.begin();
			batch.draw(FireFighterGame.res.getTexture("mommy"), 55, 385);
			batch.draw(FireFighterGame.res.getTexture("you"), 55, 75);
			d.render(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		d.dispose();
	}

}
