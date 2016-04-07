package com.picklegames.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.Box2D.B2DVars;

public class Axe extends Weapon{
	private Texture axechop;
	
	public Axe(Body body) {
		super(body);
		axechop = new Texture("image/Weapon/axechop.png");	
	}

	@Override
	public void use() {
		if(isUsable()){
			setIsUse(true);
		}
		
	}

	@Override
	public boolean isInRange(float x2, float y2) {
		
		return false;
	}
	
	public void render(SpriteBatch batch){
		if(isUse()){
			
			batch.draw(axechop, getPosition().x * B2DVars.PPM, getPosition().y * B2DVars.PPM);
		
		}
	}

}
