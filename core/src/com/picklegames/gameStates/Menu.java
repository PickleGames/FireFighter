package com.picklegames.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.picklegames.TweenAccessor.SpriteTweenAccessor;
import com.picklegames.game.FireFighterGame;
import com.picklegames.managers.GameStateManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

public class Menu extends GameState{
	private Sprite background, whitebg;
	private TextBox[] textBoxes;
	private int index = 0;
	public Menu(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		Tween.registerAccessor(Sprite.class, new SpriteTweenAccessor());
		FireFighterGame.res.loadTexture("image/Backgrounds/Menu.png", "background");
		FireFighterGame.res.loadTexture("image/Backgrounds/whitebg.png", "whitebg");
		
		whitebg = new Sprite(FireFighterGame.res.getTexture("whitebg"));
		whitebg.setSize(cam.viewportWidth, cam.viewportHeight);
		whitebg.setAlpha(0);
		background = new Sprite(FireFighterGame.res.getTexture("background"));
		background.setSize(cam.viewportWidth, cam.viewportHeight);
		
		textBoxes = new TextBox[3];
		textBoxes[0] = new TextBox("Play", cam.viewportWidth - 250, 500);
		textBoxes[1] = new TextBox("Help", cam.viewportWidth - 250, 440);
		textBoxes[2] = new TextBox("Exit", cam.viewportWidth - 250, 380);
		
		
	}
	
	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)){
			textBoxes[index].setHighLight(false);
			if(index >= 2) index = 0;
			else {
				index++;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			textBoxes[index].setHighLight(false);
			if(index <= 0) index = 2;
			else {
				index--;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			textBoxes[index].setActivated(true);
		}
		
	}
	
	float timeElapsed;
	boolean isTween = false;
	
	@Override
	public void update(float dt) {
		handleInput();	
		
		textBoxes[index].setHighLight(true);
		for(TextBox t : textBoxes){
			t.update(dt);
			if(t.isActivated()){
				if(t.text.equals("Play")){
					if(!isTween){
						Tween.to(whitebg, SpriteTweenAccessor.ALPHA, 2f).target(1).ease(TweenEquations.easeNone).start(gsm.getTweenManager());
						isTween = true;
					}
					System.out.println(whitebg.getColor().a);
					timeElapsed+=dt;
					if(timeElapsed >= 3f){
						gsm.setState(GameStateManager.PLAY);
					}
				}else if(t.text.equals("Help")){
					gsm.setState(GameStateManager.HELP);
				}else if(t.text.equals("Exit")){
					System.exit(0);
				}
			}
		}

		
	}

	@Override
	public void render() {
		batch.begin();
		background.draw(batch);
		
		for(TextBox t : textBoxes){
			t.render();
		}
		whitebg.draw(batch);
		batch.end();
		
	}

	@Override
	public void dispose() {
		whitebg.getTexture().dispose();
	}

	public class TextBox{
		private String text;
		private Vector2 pos;
		private BitmapFont font;
		private boolean isHighLight;
		private boolean isActivated;
		
		public TextBox(String text,float x, float y){
			this.text = text;
			pos = new Vector2(x, y);
			font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
			font.getData().setScale(1.5f);
		}
		
		public void update(float dt){
			if(isHighLight){
				font.setColor(Color.YELLOW);
			}else{
				font.setColor(Color.WHITE);
			}
		}
		
		public void render(){
			font.draw(batch, text, pos.x, pos.y);
		}
		
		public void dispose(){
			font.dispose();
		}
		
		public void setHighLight(boolean b){
			isHighLight = b;
		}

		public boolean isActivated() {
			return isActivated;
		}

		public void setActivated(boolean isActivated) {
			this.isActivated = isActivated;
		}
		
		public String getText() {
			return text;
		}
	}

}
