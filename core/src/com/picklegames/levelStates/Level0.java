package com.picklegames.levelStates;

import static com.picklegames.handlers.Box2D.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.picklegames.TweenAccessor.EntityTweenAccessor;
import com.picklegames.entities.Entity;
import com.picklegames.entities.Explosion;
import com.picklegames.entities.Fire;
import com.picklegames.entities.Lamp;
import com.picklegames.entities.Lamp.CharacterState;
import com.picklegames.entities.Transport;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.TileObject;
import com.picklegames.handlers.Box2D.B2DVars;
import com.picklegames.handlers.Box2D.CreateBox2D;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

public class Level0 extends LevelState {

	private OrthogonalTiledMapRenderer tmr;
	private TiledMap tileMap;
	private TileObject tileObject;
	private Box2DDebugRenderer b2dr;
	private ArrayList<Fire> fires;

	private Explosion explosion;
	private Transport transport;
	private Lamp player;

	public Level0(LevelStateManager lsm) {
		super(lsm);

	}

	@Override
	public void init() {
		Tween.registerAccessor(Entity.class, new EntityTweenAccessor());

		tileMap = new TmxMapLoader().load("map/introlevel.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);

		cam.viewportWidth = tmr.getMap().getProperties().get("width", Integer.class) * 32;
		cam.viewportHeight = tmr.getMap().getProperties().get("height", Integer.class) * 32;
		cam.position.x = cam.viewportWidth / 2;
		cam.position.y = cam.viewportHeight / 2;

		b2dr = new Box2DDebugRenderer();
		fires = new ArrayList<Fire>();
		createDebrisBox2D();
		
		tileObject = new TileObject();
		tileObject.parseTiledObjectLayer(game.getWorld(), tileMap.getLayers().get("streetbound").getObjects(), "ground");
		player = lsm.getPlayer();
		player.scl(8f);
		player.setBody(CreateBox2D.createBox(FireFighterGame.world, 100, 0, player.getWidth() / 3.5f,
				player.getHeight() / 9, new Vector2(0, -player.getHeight() / 2.5f), BodyType.DynamicBody, "lamp",
				B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));
		player.characterState = CharacterState.ADULT;

		Tween.set(player, EntityTweenAccessor.VEL).target(2f, 1f).ease(TweenEquations.easeNone).delay(.5f)
				.start(lsm.getTweenManager());
		Tween.to(player, EntityTweenAccessor.DIMENSION, 7f).target(player.getWidth() * .5f, player.getHeight() * .5f)
				.ease(TweenEquations.easeInOutQuad).delay(3f).start(lsm.getTweenManager());
	}

	@Override
	public void handleInput() {
		if (Gdx.input.isKeyPressed(Keys.Q)) {
			cam.viewportHeight += 10;
			cam.viewportWidth += 10;
		} else if (Gdx.input.isKeyPressed(Keys.E)) {
			cam.viewportHeight -= 10;
			cam.viewportWidth -= 10;
		}

	}
	float timeElapsed;
	@Override
	public void update(float dt) {
		handleInput();

		player.update(dt);

		for (Fire f : fires) {
			f.update(dt);
		}
		explosion.update(dt);
		transport.update(dt);
		
		System.out.println(transport.isInRange(player.getBody().getPosition().x * B2DVars.PPM, player.getBody().getPosition().y * B2DVars.PPM, 100));
		if(transport.isInRange(player.getBody().getPosition().x * B2DVars.PPM, player.getBody().getPosition().y * B2DVars.PPM, 200)){
			explosion.start();
			timeElapsed+=dt;
		}

		if(timeElapsed >= .5f){
			lsm.setState(LevelStateManager.Level_1);
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
		cam.update();

		player.render(batch);
		batch.begin();
		explosion.render(batch);
		for (Fire f : fires) {
			f.render(batch);
		}


		batch.end();
		
	}

	public void createDebrisBox2D() {

		MapLayer layer = tileMap.getLayers().get("debris");


		layer = tileMap.getLayers().get("fire");
		if (layer != null) {
			for (MapObject mo : layer.getObjects()) {

				// get fire position from tile map object layer
				float x = (float) mo.getProperties().get("x", Float.class);
				float y = (float) mo.getProperties().get("y", Float.class);

				// create new fire and add to fires list

				Fire f = new Fire(CreateBox2D.createCircle(game.getWorld(), x, y, 15, false, 1, BodyType.StaticBody,
						"fire", B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));
				f.scl((float) Math.random() * 400);
				fires.add(f);

			}
		}

		layer = tileMap.getLayers().get("explosion");
		if (layer != null) {
			for (MapObject mo : layer.getObjects()) {

				// get transport position from tile map object layer
				float x = (float) mo.getProperties().get("x", Float.class);
				float y = (float) mo.getProperties().get("y", Float.class);

				// create new transport

				explosion = new Explosion(CreateBox2D.createCircle(game.getWorld(), x, y, 15, false, 1,
						BodyType.StaticBody, "transport", B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));
				explosion.scl(700);
			}

		}

		layer = tileMap.getLayers().get("transport");
		if (layer != null) {

			for (MapObject mo : layer.getObjects()) {

				// get transport position from tile map object layer
				float x = (float) mo.getProperties().get("x", Float.class);
				float y = (float) mo.getProperties().get("y", Float.class);

				// create new transport

				transport = new Transport(CreateBox2D.createCircle(game.getWorld(), x, y, 15, false, 1,
						BodyType.StaticBody, "transport", B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));

			}
		}
	}

	@Override
	public void dispose() {
		for (Fire f : fires) {
			f.dispose();
		}

	}

}
