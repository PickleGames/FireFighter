package com.picklegames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.MathHelper;
import com.picklegames.handlers.Box2D.B2DVars;

public class Explosion extends Entity{
	private ParticleEffect explosion;
	private boolean isStart;

	public Explosion(Body body){
		super(body);
		
		explosion = new ParticleEffect();
		explosion.load(Gdx.files.internal("Particles/explosion.par"), Gdx.files.internal(""));
		explosion.setPosition(body.getPosition().x * B2DVars.PPM, body.getPosition().y * B2DVars.PPM);
		
		isStart = false;
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		if(isStart){
			explosion.update(dt);
//			if(explosion.isComplete()){
//				isStart = false;
//			}
		}
		
		System.out.println(isStart);
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(isStart)
			explosion.draw(batch);
		
	}
	
	public void start(){
		if(!isStart){
			explosion.start();
			isStart = true;
		}
	}
	
	public void reset(){
		explosion.reset();
		isStart = false;
	}
	
	public void push(Body body){	
		body.applyLinearImpulse(new Vector2(-100000,0), this.body.getPosition(), true);
	}
	
	public boolean isStart() {
		return isStart;
	}

	public boolean isComplete(){
		return explosion.isComplete();
	}
	
	public boolean isInRadius(float x2, float y2, float radius) {
		float d = MathHelper.distanceEquation(body.getPosition().x * B2DVars.PPM, body.getPosition().y * B2DVars.PPM, x2, y2);
		System.out.println("distance: "+ d);
		return MathHelper.distanceEquation(body.getPosition().x * B2DVars.PPM, body.getPosition().y * B2DVars.PPM, x2, y2) < radius;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
	}

}
