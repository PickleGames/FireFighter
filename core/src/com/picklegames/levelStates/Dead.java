package com.picklegames.levelStates;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.picklegames.game.FireFighterGame;
import com.picklegames.managers.LevelStateManager;

public class Dead extends LevelState{

	private Sprite tex;
	
	public Dead(LevelStateManager lsm) {
		super(lsm);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		FireFighterGame.res.loadTexture("image/Backgrounds/dead.png", "dead");
		
		
		tex = new Sprite(FireFighterGame.res.getTexture("dead"));
		tex.setSize(cam.viewportWidth, cam.viewportHeight / 2);
		
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		batch.begin();
		tex.draw(batch);
		batch.end();
		
	}

	@Override
	public void dispose() {
		FireFighterGame.res.disposeTexture("dead");
	}

}
