package com.picklegames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.MathHelper;
import com.picklegames.handlers.Box2D.B2DVars;

public class Fire extends Entity {
	
	private ParticleEffect particleE;

	public Fire(Body body) {
		super(body);
		
		particleE = new ParticleEffect();
		particleE.load(Gdx.files.internal("Particles/Fire.par"), Gdx.files.internal(""));
		particleE.setPosition(body.getPosition().x * B2DVars.PPM, body.getPosition().y * B2DVars.PPM);
		particleE.start();

		
	}
	
	public void update(float dt){
		particleE.update(dt);
	}
	
	public void render(SpriteBatch batch){
		particleE.draw(batch);
		
	}
	
	public ParticleEffect getParticleEffect(){
		return particleE;
	}

	public boolean isInRadius(float x2, float y2, float radius) {
		float d = MathHelper.distanceEquation(body.getPosition().x * B2DVars.PPM, body.getPosition().y * B2DVars.PPM, x2, y2);
		//System.out.println("distance " + d);
		return d < radius;
	}

	public float findDistance(float x2, float y2) {
		return (float) Math.sqrt(Math.abs(Math.pow(
				(x2 - body.getPosition().x), 2) + Math.pow((y2 - body.getPosition().y), 2)));

	}
	
	@Override
	public void scl(float scl) {
		particleE.getEmitters().first().getScale().setHighMax(scl);
	}


}
