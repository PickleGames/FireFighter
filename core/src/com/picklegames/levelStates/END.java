package com.picklegames.levelStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.picklegames.game.FireFighterGame;
import com.picklegames.gameStates.GameState;
import com.picklegames.managers.GameStateManager;
import com.picklegames.managers.LevelStateManager;

public class END extends LevelState{
	private Sprite tex;
	
	public END(LevelStateManager lsm) {
		super(lsm);

	}

	@Override
	public void init() {
	
		FireFighterGame.res.loadTexture("image/Backgrounds/end.png", "end");
		
		tex = new Sprite(FireFighterGame.res.getTexture("end"));
		
		tex.setSize(cam.viewportWidth,cam.viewportHeight);
		tex.setPosition(0, 0);
		System.out.println(cam.viewportWidth + 50);
		System.out.println(cam.viewportHeight);
		
		cam.position.set(cam.viewportWidth / 2 , cam.viewportHeight /2 , 0);
		
		cam.update();
		
		//FireFighterGame.res.loadMusic("sound/Dialogue 6.mp3", "d_6");
		
		
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			lsm.getGsm().setState(GameStateManager.MENU);
		}
		
//		if (Gdx.input.isKeyPressed(Keys.Q)) {
//			cam.viewportHeight += 10;
//			cam.viewportWidth += 10;
//		} else if (Gdx.input.isKeyPressed(Keys.E)) {
//			cam.viewportHeight -= 10;
//			cam.viewportWidth -= 10;
//		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		System.out.println("cam width: " + cam.viewportWidth);
		System.out.println("cam height: " +cam.viewportHeight);
		
//		if(!FireFighterGame.res.getMusic("d_6").isPlaying()){
//			FireFighterGame.res.getMusic("d_6").play();
//		}
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		//batch.setTransformMatrix(cam.combined);
		batch.setProjectionMatrix(cam.combined);
		cam.update();
		
		batch.begin();
		tex.draw(batch);
		batch.end();
		
	}

	@Override
	public void dispose() {
		FireFighterGame.res.disposeTexture("dead");
	}


}
