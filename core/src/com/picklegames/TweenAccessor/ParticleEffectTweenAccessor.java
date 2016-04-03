package com.picklegames.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import aurelienribon.tweenengine.TweenAccessor;

public class ParticleEffectTweenAccessor implements TweenAccessor<ParticleEffect>{
	public static final int GRAVITY = 0;
	public static final int LIFE = 1;
	
	@Override
	public int getValues(ParticleEffect target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case GRAVITY:
			returnValues[0] = target.getEmitters().get(0).getGravity().getHighMin();
			returnValues[1] = target.getEmitters().get(0).getGravity().getHighMax();
			return 2;
		case LIFE:
			returnValues[0] = target.getEmitters().get(0).getLife().getHighMin();
			returnValues[1] = target.getEmitters().get(0).getLife().getHighMax();
			return 2;
		default:
			assert false; return -1;
		}
	}

	@Override
	public void setValues(ParticleEffect target, int tweenType, float[] newValues) {
		switch(tweenType){
		case GRAVITY:
			target.getEmitters().get(0).getGravity().setHighMin(newValues[0]);
			target.getEmitters().get(0).getGravity().setHighMax(newValues[1]);
			break;
		case LIFE:
			target.getEmitters().get(0).getLife().setHighMin(newValues[0]);
			target.getEmitters().get(0).getLife().setHighMax(newValues[1]);
			break;
		}
	}

}
