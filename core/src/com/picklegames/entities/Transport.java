package com.picklegames.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.MathHelper;
import com.picklegames.handlers.Box2D.B2DVars;

public class Transport extends Entity {

	public Transport(Body body) {
		super(body);
	}

	public boolean isInRange(float x1, float y1, float range) {
		float d = MathHelper.distanceEquation(body.getPosition().x * B2DVars.PPM, body.getPosition().y * B2DVars.PPM, x1, y1);
		return MathHelper.distanceEquation(body.getPosition().x * B2DVars.PPM, body.getPosition().y * B2DVars.PPM, x1, y1) < range;
	}

}
