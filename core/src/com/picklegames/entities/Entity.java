package com.picklegames.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.Animation;
import com.picklegames.handlers.Box2D.B2DVars;

public class Entity {
	protected Body body;
	protected Animation animation;
	protected float width;
	protected float height;

	public Entity(){
		animation = new Animation();
	}
	
	public Entity(Body body) {
		this.body = body;
		animation = new Animation();
	}

	public void setBody(Body body){
		this.body = body;
	}
	
	public void setAnimation(TextureRegion reg, float delay) {
		setAnimation(new TextureRegion[] { reg }, delay);
	}

	public void setAnimation(TextureRegion[] reg, float delay) {
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
	}

	public void update(float dt) {
		animation.update(dt);
	}

	public void render(SpriteBatch batch) {
		try {
			if (animation.getFrame() != null) {
				batch.begin();
				batch.draw(animation.getFrame(), body.getPosition().x * B2DVars.PPM - width / 2,
						body.getPosition().y * B2DVars.PPM - height / 2);
				batch.end();
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void dispose(){
		body.getFixtureList().clear();
		animation.dispose();
	}
	
	public Body getBody() {
		return body;
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public void setPosition(float x, float y) {
		body.setTransform(x, y, 0);
		
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
