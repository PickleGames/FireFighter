package com.picklegames.levelStates;

import static com.picklegames.handlers.Box2D.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.entities.Debris;
import com.picklegames.entities.Fire;
import com.picklegames.entities.Lamp;
import com.picklegames.entities.Lamp.CharacterState;
import com.picklegames.entities.Lamp.WeaponState;
import com.picklegames.entities.Person;
import com.picklegames.entities.Person.PersonState;
import com.picklegames.entities.Transport;
import com.picklegames.entities.weapons.Axe;
import com.picklegames.entities.weapons.Extinguisher;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.HUD;
import com.picklegames.handlers.HUD.HudState;
import com.picklegames.handlers.TileObject;
import com.picklegames.handlers.Box2D.B2DVars;
import com.picklegames.handlers.Box2D.CreateBox2D;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;

public class Tutorial extends LevelState {

	private BitmapFont font;
	private GlyphLayout layout;
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap tileMap;


	private Box2DDebugRenderer b2dr;
	private Lamp player;
	private Transport transport;

	private ArrayList<Debris> crap;
	private ArrayList<Person> people;
	private ArrayList<Fire> fires;

	private TileObject tileObject;
	private HUD hud;

	private String help = "Switch between your tools using [1] and [2]";

	public Tutorial(LevelStateManager lsm) {
		super(lsm);

	}

	@Override
	public void init() {

		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());

		tileMap = new TmxMapLoader().load("map/catlevel.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);

		player = lsm.getPlayer();
		player.scl(3f);
		player.setBody(CreateBox2D.createBox(FireFighterGame.world, 100, 100, player.getWidth() / 3.5f, player.getHeight() / 9,
				new Vector2(0, -player.getHeight() / 2.5f), BodyType.DynamicBody, "lamp", B2DVars.BIT_PLAYER,
				B2DVars.BIT_GROUND));
		
		b2dr = new Box2DDebugRenderer();

		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.getData().setScale(.5f, .5f);
		layout = new GlyphLayout();
		
		tileObject = new TileObject();
		tileObject.parseTiledObjectLayer(game.getWorld(), tileMap.getLayers().get("streetbound").getObjects(), "ground");

		crap = new ArrayList<Debris>();
		people = new ArrayList<Person>();
		fires = new ArrayList<Fire>();

		hud = new HUD(cam);

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

		if (Gdx.input.isKeyJustPressed(Keys.J)) {
			player.use();
		}

		if (player.characterState.equals(CharacterState.ADULT)) {
			if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
				player.getCurrentWeapon().reset();
				player.weaponState = WeaponState.AXE;
			}
			if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
				player.getCurrentWeapon().reset();
				player.weaponState = WeaponState.EXTINGUISHER;
			}
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
	boolean isTransport = false;

	@Override
	public void update(float dt) {
		handleInput();

		player.update(dt);
		player.getBody().setLinearVelocity(player.getVelocity());
		transport.update(dt);
		hud.update(dt);

		if (player.weaponState.equals(WeaponState.AXE)) {
			hud.hudState = HudState.AXE;
		} else if (player.weaponState.equals(WeaponState.EXTINGUISHER)) {
			hud.hudState = HudState.EXTINGUISHER;
		}

		if (transport.isInRange(player.getPosition().x, player.getPosition().y, 3)) {
			isTransport = true;
		}
		if (isTransport) {
			timeElapsed += dt;
			if (!lsm.getTe().isStart()) {
				lsm.getTe().start();
			}

			if (timeElapsed >= 2f) {
				lsm.setState(LevelStateManager.Level_3);
			}
		} else {
			
			for (int i = 0; i < fires.size(); i++) {
				Fire f = fires.get(i);
				f.update(dt);
				
				if (player.getCurrentWeapon().isInRange(f.getPosition().x * PPM, f.getPosition().y * PPM)) {
					help = "Use your EXTINGUISHER, and press [SPACE] to put out the fires";
				}
				
				if (!(player.getCurrentWeapon() instanceof Extinguisher))
					continue;

				if (player.getCurrentWeapon().isInRange(f.getPosition().x * PPM, f.getPosition().y * PPM)) {
					if (player.getCurrentWeapon().isUse()) {
						float life = f.getParticleEffect().getEmitters().first().getLife().getHighMax();
						f.getParticleEffect().getEmitters().first().getLife().setHighMax(life -= 3f);

						// Tween.to(f.getParticleEffect(),
						// ParticleEffectTweenAccessor.LIFE, 2).target(0, 0)
						// .ease(TweenEquations.easeNone).start(lsm.getTweenManager());
					} else {

					}

				}
				// System.out.println(f.getParticleEffect().getEmitters().first().getLife().getHighMax()
				// );
				if (f.getParticleEffect().getEmitters().first().getLife().getHighMax() <= 0f) {
					f.dispose();
					game.getWorld().destroyBody(f.getBody());
					fires.remove(f);
					i--;
				}
			}

			for (Person p : people) {
				p.update(dt);

				if (p.isInRadius(player.getPosition().x, player.getPosition().y, 2)) {
					p.personState = PersonState.RUN;
				}
			}

			for (int i = 0; i < crap.size(); i++) {
				Debris d = crap.get(i);
				d.update(dt);
				
				if (player.getCurrentWeapon().isInRange(d.getPosition().x * PPM, d.getPosition().y * PPM)) {
					help = "Switch to your AXE, and press [SPACE] to smash the debris";
				}
				
				if (!(player.getCurrentWeapon() instanceof Axe))
					continue;

				if (player.getCurrentWeapon().isInRange(d.getPosition().x * PPM, d.getPosition().y * PPM)) {
					if (player.getCurrentWeapon().isUse()) {
						if (!player.getCurrentWeapon().isUsable() && Gdx.input.isKeyJustPressed(Keys.J)) {
							d.doHit();
						}
					}

				}

				if (d.isBreakAnimationDone()) {
					d.dipose();
					game.getWorld().destroyBody(d.getBody());
					crap.remove(i);
					i--;
				}
			}
		}
	}

	Vector3 initializeCamPos = new Vector3(cam.position);

	public void shakeCam(Camera cam, float amplitude, float duration) {
		cam.position.x = (float) (initializeCamPos.x + Math.random() * amplitude);
		cam.position.y = (float) (initializeCamPos.y + Math.random() * amplitude);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		batch.setProjectionMatrix(cam.combined);
		shakeCam(cam, 2f, 1);

		System.out.println(cam.position.toString());
		// cam.position.set(player.getPosition().x * PPM, player.getPosition().y
		// * PPM, 0);

		tmr.setView(cam);
		batch.begin();
		tmr.render();
		b2dr.render(game.getWorld(), cam.combined.scl(PPM));
		batch.end();

		cam.update();

		player.render(batch);

		hud.render(batch);

		batch.begin();
		transport.render(batch);

		for (Debris d : crap) {
			d.render(batch);
		}

		for (Person p : people) {
			p.render(batch);
		}

		for (Fire f : fires) {
			f.render(batch);
		}

		batch.end();

		layout.setText(font, help);
		float width = layout.width;
		batch.begin();
		font.draw(batch, help, Gdx.graphics.getWidth() / 2 - width / 2,
				Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 6);
		font.draw(batch, "Level 6, time: " + timeElapsed, Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 + 50);
		batch.end();
	}

	public void createDebrisBox2D() {

		MapLayer layer = tileMap.getLayers().get("debris");
		if (layer == null)
			return;

		for (MapObject mo : layer.getObjects()) {

			// get debris position from tile map object layer
			float x = (float) mo.getProperties().get("x", Float.class);
			float y = (float) mo.getProperties().get("y", Float.class);

			// create new debris and add to crap list
			Debris f = new Debris(CreateBox2D.createCircle(game.getWorld(), x, y, 100, false, 1, BodyType.StaticBody,
					"debris", B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));
			crap.add(f);
		}

		layer = tileMap.getLayers().get("people");
		if (layer == null)
			return;

		for (MapObject mo : layer.getObjects()) {

			// get people position from tile map object layer
			float x = (float) mo.getProperties().get("x", Float.class);
			float y = (float) mo.getProperties().get("y", Float.class);

			// create new person and add to people list
			Person f = new Person(CreateBox2D.createCircle(game.getWorld(), x, y, 15, false, 1, BodyType.DynamicBody,
					"people", B2DVars.BIT_GROUND, B2DVars.BIT_GROUND));
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

			Fire f = new Fire(CreateBox2D.createCircle(game.getWorld(), x, y, 15, false, 1, BodyType.StaticBody, "fire",
					B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));
			fires.add(f);

		}

		layer = tileMap.getLayers().get("end");
		if (layer == null)
			return;

		for (MapObject mo : layer.getObjects()) {

			// get transport position from tile map object layer
			float x = (float) mo.getProperties().get("x", Float.class);
			float y = (float) mo.getProperties().get("y", Float.class);

			// create new transport

			transport = new Transport(CreateBox2D.createCircle(game.getWorld(), x, y, 15, false, 1, BodyType.StaticBody,
					"transport", B2DVars.BIT_GROUND, B2DVars.BIT_PLAYER));

		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
