package com.picklegames.levelStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

public class Level1 extends LevelState {

	// First dialogue before cat

	private Dialogue d;
	private BitmapFont font;
	private GlyphLayout layout;

	private Texture bg;
	private Texture bgBar;

	private Animation teenGirl;
	private TextureRegion[] girlReg;

	private Animation teenAni;
	private TextureRegion[] teenReg;

	private Sound playerS, girlS, catS, currentSound;
	private Sprite white;

	public Level1(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		init();

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		white = new Sprite(new Texture(("image/Backgrounds/whitebg.png")));

		Tween.registerAccessor(Sprite.class, new SpriteTweenAccessor());
		white.setAlpha(1);
		white.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Tween.to(white, SpriteTweenAccessor.ALPHA, 3f).target(0).delay(3f).ease(TweenEquations.easeNone)
				.start(lsm.getTweenManager());

		d = new Dialogue("dialogue/dialogue1.txt", "Summer, 1985");
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.setColor(Color.WHITE);
		font.getData().scaleX = .45f;
		layout = new GlyphLayout(); // dont do this every frame! Store it as
									// member

		FireFighterGame.res.loadTexture("image/Character/teenGirl.png", "girl");
		FireFighterGame.res.loadTexture("image/Character/teenFace.png", "teen");
		FireFighterGame.res.loadTexture("image/Backgrounds/bg1.png", "bg");
		FireFighterGame.res.loadTexture("image/Backgrounds/diaBar.png", "diaBox");

		girlReg = TextureRegion.split(FireFighterGame.res.getTexture("girl"), 300, 300)[0];
		teenGirl = new Animation();
		teenGirl.setFrames(girlReg, 8f);

		teenReg = TextureRegion.split(FireFighterGame.res.getTexture("teen"), 300, 300)[0];
		teenAni = new Animation();
		teenAni.setFrames(teenReg, 16f);

		bg = FireFighterGame.res.getTexture("bg");
		bgBar = FireFighterGame.res.getTexture("diaBox");

		FireFighterGame.res.loadSound("sound/wac.mp3", "playerS");
		FireFighterGame.res.loadSound("sound/girlWomp.mp3", "girlS");
		FireFighterGame.res.loadSound("sound/Meow.mp3", "catS");
		playerS = FireFighterGame.res.getSound("playerS");
		girlS = FireFighterGame.res.getSound("girlS");
		catS = FireFighterGame.res.getSound("catS");
		currentSound = playerS;

		cam.position.x = cam.viewportWidth / 2;
		cam.position.y = cam.viewportHeight / 2;
		cam.update();
		System.out.println(cam.position.toString());

		FireFighterGame.res.loadMusic("sound/Level 1, Level 2.mp3", "l_1");
		FireFighterGame.res.getMusic("l_1").play();
	}

	@Override
	public void handleInput() {

		if (Gdx.input.isKeyPressed(Keys.P)) {
			FireFighterGame.res.getMusic("l_1").stop();
			lsm.setState(LevelStateManager.Level_2);
		}

	}

	float timeElapsed;
	boolean isCallTween = false;

	public void callTween() {
		Tween.to(white, SpriteTweenAccessor.ALPHA, 1f).target(1).ease(TweenEquations.easeNone)
				.start(lsm.getTweenManager());
		isCallTween = true;
	}

	@Override
	public void update(float dt) {
		handleInput();

		timeElapsed += dt;

		if (!FireFighterGame.res.getMusic("l_1").isPlaying()) {
			FireFighterGame.res.getMusic("l_1").play();
		}

		if (white.getColor().a <= .1f) {
			if (d.isFinished()) {

				timeElapsed += dt;
				if (!isCallTween) {
					callTween();
				}
				if (white.getColor().a >= .9f) {
					lsm.setState(LevelStateManager.Level_2);
				}
				FireFighterGame.res.getMusic("l_1").stop();

			}

			if (d.getName().equals("YOU")) {
				currentSound = playerS;
			} else if (d.getName().equals("JOYCE")) {
				currentSound = girlS;
			} else if (d.getName().equals("CAT")) {
				currentSound = catS;

			}

			d.update(dt, currentSound);

			if (d.isIntroDone()) {
				// teenGirl.update(dt);
				// teenAni.update(dt);

				if (d.getName().equals("YOU")) {
					teenAni.setCurrentFrame(d.getCurrentLine().getAnimationIndex());
				} else if (d.getName().equals("JOYCE")) {
					teenGirl.setCurrentFrame(d.getCurrentLine().getAnimationIndex());
				}
			}

			if (d.getName().equals("YOU")) {
				font.setColor(Color.BLUE);
			} else if (d.getName().equals("JOYCE")) {
				font.setColor(Color.PURPLE);
			} else {
				font.setColor(Color.GREEN);
			}
		}

	}

	@Override
	public void render() {

		layout.setText(font, d.getCharacterLine());
		float width = layout.width;// contains the width of the current set text
		// float height = layout.height; // contains the height of the current
		// set text
		batch.setProjectionMatrix(cam.combined);

		batch.begin();

		batch.draw(bg, 0, 0, FireFighterGame.V_WIDTH, FireFighterGame.V_HEIGHT);
		batch.draw(bgBar, 0, FireFighterGame.V_HEIGHT - FireFighterGame.V_HEIGHT / 4, FireFighterGame.V_WIDTH - 50,
				FireFighterGame.V_HEIGHT / 6);
		batch.draw(teenGirl.getFrame(), FireFighterGame.V_WIDTH - teenGirl.getFrame().getRegionWidth() * 1.5f, 5, 500,
				500);
		batch.draw(teenAni.getFrame(), 5, 5, 500, 500);

		font.draw(batch, d.getName(), FireFighterGame.V_WIDTH / 2 - 15, 625);
		font.draw(batch, d.getCharacterLine(), FireFighterGame.V_WIDTH / 2 - width / 2, 600);

		batch.end();

		d.render(batch);

		batch.begin();
		white.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		d.dispose();
		FireFighterGame.res.removeSound("playerS");
		FireFighterGame.res.removeSound("girldS");
		FireFighterGame.res.removeSound("catS");
		FireFighterGame.res.getMusic("l_1").dispose();
	}

}
