package com.picklegames.managers;

import java.io.FileNotFoundException;
import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.picklegames.game.FireFighterGame;
import com.picklegames.gameStates.Dialogue;
import com.picklegames.gameStates.GameState;
import com.picklegames.gameStates.Menu;
import com.picklegames.gameStates.Play;

public class GameStateManager {

	private FireFighterGame game;
	private SpriteBatch spriteBatch;
	private Stack<GameState> gameStates;

	public static final int MENU = 123;
	public static final int PLAY = 007;
	public static final int DIALOGUE = 420;
	public static final int SPLASH = 6969;

	public GameStateManager(FireFighterGame game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		spriteBatch = game.getBatch();
		pushState(DIALOGUE);
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
		if (state == DIALOGUE){
			try {
				return new Dialogue(this);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
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
