package com.picklegames.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransitionEffect {
	public static final int FIRE = 1;
	
	private float segment = Gdx.graphics.getWidth() / 10;
	private ArrayList<ParticleEffect> effectList;
	private ParticleEffect effect;
	private boolean isStart = false;

	public TransitionEffect(){
		init();
	}
	
	public void init(){
		
		effectList = new ArrayList<ParticleEffect>();
		for(int i = 0; i < 10; i++){
			ParticleEffect temp = new ParticleEffect();
			temp.load(Gdx.files.internal("Particles/SuperHotFire.par"), Gdx.files.internal(""));
			temp.getEmitters().first().setPosition(segment * i + 25, -50);
			//temp.start();
			effectList.add(temp);
		}
	}
	
	public void update(float dt){
		if(isStart){
			for(ParticleEffect p : effectList){
				p.update(dt);
				//System.out.println(p.isComplete());
				//System.out.println(p.getEmitters().first().isComplete());
			}
		}
	}
	
	public void render(SpriteBatch batch){	
		for(ParticleEffect p : effectList){
			p.draw(batch);
		}
	}
	
	public void start(){
		for(ParticleEffect p: effectList){
			p.start();
		}
		isStart = true;
	}
	
	public boolean isStart() {
		return isStart;
	}
	
	public boolean isFinished(){
		for(ParticleEffect p: effectList){
			if(!p.isComplete()) return false;
		}
		return true;
	}
	
	public ParticleEffect getEffect() {
		return effect;
	}
	
	public ArrayList<ParticleEffect> getEffectList(){
		return effectList;
	}
}
