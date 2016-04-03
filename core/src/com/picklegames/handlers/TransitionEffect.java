package com.picklegames.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransitionEffect {
	public static final int FIRE = 1;
	
	private float segment = Gdx.graphics.getWidth() / 10;
	private ArrayList<ParticleEffect> effectList;
	
	public TransitionEffect(){
		init();
	}
	
	public void init(){
		effectList = new ArrayList<ParticleEffect>();
		
		for(int i = 0; i < 10; i++){
			ParticleEffect temp = new ParticleEffect();
			temp.load(Gdx.files.internal("Particles/RealFire.par"), Gdx.files.internal(""));
			temp.setPosition(segment * i + 25, -50);
			effectList.add(temp);
		}
	}
	
	public void update(float dt){
		for(ParticleEffect p : effectList){
			p.update(dt);
		}
	}
	
	public void render(SpriteBatch batch){
		for(ParticleEffect p : effectList){
			p.draw(batch);
		}
	}
	
	public ArrayList<ParticleEffect> getEffectList(){
		return effectList;
	}
}
