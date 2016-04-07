package com.picklegames.entities;

import com.badlogic.gdx.physics.box2d.Body;

public class Debris extends Entity{
	
	private float health;
	
	private enum DebrisState{
		CRACK, BREAK
	}
	
	public DebrisState debrisState;
	
	public Debris(Body body) {
		super(body);

	}
	
	public void update(float dt){
		super.update(dt);	
		
	}

	public boolean isInRadius(float x2, float y2, float radius) {
		return findDistance(x2, y2) < radius;
	}

	public float findDistance(float x2, float y2) {
		return (float) Math.sqrt(Math.abs(Math.pow(
				(x2 - body.getPosition().x), 2) + Math.pow((y2 - body.getPosition().y), 2)));
	}
	
	public float getHealth(){
		return health;
	}
	
	public void setHealth(float health){
		this.health = health;
	}
	
	public void getHit(){
		health--;
	}
}
