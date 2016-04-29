package com.picklegames.levelStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.Animation;
import com.picklegames.handlers.dialogue.Dialogue;
import com.picklegames.managers.LevelStateManager;

public class Level5 extends LevelState {

	// 3rd dialogue with mom in college

	private Dialogue d;
	private BitmapFont font;
	private GlyphLayout layout;

	private Texture bg;
	private Texture bgBar;

	private Animation mom;
	private TextureRegion[] momReg;

	private Animation collAni;
	private TextureRegion[] collReg;

	private Sound playerS, currentSound;

	public Level5(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		init();
	}

	public void init() {
		// TODO Auto-generated method stub
		d = new Dialogue("dialogue/dialogue3.txt", "Spring, 1995");
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.setColor(Color.WHITE);
		font.getData().scaleX = .4f;
		layout = new GlyphLayout(); // dont do this every frame! Store it as
									// member

		FireFighterGame.res.loadTexture("image/Character/momFace.png", "mom");
		FireFighterGame.res.loadTexture("image/Character/collegeFace.png", "college");
		FireFighterGame.res.loadTexture("image/Backgrounds/dorm.png", "dorm_bg");
		FireFighterGame.res.loadTexture("image/Backgrounds/diaBar.png", "diaBox");

		momReg = TextureRegion.split(FireFighterGame.res.getTexture("mom"), 300, 300)[0];
		mom = new Animation();
		mom.setFrames(momReg, 8f);

		collReg = TextureRegion.split(FireFighterGame.res.getTexture("college"), 300, 300)[0];
		collAni = new Animation();
		collAni.setFrames(collReg, 16f);

		bg = FireFighterGame.res.getTexture("dorm_bg");
		bgBar = FireFighterGame.res.getTexture("diaBox");

		FireFighterGame.res.loadSound("sound/wac.mp3", "playerS");
		playerS = FireFighterGame.res.getSound("playerS");
		currentSound = playerS;

		cam.update();
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

		if (Gdx.input.isKeyPressed(Keys.P)) {
			lsm.setState(LevelStateManager.Level_6);
		}

	}

	@Override
	public void update(float dt) {
		handleInput();
		d.update(dt, currentSound);
		if (d.isFinished()) {
			lsm.setState(LevelStateManager.Level_6);
		}
//		System.out.println("CAM WIDTH " + cam.viewportWidth + " CAM HEIGHT " + cam.viewportHeight);
//		System.out.println("CAM POS " + cam.position);
		if (d.isIntroDone()) {
			// teenGirl.update(dt);
			// teenAni.update(dt);

			if (d.getName().equals("YOU")) {
				collAni.setCurrentFrame(d.getCurrentLine().getAnimationIndex());
			} else if (d.getName().equals("MOM")) {
				mom.setCurrentFrame(d.getCurrentLine().getAnimationIndex());
			}
		}
		if (d.getName().equals("YOU")) {
			font.setColor(Color.BLUE);
		} else {
			font.setColor(Color.PURPLE);
		}

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		layout.setText(font, d.getCharacterLine());
		float width = layout.width;// contains the width of the current set text
		// float height = layout.height; // contains the height of the current
		// set text
		batch.setProjectionMatrix(cam.combined);

		batch.begin();

		batch.draw(bg, 0, 0, cam.viewportWidth, cam.viewportHeight);
		batch.draw(bgBar, 25, cam.viewportHeight - cam.viewportHeight / 4, cam.viewportWidth - 50,
				cam.viewportHeight / 6);
		batch.draw(mom.getFrame(), cam.viewportWidth - 505, 5, 500, 500);
		batch.draw(collAni.getFrame(), 5, 5, 500, 500);

		font.draw(batch, d.getCharacterLine(), cam.viewportWidth / 2 - width / 2, 600);
		layout.setText(font, d.getName());
		width = layout.width;
		font.draw(batch, d.getName(), cam.viewportWidth / 2 - width / 2, 625);
		batch.end();

		d.render(batch);
	}

	@Override
	public void dispose() {
		font.dispose();

	}

}
