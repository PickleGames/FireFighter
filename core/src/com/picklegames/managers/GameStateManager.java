package com.picklegames.managers;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.picklegames.game.FireFighterGame;
import com.picklegames.gameStates.GameState;
import com.picklegames.gameStates.Menu;
import com.picklegames.gameStates.Play;

public class GameStateManager {

	private FireFighterGame game;
	private SpriteBatch spriteBatch;
	private Stack<GameState> gameStates;

	public static final int MENU = 123;
	public static final int PLAY = 007;
	public static final int LEVEL_SELECT = 6969;
	public static final int SPLASH = 420;

	public GameStateManager(FireFighterGame game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		spriteBatch = game.getBatch();
		pushState(PLAY);
	}
	public FireFighterGame game() {
		return game;
	}

	public void update(float dt) {
		gameStates.peek().update(dt);
	}

	public void render() {
		gameStates.peek().render(spriteBatch);
	}

	private GameState getState(int state) {
		if (state == MENU)
			return new Menu(this);
		if (state == PLAY)
			return new Play(this);
		return null;

	}

	public void setState(int state) {
		popState();
		pushState(state);
	}

	public void pushState(int state) {
		gameStates.push(getState(state));
	}

	public void popState() {
		GameState g = gameStates.pop();
		g.dispose();
	}

}
