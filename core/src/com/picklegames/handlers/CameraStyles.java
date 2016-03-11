package com.picklegames.handlers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public final class CameraStyles {
	
	public static void Boundary(Camera cam, float startX, float startY, float width, float height){
		
		Vector3 position = cam.position;
		
		if(position.x < startX){
			position.x = startX;
		}
		if(position.x > startX + width){
			position.x = startX + width;
		}
		if(position.y < startY){
			position.y = startY;
		}
		if(position.y > startY + height){
			position.y = startY + height;
		}

	}
	
	public static void Lerp(Camera cam, float speed, Vector2 targetPos){
		Vector3 position = cam.position;
		position.x += (targetPos.x - position.x) * speed;
		position.y += (targetPos.y - position.y) * speed;
	}
}

