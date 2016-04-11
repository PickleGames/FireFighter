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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.entities.Debris;
import com.picklegames.entities.Fire;
import com.picklegames.entities.Debris.DebrisState;
import com.picklegames.entities.Person.PersonState;
import com.picklegames.entities.Lamp;
import com.picklegames.entities.Person;
import com.picklegames.handlers.TileObject;
import com.picklegames.handlers.Box2D.B2DVars;
import com.picklegames.handlers.Box2D.CreateBox2D;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;

public class Level6 extends LevelState {

	private BitmapFont font;
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap tileMap;

	private Box2DDebugRenderer b2dr;
	private Lamp player;

	private ArrayList<Debris> crap;
	private ArrayList<Person> people;
	private ArrayList<Fire> fires;
	
	public Level6(LevelStateManager lsm) {
		super(lsm);

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

		crap = new ArrayList<Debris>();
		people = new ArrayList<Person>();
		fires = new ArrayList<Fire>();

		createDebrisBox2D();

	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
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
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
			player.use();
		}

		if (Gdx.input.isKeyPressed(Keys.Q)) {
			cam.viewportHeight += 10;
			cam.viewportWidth += 10;
		} else if (Gdx.input.isKeyPressed(Keys.E)) {
			cam.viewportHeight -= 10;
			cam.viewportWidth -= 10;
		}
	}

	private float timeElapsed = 0;

	@Override
	public void update(float dt) {

		handleInput();
		timeElapsed += dt;
		player.update(dt);
		player.getBody().setLinearVelocity(player.getVelocity());
		
		for(Fire f : fires){
			f.update(dt);
		}
		
		for (Person p : people) {
			p.update(dt);
			
			if(p.isInRadius(player.getPosition().x, player.getPosition().y, 2)){
				p.personState = PersonState.RUN;
			}
		}


		for(int i = 0; i< crap.size(); i++){
			Debris d = crap.get(i);
			d.update(dt);
			
			if (d.isInRadius(player.getPosition().x, player.getPosition().y, 2)) {
				if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !(d.debrisState.equals(DebrisState.BREAK))) {
					if (d.getHealth() > 0) {
						d.debrisState = DebrisState.CRACK;
					} else {
						d.debrisState = DebrisState.BREAK;
					}
					d.resetAnimation();
					d.doHit();
				}
			}
			
			if(d.isBreakAnimationDone()){
				d.dipose();
				game.getWorld().destroyBody(d.getBody());
				crap.remove(i);
				i--;
			}
		}
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
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
		for (Debris d : crap) {
			d.render(batch);
		}
		
		for (Person p : people) {
			p.render(batch);
		}
		
		for(Fire f : fires){
			f.render(batch);
		}
		batch.end();
		
		batch.begin();
		font.draw(batch, "Level 6, time: " + timeElapsed, Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 + 50);
		batch.end();
	}

	public void createDebrisBox2D() {

		MapLayer layer = tileMap.getLayers().get("debris");
		if (layer == null)
			return;

		for (MapObject mo : layer.getObjects()) {

			// get fire position from tile map object layer
			float x = (float) mo.getProperties().get("x", Float.class);
			float y = (float) mo.getProperties().get("y", Float.class);

			// create new fire and add to fires list
			Debris f = new Debris(CreateBox2D.createCircle(game.getWorld(), x, y, 100, false, 1, BodyType.StaticBody,
					"people", B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));
			crap.add(f);
		}
		
		layer = tileMap.getLayers().get("people");
		if (layer == null)
			return;

		for (MapObject mo : layer.getObjects()) {

			// get fire position from tile map object layer
			float x = (float) mo.getProperties().get("x", Float.class);
			float y = (float) mo.getProperties().get("y", Float.class);

			// create new fire and add to fires list
			Person f = new Person(CreateBox2D.createCircle(game.getWorld(), x, y, 15, false, 1, BodyType.DynamicBody,
					"people", B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));
			people.add(f);
		}
		
		layer = tileMap.getLayers().get("fire");
		if (layer == null)
			return;

		for (MapObject mo : layer.getObjects()) {

			// get fire position from tile map object layer
			float x = (float) mo.getProperties().get("x", Float.class);
			float y = (float) mo.getProperties().get("y", Float.class);
			
			// create new fire and add to fires list

			Fire f = new Fire(CreateBox2D.createCircle(game.getWorld(), x, y, 15, false, 1, BodyType.StaticBody, "fire", B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));
			fires.add(f);
			
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	
	}

}
