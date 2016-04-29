package com.picklegames.entities;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.entities.Person.PersonState;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.Animation;
import com.picklegames.handlers.Box2D.B2DVars;

public class Animal extends Entity{

private ArrayList<TextureRegion[]> texReg;
private Texture[] animals;
private Animation ani;

	
	private Random r;

	public enum AnimalState {
		HELP, RUN
	}
	
	public AnimalState animalState;
	
	public Animal(Body body){
		super(body);
		
		init();
	}
	
	public void init(){
		
		FireFighterGame.res.loadTexture("image/Character/CUTEST DOGS EVER.png", "doge");
		FireFighterGame.res.loadTexture("image/Character/dalmations.png", "dalm");
		FireFighterGame.res.loadTexture("image/Character/foxes-nguyen.png", "fox");
		FireFighterGame.res.loadTexture("image/Character/Hedgehogs.png", "hedgehog");
		
		animals = new Texture[4];
		
		animals[0] = FireFighterGame.res.getTexture("doge");
		animals[1] = FireFighterGame.res.getTexture("dalm");
		animals[2] = FireFighterGame.res.getTexture("fox");
		animals[3] = FireFighterGame.res.getTexture("hedgehog");
		
		texReg = new ArrayList<TextureRegion[]>();
		texReg.add(TextureRegion.split(animals[0], 89, 55)[0]);
		texReg.add(TextureRegion.split(animals[1], 89, 55)[0]);
		texReg.add(TextureRegion.split(animals[2], 89, 55)[0]);
		texReg.add(TextureRegion.split(animals[3], 89, 55)[0]);
		
		ani = new Animation();
		
		r = new Random();
		
		randomize(r);
		
		animalState = AnimalState.HELP;
			
	}
	
	boolean isRun = false;
	public void update(float dt){
		
		if(animalState.equals(AnimalState.HELP)){
			ani.setCurrentFrame(0);
		}else if(animalState.equals(AnimalState.RUN)){
			ani.update(dt);
			runAway();
		}
		
		
	}
	
	public void render(SpriteBatch batch){
		
		batch.draw(ani.getFrame(), body.getPosition().x * B2DVars.PPM, body.getPosition().y * B2DVars.PPM);
	}
	
	
	private void runAway() {
		body.setLinearVelocity(new Vector2(-5,0));
	}
	
	public void randomize(Random r){
		
		int i = r.nextInt(4);
		
		ani.setFrames(texReg.get(i));
		
	}
	
	public boolean isInRadius(float x2, float y2, float radius) {
		return (findDistance(x2, y2) < radius);

	}

	public float findDistance(float x2, float y2) {

		return (float) Math
				.sqrt(Math.abs(Math.pow((x2 - body.getPosition().x), 2) + Math.pow((y2 - body.getPosition().y), 2)));
	}
	
	
	

	
}
