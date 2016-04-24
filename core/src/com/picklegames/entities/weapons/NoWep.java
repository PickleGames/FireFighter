package com.picklegames.entities.weapons;

import com.badlogic.gdx.physics.box2d.Body;

public class NoWep extends Weapon{

	public NoWep(Body body) {
		super(body);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInRange(float x2, float y2) {
		// TODO Auto-generated method stub
		return false;
	}

}
