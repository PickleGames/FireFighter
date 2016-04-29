package com.picklegames.levelStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.picklegames.game.FireFighterGame;
import com.picklegames.managers.LevelStateManager;

public class Dead extends LevelState{

	private Sprite tex;
	private BitmapFont font;
	
	public Dead(LevelStateManager lsm) {
		super(lsm);

	}

	@Override
	public void init() {
	
		FireFighterGame.res.loadTexture("image/Backgrounds/dead.png", "dead");
		
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.getData().scale(2);
		tex = new Sprite(FireFighterGame.res.getTexture("dead"));
		
		tex.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		tex.setPosition(0, 0);
		System.out.println(cam.viewportWidth);
		System.out.println(cam.viewportHeight);
		
		cam.position.set(cam.viewportWidth / 2 , cam.viewportHeight /2 , 0);
		
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			lsm.setState(lsm.getLastLevel());
		}
		
		if (Gdx.input.isKeyPressed(Keys.Q)) {
			cam.viewportHeight += 10;
			cam.viewportWidth += 10;
		} else if (Gdx.input.isKeyPressed(Keys.E)) {
			cam.viewportHeight -= 10;
			cam.viewportWidth -= 10;
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		System.out.println("cam width: " + cam.viewportWidth);
		System.out.println("cam height: " +cam.viewportHeight);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		//batch.setTransformMatrix(cam.combined);
		batch.setProjectionMatrix(cam.combined);
		cam.update();
		
		batch.begin();
		tex.draw(batch);
		font.draw(batch, "Enter to retry", 50, cam.viewportHeight / 2  - 50);
		batch.end();
		
	}

	@Override
	public void dispose() {
		FireFighterGame.res.disposeTexture("dead");
	}

}
