package com.picklegames.levelStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.picklegames.TweenAccessor.SpriteTweenAccessor;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.Animation;
import com.picklegames.handlers.dialogue.Dialogue;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

public class Level15 extends LevelState {

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

	private Sound playerS, girlS, catS, currentSound;
	private Sprite white;

	public Level15(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		d = new Dialogue("dialogue/dialogue8.txt", "December, 2000");
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.setColor(Color.WHITE);
		font.getData().scaleX = .4f;
		layout = new GlyphLayout(); // dont do this every frame! Store it as
									// member
		white = new Sprite(new Texture(("image/Backgrounds/whitebg.png")));
		white.setAlpha(0);
		white.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		white.setPosition(0, 0);

		FireFighterGame.res.loadTexture("image/Character/oldMom.png", "oMom");
		FireFighterGame.res.loadTexture("image/Character/firemanFace.png", "fireman");
		FireFighterGame.res.loadTexture("image/Backgrounds/momroomfire.png", "momRoom");
		FireFighterGame.res.loadTexture("image/Backgrounds/diaBar.png", "diaBox");

		momReg = TextureRegion.split(FireFighterGame.res.getTexture("oMom"), 300, 300)[0];
		mom = new Animation();
		mom.setFrames(momReg, 8f);

		collReg = TextureRegion.split(FireFighterGame.res.getTexture("fireman"), 300, 300)[0];
		collAni = new Animation();
		collAni.setFrames(collReg, 16f);

		// FireFighterGame.res.loadTexture("image/Background/momroomfire.png",
		// "momroomfire");
		bg = FireFighterGame.res.getTexture("momRoom");
		bgBar = FireFighterGame.res.getTexture("diaBox");

		FireFighterGame.res.loadSound("sound/wac.mp3", "playerS");
		FireFighterGame.res.loadSound("sound/girlWomp.mp3", "girlS");
		FireFighterGame.res.loadSound("sound/wac.mp3", "catS");
		playerS = FireFighterGame.res.getSound("playerS");
		girlS = FireFighterGame.res.getSound("girlS");
		catS = FireFighterGame.res.getSound("catS");
		currentSound = playerS;

		FireFighterGame.res.loadMusic("sound/soundcool.mp3", "d_6");

		cam.update();

	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	boolean isTween = false;
	float timeElapsed;

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

		if (!FireFighterGame.res.getMusic("d_6").isPlaying()) {
			FireFighterGame.res.getMusic("d_6").play();
		}

		d.update(dt, currentSound);
		if (d.isFinished()) {
			// FireFighterGame.res.getMusic("d_6").stop();
			if (!isTween) {
				Tween.to(white, SpriteTweenAccessor.ALPHA, 6f).target(1).ease(TweenEquations.easeNone)
						.start(lsm.getTweenManager());
				isTween = true;
			}

			if (white.getColor().a >= .95f) {
				timeElapsed += dt;
				if (timeElapsed >= 10f) {
					// FireFighterGame.res.getMusic("d_6").stop();
					lsm.setState(LevelStateManager.End);
				}
			}
			// lsm.setState(LevelStateManager.End);
		}
		if (d.getName().equals("YOU")) {
			currentSound = playerS;
		} else if (d.getName().equals("MOM")) {
			currentSound = girlS;
		} else if (d.getName().equals("CAT")) {
			currentSound = catS;
		}
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
		batch.draw(collAni.getFrame(), 5, 5, 500, 500);
		batch.draw(mom.getFrame(), cam.viewportWidth - 505, 5, 500, 500);

		font.draw(batch, d.getCharacterLine(), cam.viewportWidth / 2 - width / 2, 600);
		layout.setText(font, d.getName());
		width = layout.width;
		font.draw(batch, d.getName(), cam.viewportWidth / 2 - width / 2, 625);
		white.draw(batch);
		batch.end();

		d.render(batch);
	}

	@Override
	public void dispose() {
		font.dispose();

	}

}
