package com.picklegames.levelStates;

import static com.picklegames.handlers.B2DVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.entities.Lamp;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.CreateBox2D;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;

public class Level1 extends LevelState{

	private BitmapFont font; 
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap tileMap;

	private Box2DDebugRenderer b2dr;
	private Lamp player;
	
	public Level1(LevelStateManager lsm) {
		super(lsm);
		init();
		
	}


	@Override
	public void init() {
		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());
		
		tileMap = new TmxMapLoader().load("map/catlevel.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		
		player = new Lamp(CreateBox2D.createBox(game.getWorld(), 100, 100, 80 / 2, 150 / 2, "lamp"));	
		b2dr = new Box2DDebugRenderer();
		
		font = new BitmapFont();
		
		
	}
	
	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}
	
	private float timeElapsed; 

	@Override
	public void update(float dt) {
		timeElapsed += dt;
		
		if(timeElapsed >= 4){
			lsm.setTeActivated(true);
		}
		if(timeElapsed >= 6){
			lsm.setState(LevelStateManager.Level_2);
			
		}
		
		player.update(dt);
	}

	@Override
	public void render() {
		
		
		
		batch.setProjectionMatrix(cam.combined);
		tmr.setView(cam);
		batch.begin();
			tmr.render();
			b2dr.render(game.getWorld(), cam.combined.scl(PPM));
		batch.end();
		
		cam.unproject(new Vector3(player.getPosition(),0));
		cam.update();
		player.render(batch);
		
		batch.begin();
		font.draw(batch, "Level 1, time: " + timeElapsed, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 + 50);
		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		font.dispose();
		tmr.dispose();
		b2dr.dispose();
	}

}
