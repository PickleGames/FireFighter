package com.picklegames.handlers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraStyles {
	private float timeElapsed;
	private float amplitude;
	private boolean isStart;

	public CameraStyles() {
		isStart = false;
	}

	public void update(float dt) {
		if (isStart) {
			timeElapsed += dt;
		}
	}

	public void Boundary(Camera cam, float startX, float startY, float width, float height) {

		Vector3 position = cam.position;

		if (position.x < startX) {
			position.x = startX;
		}
		if (position.x > startX + width) {
			position.x = startX + width;
		}
		if (position.y < startY) {
			position.y = startY;
		}
		if (position.y > startY + height) {
			position.y = startY + height;
		}

	}

	public void Lerp(Camera cam, float speed, Vector2 targetPos) {
		Vector3 position = cam.position;
		position.x += (targetPos.x - position.x) * speed;
		position.y += (targetPos.y - position.y) * speed;
	}

	public void Shake(Camera cam, Vector3 initPos, float amplitude, float duration) {
		if (!isStart && timeElapsed <= duration) {
			isStart = true;
		}
		if (timeElapsed >= duration)
			isStart = false;

		if (isStart) {
			cam.position.x = (float) (initPos.x + opposite() * Math.random() * amplitude);
			cam.position.y = (float) (initPos.y + opposite() * Math.random() * amplitude);
		}
	}

	public void Shake(Camera cam, Vector3 initPos, float amplitude) {
		cam.position.x = (float) (initPos.x + opposite() * Math.random() * amplitude);
		cam.position.y = (float) (initPos.y + opposite() * Math.random() * amplitude);

	}

	public void reset() {
		timeElapsed = 0;
		isStart = false;
	}

	private int opposite() {
		double i = Math.random();
		if (i > .5)
			return 1;
		else
			return -1;
	}
}
