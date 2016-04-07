package com.picklegames.levelStates;

import static com.picklegames.handlers.Box2D.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.entities.Fire;
import com.picklegames.entities.Lamp;
import com.picklegames.handlers.TileObject;
import com.picklegames.handlers.Box2D.B2DVars;
import com.picklegames.handlers.Box2D.CreateBox2D;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;

public class Level4 extends LevelState {
	private BitmapFont font;
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap tileMap;

	private Box2DDebugRenderer b2dr;
	private Lamp player;
	
	private ArrayList<ParticleEffect> pFires;

	private ArrayList<Fire> fires;

	public Level4(LevelStateManager lsm) {
		super(lsm);
		init();

	}

	@Override
	public void init() {
		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());

		tileMap = new TmxMapLoader().load("map/catlevel.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);

		player = lsm.getPlayer();
		
		b2dr = new Box2DDebugRenderer();

		font = new BitmapFont();

		TileObject.parseTiledObjectLayer(game.getWorld(), tileMap.getLayers().get("streetbound").getObjects());

		fires = new ArrayList<Fire>();
		pFires = new ArrayList<ParticleEffect>();
		
		createFiresBox2D();
	}

	@Override
	public void handleInput() {
		if (Gdx.input.isKeyPressed(Keys.D)) {
			player.setVelocityX(2);
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			player.setVelocityX(-2);
		} else {
			player.setVelocityX(0);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			player.setVelocityY(2);
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			player.setVelocityY(-2);
		} else {
			player.setVelocityY(0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.Q)){
			cam.viewportHeight += 10;
			cam.viewportWidth += 10;
		}else if(Gdx.input.isKeyPressed(Keys.E)){
			cam.viewportHeight -= 10;
			cam.viewportWidth -= 10;
		}

		System.out.println(player.getVelocity().toString());
	}

	private float timeElapsed;

	@Override
	public void update(float dt) {
		handleInput();
		timeElapsed += dt;
		player.update(dt);
		player.getBody().setLinearVelocity(player.getVelocity());
		
		for(ParticleEffect p : pFires){
			p.update(dt);
		}
		
		for(Fire f : fires){
			f.update(dt);
		}
	}

	@Override
	public void render() {

		batch.setProjectionMatrix(cam.combined);
		
		
		
		
		tmr.setView(cam);
		batch.begin();
		tmr.render();
		b2dr.render(game.getWorld(), cam.combined.scl(PPM));
		batch.end();

		cam.unproject(new Vector3(player.getPosition(), 0));
		cam.update();

		player.render(batch);
		
		batch.begin();
		for(ParticleEffect p : pFires){
			p.draw(batch);
		}
		
		for(Fire f : fires){
			f.render(batch);
		}		
		batch.end();
		
		batch.begin();
		font.draw(batch, "Level 4, time: " + timeElapsed, Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 + 50);
		batch.end();
	}

	public void createFiresBox2D() {

		MapLayer layer = tileMap.getLayers().get("fire");
		if (layer == null)
			return;

		for (MapObject mo : layer.getObjects()) {

			// get fire position from tile map object layer
			float x = (float) mo.getProperties().get("x", Float.class);
			float y = (float) mo.getProperties().get("y", Float.class);
			
			// create new fire and add to fires list

			Fire f = new Fire(CreateBox2D.createCircle(game.getWorld(), x, y, 15, false, 1, BodyType.StaticBody, "fire", B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));

			fires.add(f);
			
			System.out.println("fireX: " + x + "fireY: " + y);
			ParticleEffect temp = new ParticleEffect();
			temp.load(Gdx.files.internal("Particles/RealFire.par"), Gdx.files.internal(""));
			temp.setPosition(x, y);
			pFires.add(temp);
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		font.dispose();
		tmr.dispose();
		b2dr.dispose();
	}

}
