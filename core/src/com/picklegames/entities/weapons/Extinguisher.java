package com.picklegames.entities.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.Box2D.B2DVars;

public class Extinguisher extends Weapon{
	private ParticleEffect thingy;
	
	public Extinguisher(Body body){
		super(body);
		thingy = new ParticleEffect();
		thingy.load(Gdx.files.internal("Particles/Extinguisher.par"), Gdx.files.internal(""));
		thingy.getEmitters().first().setPosition(getPosition().x, getPosition().y);
		
	}
	
	public void update(float dt){
		super.update(dt);
		
		thingy.update(dt);
		System.out.println(thingy.getEmitters().first().getX() + " " + thingy.getEmitters().first().getY());
		thingy.getEmitters().first().setPosition(getPosition().x * B2DVars.PPM, getPosition().y * B2DVars.PPM);
	}
	
	@Override
	public void use() {
		if(isUsable() && thingy.isComplete()){
			setIsUse(true);
			System.out.println("use");
			thingy.start();
		}
		
	}

	@Override
	public boolean isInRange(float x2, float y2) {
		
		return false;
	}

	@Override
	public void render(SpriteBatch batch) {
		thingy.draw(batch);		
	}

	@Override
	public void dispose() {
		thingy.dispose();
		
	}

	

}
