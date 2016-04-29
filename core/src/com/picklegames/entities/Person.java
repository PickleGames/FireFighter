package com.picklegames.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.Box2D.B2DVars;

public class Person extends Entity {

	private String[] expressions;
	private String currentSay = "";
	private BitmapFont font;
	private Random r;
	
	private TextureRegion[] personStand, personRun;
	
	public enum PersonState {
		HELP, RUN
	}

	public PersonState personState;
	public Person(){
		super();
		init();
	}
	
	public Person(Body body) {
		super(body);
		init();
	}
	
	public void init(){
		r = new Random();

		expressions = new String[4];

		expressions[0] = "HELP!!";
		expressions[1] = "SAVE ME";
		expressions[2] = "PLEASE HELP ME";
		expressions[3] = "I DON'T WANT TO DIE!!";
		
		FireFighterGame.res.loadTexture("image/Character/person_stand.png", "person_stand");
		FireFighterGame.res.loadTexture("image/Character/person_run.png", "person_run");
		
		Texture tps = FireFighterGame.res.getTexture("person_stand"); //925 564
		Texture tpr = FireFighterGame.res.getTexture("person_run"); //1158 600
		personStand = TextureRegion.split(tps, 925 / 3, 568)[0];
		personRun = TextureRegion.split(tpr, 1500 / 4, 500)[0];
		
		animation.setFrames(personStand, 1/8f);
		
		width = animation.getFrame().getRegionWidth() * .4f;
		height = animation.getFrame().getRegionHeight() * .4f;

		font = new BitmapFont();
		personState = PersonState.HELP;
	}

	boolean isRun = false;
	public void update(float dt) {
		super.update(dt);

		if (personState.equals(PersonState.HELP)) {
			updateHelp(dt);
		} else if(personState.equals(PersonState.RUN)){
			if(!isRun){
				animation.setFrames(personRun);
				width = animation.getFrame().getRegionWidth() * .356f;
				height = animation.getFrame().getRegionHeight() * .356f;
				isRun = true;
			}
			runAway();
			
		}

	}

	public void render(SpriteBatch batch) {
		
		batch.draw(animation.getFrame(), body.getPosition().x * B2DVars.PPM - width /2 , body.getPosition().y * B2DVars.PPM - height /2 , width, height);
		if (personState.equals(PersonState.HELP)) {
			font.draw(batch, currentSay, body.getPosition().x * B2DVars.PPM - width / 2,
					(body.getPosition().y * B2DVars.PPM - width / 2) + height - height / 4);
		} else {

		}

	}

	float timeElap = 0;

	private void updateHelp(float dt) {

		int i = r.nextInt(4);
		timeElap += dt;
		if (timeElap > 1.5f) {
			currentSay = expressions[i];
			timeElap = 0;
		}

	}

	private void runAway() {
		body.setLinearVelocity(new Vector2(-5,0));
	}


	public boolean isInRadius(float x2, float y2, float radius) {
		return (findDistance(x2, y2) < radius);

	}

	public float findDistance(float x2, float y2) {

		return (float) Math
				.sqrt(Math.abs(Math.pow((x2 - body.getPosition().x), 2) + Math.pow((y2 - body.getPosition().y), 2)));
	}
	
	public void dispose(){
		super.dispose();
		
	}

}
