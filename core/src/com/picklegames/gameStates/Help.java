package com.picklegames.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.picklegames.game.FireFighterGame;
import com.picklegames.managers.GameStateManager;

public class Help extends GameState{
	private Sprite background;
	private BitmapFont font;
	public Help(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		background = new Sprite(FireFighterGame.res.getTexture("background"));
		background.setSize(cam.viewportWidth, cam.viewportHeight);
		
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.getData().setScale(1.5f);
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			gsm.setState(GameStateManager.MENU);
		}
		
	}

	@Override
	public void update(float dt) {
		handleInput();
		
	}

	@Override
	public void render() {
		batch.begin();
			background.draw(batch);
			font.setColor(Color.WHITE);
			font.draw(batch, "[1][2] to switch weapon\n"
					+ "[SPACE] to use weapon\n"
					+ "[X] to interact\n"
					+ "\n"
					+ "", cam.viewportWidth / 2 , 500);
			font.setColor(Color.YELLOW);
			font.draw(batch, "Escape to go back", 0, 100);
		batch.end();
		
		
	}

	@Override
	public void dispose() {
		font.dispose();
		
	}

}
