package com.picklegames.levelStates;

import static com.picklegames.handlers.Box2D.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.entities.Cat;
import com.picklegames.entities.Cat.CatState;
import com.picklegames.entities.Lamp;
import com.picklegames.entities.Lamp.CharacterState;
import com.picklegames.entities.Lamp.WeaponState;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.CameraStyles;
import com.picklegames.handlers.HUD;
import com.picklegames.handlers.HUD.HudState;
import com.picklegames.handlers.TileObject;
import com.picklegames.handlers.Box2D.B2DVars;
import com.picklegames.handlers.Box2D.CreateBox2D;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;

public class Level2 extends LevelState implements ContactListener{

	private BitmapFont font;
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap tileMap;

	private Box2DDebugRenderer b2dr;
	private Lamp player;
	private Cat cat;

	private CameraStyles camStyle;

	private HUD hud;

	public Level2(LevelStateManager lsm) {
		super(lsm);

	}

	@Override
	public void init() {

		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());

		tileMap = new TmxMapLoader().load("map/catlevel1.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);

		// cam.viewportWidth = tmr.getMap().getProperties().get("width",
		// Integer.class) * 32;
		// cam.viewportHeight = tmr.getMap().getProperties().get("height",
		// Integer.class) * 32;
		// cam.viewportWidth = tmr.getMap().getProperties().get("width",
		// Integer.class) * 32;
		cam.viewportHeight = tmr.getMap().getProperties().get("height", Integer.class) * 32;
		// cam.position.x = cam.viewportWidth / 2;
		cam.position.y = cam.viewportHeight / 2;

		// batch.setTransformMatrix(cam.combined.scl(PPM));

		player = lsm.getPlayer();
		player.scl(2f);
		player.setBody(CreateBox2D.createBox(FireFighterGame.world, 100, 100, player.getWidth() / 3.5f,
				player.getHeight() / 9, new Vector2(0, -player.getHeight() / 2.5f), BodyType.DynamicBody, "lamp",
				B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));

		cat = new Cat();

		b2dr = new Box2DDebugRenderer();

		font = new BitmapFont();

		TileObject.parseTiledObjectLayer(game.getWorld(), tileMap.getLayers().get("streetbound").getObjects());

		hud = new HUD(hudCam);

		createDebrisBox2D();
		camStyle = new CameraStyles();
		game.getWorld().setContactListener(this);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if (!player.getCurrentWeapon().isUse()) {
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
		}

		// if (player.getCurrentWeapon().isUsable()) {
		if (Gdx.input.isKeyJustPressed(Keys.J)) {
			player.use();
		}
		// }

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
		cat.update(dt);
		
		if(cat.isInRange(player.getWorldPosition().x, player.getWorldPosition().y) && !ground_cat_contact){
			cat.catState = CatState.Fall;
			cat.setVelocity(0, -2);
			
		}

		hud.update(dt);

		if (player.weaponState.equals(WeaponState.AXE)) {
			hud.hudState = HudState.AXE;
		} else if (player.weaponState.equals(WeaponState.EXTINGUISHER)) {
			hud.hudState = HudState.EXTINGUISHER;
		}

		camStyle.update(dt);
	}

	Vector3 initialCamPos = new Vector3(cam.position);

	@Override
	public void render() {
		// TODO Auto-generated method stub
		batch.setProjectionMatrix(cam.combined);

		float startx = cam.viewportWidth / 2;
		float starty = cam.viewportHeight / 2;
		float endWidth = tileMap.getProperties().get("width", Integer.class) * 32 - startx * 2;
		float endHeight = tileMap.getProperties().get("height", Integer.class) * 32 - starty * 2;
//		System.out.println("endx: " + endWidth + " endy: " + endHeight);
//		System.out.println(cam.position);

		camStyle.Lerp(cam, .5f, player.getWorldPosition());
		camStyle.Boundary(cam, startx, starty, endWidth, endHeight);
		initialCamPos = new Vector3(cam.position);

		tmr.setView(cam);
		batch.begin();
		tmr.render();
		b2dr.render(game.getWorld(), cam.combined.scl(PPM));
		batch.end();

		cam.update();

		player.render(batch);

		hud.render(batch);
		batch.begin();
			cat.render(batch);
		batch.end();
		
		batch.begin();
		font.draw(batch, "Level 6, time: " + timeElapsed, Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 + 50);
		batch.end();
	}

	public void createDebrisBox2D() {

		MapLayer layer = tileMap.getLayers().get("cat");
		if (layer == null)
			return;

		for (MapObject mo : layer.getObjects()) {

			// get debris position from tile map object layer
			float x = (float) mo.getProperties().get("x", Float.class);
			float y = (float) mo.getProperties().get("y", Float.class);

			// create new debris and add to crap list
			cat = new Cat(CreateBox2D.createBox(FireFighterGame.world, x, y, 20, 20, new Vector2(0, 0),
					BodyType.DynamicBody, "cat", B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));

		}

	}

	@Override
	public void dispose() {
		

	}
	
	boolean ground_cat_contact;
	@Override
	public void beginContact(Contact contact) {
//		Body ba = contact.getFixtureA().getBody();
//		Body bb = contact.getFixtureB().getBody();
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		//System.out.println(ba.getUserData() + " " +bb.getUserData());
		System.out.println(fa.getUserData() + " " +fb.getUserData());
		if(contact.getFixtureA().getBody().getUserData().equals("ground") && contact.getFixtureB().getBody().getUserData().equals("cat")){
			cat.catState = CatState.Stand;
			cat.setVelocity(0, 0);
			ground_cat_contact = true;
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
