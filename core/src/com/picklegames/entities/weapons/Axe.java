package com.picklegames.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.MathHelper;
import com.picklegames.handlers.Box2D.B2DVars;

public class Axe extends Weapon{
	private Texture axechop;
	private TextureRegion[] texR;
	
	public Axe(Body body) {
		super(body);
		FireFighterGame.res.loadTexture("image/Weapon/axechop.png", "axechop");
		axechop = FireFighterGame.res.getTexture("axechop");
		
		texR = TextureRegion.split(axechop, axechop.getWidth() / 4, axechop.getHeight())[0];
		width = texR[0].getRegionWidth();
		height = texR[0].getRegionHeight();

		setTimeCoolDown(1);
		animation.setFrames(texR, 1/8f);
		setRadius(200f);
		
		FireFighterGame.res.loadSound("sound/Chopping Sound.mp3", "chop");
	}

	@Override
	public void use() {
		if(isUsable()){		
			setIsUse(true);
			setUsable(false);
			//System.out.println("use");	
			FireFighterGame.res.getSound("chop").play();
		}
	}

	@Override
	public boolean isInRange(float x2, float y2) {
		boolean distance = MathHelper.distanceEquation(getPosition().x * B2DVars.PPM, getPosition().y * B2DVars.PPM, x2, y2) < getRadius();
		//System.out.println(distance);
		return distance;
	}
	@Override
	public void reset() {
		animation.reset();
		setIsUse(false);
		setUsable(true);
	}
	
	public void update(float dt){
		//super.update(dt);
		if(isUse()){
			animation.play(dt, 1);
		}
		
//		System.out.println("isUse " + isUse());
//		System.out.println("isUsable " + isUsable());
		
		
		if(animation.isCompleted()) {
			setIsUse(false);
			setUsable(true);
			animation.reset();
		}
	}
	
	public void render(SpriteBatch batch){
		if(isUse()){	
			//batch.draw(animation.getFrame(), getPosition().x * B2DVars.PPM, getPosition().y * B2DVars.PPM);
		}
	}

}
