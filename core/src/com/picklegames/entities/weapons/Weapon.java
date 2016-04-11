package com.picklegames.entities.weapons;

import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.entities.Entity;

public abstract class Weapon extends Entity{
	private boolean isUse;
	private boolean isUsable;
	private float timeElapsed;
	private float timeCoolDown;
	private float radius;
	
	public Weapon(Body body){
		super(body);
		timeCoolDown = 0f;
		isUsable = true;
		radius = 10f;
	}
	
	public abstract void use();
	public abstract boolean isInRange(float x2, float y2);

	
	public void update(float dt) {
		if(isUse()){
			timeElapsed += dt;
		}
		
		if(timeElapsed >= timeCoolDown){
			isUsable = true;
		}else isUsable = false;
		
		System.out.println(isUsable);
		
		if(timeElapsed >= 1f){
			setIsUse(false);
			timeElapsed = 0;
		}
	}

	public boolean isUsable() {	return isUsable;}

	public boolean isUse() {return isUse;}
	
	public void setIsUse(boolean use){isUse = use;}
	
	public float getTimeCoolDown() {return timeCoolDown;}

	public void setTimeCoolDown(float timeCoolDown) {this.timeCoolDown = timeCoolDown;}
	
	public float getRadius() {	return radius;	}
	
	public void setRadius(float radius) {	this.radius = radius;	}

}
