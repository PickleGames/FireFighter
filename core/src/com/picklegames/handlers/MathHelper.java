package com.picklegames.handlers;

public class MathHelper {
	public static float distanceEquation(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.abs(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
	}
}
