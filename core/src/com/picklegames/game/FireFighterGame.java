package com.picklegames.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FireFighterGame extends ApplicationAdapter {
	public static final int V_WIDTH = 1080;
	public static final int V_HEIGHT = 720;
	public static final float SCALE = 2f;
	
	private SpriteBatch batch;
	private ParticleEffect pe;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		pe = new ParticleEffect();
		pe.load(Gdx.files.internal("Particles/Fire.par"), Gdx.files.internal(""));
		pe.getEmitters().first().setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		pe.start();
	}

	public void update(float dt){
		pe.update(dt);
		if(pe.isComplete()){
			pe.start();
		}
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(Gdx.graphics.getDeltaTime());
		
		batch.begin();
		pe.draw(batch);
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
	}

	@Override
	public void dispose() {
		super.dispose();
		
	}
	
}
