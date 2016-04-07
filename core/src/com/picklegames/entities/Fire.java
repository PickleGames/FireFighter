package com.picklegames.entities;

import com.badlogic.gdx.physics.box2d.Body;

public class Fire extends Entity {

	public Fire(Body body) {
		super(body);

	}

	public boolean isInRadius(float x2, float y2, float radius) {
		return findDistance(x2, y2) < radius;
	}

	public float findDistance(float x2, float y2) {
		return (float) Math.sqrt(Math.abs(Math.pow(
				(x2 - body.getPosition().x), 2) + Math.pow((y2 - body.getPosition().y), 2)));

	}

}
