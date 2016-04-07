package com.picklegames.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.Box2D.B2DVars;

public class Person extends Entity {

	private String[] expressions;
	private String currentSay;
	private BitmapFont font;
	private Random r;

	public Person(Body body) {
		super(body);

		r = new Random();

		expressions = new String[4];

		expressions[0] = "HELP!!";
		expressions[1] = "SAVE ME";
		expressions[2] = "PLEASE HELP ME";
		expressions[3] = "I DON'T WANT TO DIE!!";

		font = new BitmapFont();
		
	}

	public void update(float dt) {
		super.update(dt);

		if (personState.equals(PersonState.HELP)) {
			updateHelp(dt);
		} else {
			runAway();
		}

	}

	public void render(SpriteBatch batch) {

		batch.begin();

		if (personState.equals(PersonState.HELP)) {
			font.draw(batch, currentSay, body.getPosition().x * B2DVars.PPM - width / 2,
					(body.getPosition().x * B2DVars.PPM - width / 2) + 20);
		} else {

		}

		batch.end();
	}

	float timeElap = 0;

	private void updateHelp(float dt) {

		int i = r.nextInt(4);
		timeElap += dt;
		if (timeElap > .5f) {
			currentSay = expressions[i];
			timeElap = 0;
		}

	}

	private void runAway() {
		body.setLinearVelocity(-2, 0);
	}

	private enum PersonState {
		HELP, RUN
	}

	public PersonState personState;

	public boolean isInRadius(float x2, float y2, float radius) {
		return (findDistance(x2, y2) < radius);

	}

	public float findDistance(float x2, float y2) {

		return (float) Math
				.sqrt(Math.abs(Math.pow((x2 - body.getPosition().x), 2) + Math.pow((y2 - body.getPosition().y), 2)));
	}

}
