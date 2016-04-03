package com.picklegames.entities;

import static com.picklegames.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FireFighterGame;

public class Lamp extends Entity{
	
	private TextureRegion[] textureR;
	private Texture textureStand;
	
	public Lamp(Body body) {
		super(body);
		init();
	}
	
	public void init(){
		FireFighterGame.res.loadTexture("Character/WalkingSprites.png", "Lamp");
		Texture texture = FireFighterGame.res.getTexture("Lamp");
		textureR = TextureRegion.split(texture, 80, 150)[0]; //80, 150
		
		animation.setFrames(textureR, 1/8f);
		
		width = textureR[0].getRegionWidth();
		height = textureR[0].getRegionHeight();
	}
	
	
	public void render(SpriteBatch spriteBatch){
		spriteBatch.begin();
		spriteBatch.draw(animation.getFrame(), body.getPosition().x * PPM	- width / 2, body.getPosition().y * PPM - height / 2, width / 2, height / 2, 80, 150, 1, 1, 0);
		spriteBatch.end();
	}
	
	
}
