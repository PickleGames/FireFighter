package com.picklegames.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.MathHelper;

public class Transport extends Entity {

	public Transport(Body body) {
		super(body);
	}

	public boolean isInRange(float x1, float y1, float range) {
		return MathHelper.distanceEquation(body.getPosition().x, body.getPosition().y, x1, y1) < range;
	}

}
