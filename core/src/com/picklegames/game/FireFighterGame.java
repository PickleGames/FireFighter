package com.picklegames.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.picklegames.handlers.Content;
import com.picklegames.managers.GameStateManager;

public class FireFighterGame extends ApplicationAdapter {
	public static final int V_WIDTH = 1080;
	public static final int V_HEIGHT = 720;
	public static final float SCALE = 1f;
	public static Content res;

	private SpriteBatch batch;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private GameStateManager gsm;

	
	private World world;


	@Override
	public void create() {
		world = new World(new Vector2(0, 0), true);

		// load content
		res = new Content();

		// set up cameras
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH / SCALE, V_HEIGHT / SCALE);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);

		// load up game
		gsm = new GameStateManager(this);

	}

	public void update(float dt) {

		gsm.update(dt);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// batch.setProjectionMatrix(cam.combined);

		update(Gdx.graphics.getDeltaTime());
		
		gsm.render();
		
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

	}

	@Override
	public void dispose() {
		super.dispose();

	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public OrthographicCamera getHudCam() {
		return hudCam;
	}

	public GameStateManager getGsm() {
		return gsm;
	}


	public World getWorld() {
		return world;
	}
}
