package com.picklegames.entities.weapons;

import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.entities.Entity;

public abstract class Weapon extends Entity{
	private boolean isUse;
	private boolean previousUse;
	private boolean isUsable;
	private float timeElapsed;
	private float timeCoolDown;
	private float radius;
	
	public Weapon(Body body){
		super(body);
		timeCoolDown = 0f;
		isUsable = true;
		isUse = false;
		previousUse = false;
		radius = 10f;
	}
	
	public abstract void use();
	public abstract void reset();
	public abstract boolean isInRange(float x2, float y2);
	
	//ignore pls
	public void update(float dt) {
//		if(isUse && !previousUse){
//			isUsable = false;
//		}
//		
//		if(!isUsable){
//			timeElapsed += dt;
//		}
//		
//		if(timeElapsed >= timeCoolDown){
//			isUsable = true;
//			timeElapsed = 0;
//		}else{}
//		
////		System.out.println("is usable " + isUsable);
////		System.out.println("timeElapsed "+ timeElapsed);
//		
//		if(timeElapsed >= .5f){
//			isUse = false;
//		}
//		
//		previousUse = isUse;
	}

	public void setUsable(boolean isUsable) {
		this.isUsable = isUsable;
	}

	public boolean isUsable() {	return isUsable;}

	public boolean isUse() {return isUse;}
	
	public void setIsUse(boolean use){isUse = use;}
	
	public float getTimeCoolDown() {return timeCoolDown;}

	public void setTimeCoolDown(float timeCoolDown) {this.timeCoolDown = timeCoolDown;}
	
	public float getRadius() {	return radius;	}
	
	public void setRadius(float radius) {	this.radius = radius;	}

}
