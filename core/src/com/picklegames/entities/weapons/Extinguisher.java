package com.picklegames.entities.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.MathHelper;
import com.picklegames.handlers.Box2D.B2DVars;

public class Extinguisher extends Weapon{
	private ParticleEffect thingy;
	
	public Extinguisher(Body body){
		super(body);
		thingy = new ParticleEffect();
		thingy.load(Gdx.files.internal("Particles/Extinguisher.par"), Gdx.files.internal(""));
		thingy.getEmitters().first().setPosition(getPosition().x, getPosition().y);
		setRadius(1000f);
		setTimeCoolDown(1);
	}
	
	public void update(float dt){
		//super.update(dt);
		if(isUse()) setUsable(false);
		
		thingy.update(dt);
		//System.out.println(thingy.getEmitters().first().getX() + " " + thingy.getEmitters().first().getY());
		thingy.getEmitters().first().setPosition(getPosition().x * B2DVars.PPM, getPosition().y * B2DVars.PPM);
		if(thingy.isComplete()){
			setIsUse(false);
		}
		
		
	}
	
	@Override
	public void use() {
		setIsUse(true);
		System.out.println("use");
		
		if(!thingy.isComplete()) return;
		thingy.start();
		
	}

	@Override
	public boolean isInRange(float x2, float y2) {
		/*System.out.println("x1 " + getPosition().x * B2DVars.PPM);
		System.out.println("y1 " + getPosition().y * B2DVars.PPM);
		System.out.println("x2 " + x2);
		System.out.println("y2 " + y2);*/
		
		float distance = MathHelper.distanceEquation(getPosition().x * B2DVars.PPM, getPosition().y * B2DVars.PPM, x2, y2);
		System.out.println("distance "+ distance);
		if(distance > getRadius()) return false;
		
		float angleLow = thingy.getEmitters().first().getAngle().getHighMin();
		float angleHigh = thingy.getEmitters().first().getAngle().getHighMax();
		
		float angleCenter = angleHigh - angleLow;
		float height = y2 - getPosition().y * B2DVars.PPM;
		float angle = (float) Math.toDegrees(Math.asin(height / distance));
		System.out.println("angle "+ angle);
		
		return (angle <= angleHigh - angleCenter || angle <= angleLow - angleCenter);
	}
	
	
	@Override
	public void render(SpriteBatch batch) {
		if(isUse()){
			thingy.draw(batch);		
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		thingy.dispose();
		
	}

	

}
