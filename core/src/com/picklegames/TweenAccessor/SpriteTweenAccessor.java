package com.picklegames.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteTweenAccessor implements TweenAccessor<Sprite>{

	public static final int ALPHA = 1;
	@Override
	public int getValues(Sprite sprite, int tweenType, float[] returnValue) {
		switch(tweenType){
		case ALPHA: 
			returnValue[0] = sprite.getColor().a;
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Sprite sprite, int tweenType, float[] newValue) {
		switch(tweenType){
		case ALPHA:
			sprite.setColor(sprite.getColor().r, sprite.getColor().g, sprite.getColor().b, newValue[0]);
		default:
			return;
		}
		
		
	}
}