package com.picklegames.TweenAccessor;

import com.picklegames.entities.Entity;
import aurelienribon.tweenengine.TweenAccessor;
import static  com.picklegames.handlers.B2DVars.PPM;

public class EntityTweenAccessor implements TweenAccessor<Entity>{
	public static final int XY = 1;
	public static final int X = 2;
	public static final int Y = 3;
	
	@Override
	public int getValues(Entity target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case XY:
			returnValues[0] = target.getPosition().x * PPM;
			returnValues[1] = target.getPosition().y * PPM;
			return 2;
		case X:
			returnValues[0] = target.getPosition().x * PPM;
			return 2;
		case Y:
			returnValues[0] = target.getPosition().y * PPM;
			return 3;
			
		default:
			assert false; return -1;
		}
	}

	@Override
	public void setValues(Entity target, int tweenType, float[] newValues) {
		switch(tweenType){
		case XY:
			target.setPosition(newValues[0] / PPM, newValues[1] / PPM);
			break;
		case X:
			target.setPosition(newValues[0] / PPM, target.getPosition().y);
			break;
		case Y:
			target.setPosition(target.getPosition().x, newValues[0] / PPM);
			break;
			
		}
	}

}
