package com.picklegames.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import aurelienribon.tweenengine.TweenAccessor;

public class ParticleEffectTweenAccessor implements TweenAccessor<ParticleEffect>{
	public static final int XY = 1;
	public static final int GRAVITY = 2;
	public static final int LIFE = 3;
	public static final int DURATION = 4;
	@Override
	public int getValues(ParticleEffect target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case XY:
			returnValues[0] = target.getEmitters().get(0).getX();
			returnValues[1] = target.getEmitters().get(0).getY();
			return 2;
		case GRAVITY:
			returnValues[0] = target.getEmitters().get(0).getGravity().getHighMin();
			returnValues[1] = target.getEmitters().get(0).getGravity().getHighMax();
			return 2;
		case LIFE:
			returnValues[0] = target.getEmitters().get(0).getLife().getHighMin();
			returnValues[1] = target.getEmitters().get(0).getLife().getHighMax();
			return 2;
		case DURATION:
			returnValues[0] = target.getEmitters().get(0).duration;
			return 1;
		default:
			assert false; return -1;
		}
	}

	@Override
	public void setValues(ParticleEffect target, int tweenType, float[] newValues) {
		switch(tweenType){
		case XY:
			target.getEmitters().get(0).setPosition(newValues[0], newValues[1]);
			break;
		case GRAVITY:
			target.getEmitters().get(0).getGravity().setHighMin(newValues[0]);
			target.getEmitters().get(0).getGravity().setHighMax(newValues[1]);
			break;
		case LIFE:
			target.getEmitters().get(0).getLife().setHighMin(newValues[0]);
			target.getEmitters().get(0).getLife().setHighMax(newValues[1]);
			break;
		case DURATION:
			target.getEmitters().get(0).duration = newValues[0];
			break;
		}
	}

}
