package com.picklegames.levelStates;

import com.picklegames.gameStates.Dialogue;
import com.picklegames.managers.LevelStateManager;

public class Level3 extends LevelState{
	
	private Dialogue d;
	
	public Level3(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		init();
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
		d.render(batch);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		d.dispose();
	}

}
