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

public class Level3 extends LevelState {

	// 2nd dialogue after saving the cat

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

	public Level3(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		init();

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		white = new Sprite(new Texture(("image/Backgrounds/whitebg.png")));
		white.setAlpha(0);
		white.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		white.setPosition(0, 0);
		d = new Dialogue("dialogue/dialogue2.txt", "");
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
		FireFighterGame.res.loadSound("sound/wac.mp3", "catS");
		playerS = FireFighterGame.res.getSound("playerS");
		girlS = FireFighterGame.res.getSound("girlS");
		catS = FireFighterGame.res.getSound("catS");
		currentSound = playerS;

		// cam.position.set(FireFighterGame.V_WIDTH / 2 ,
		// FireFighterGame.V_HEIGHT/ 2 , 0);
		cam.position.x = cam.viewportWidth / 2;
		cam.position.y = cam.viewportHeight / 2;
		cam.update();

		System.out.println(cam.position.toString());

		FireFighterGame.res.loadMusic("sound/Dialogue1,Dialogue3.mp3", "d_2");

	}

	@Override
	public void handleInput() {
		if (FireFighterGame.DEBUG) {
			if (Gdx.input.isKeyPressed(Keys.P)) {
				FireFighterGame.res.getMusic("d_2").stop();
				lsm.setState(LevelStateManager.Level_4);
			}
		}
	}

	float timeElapsed;
	boolean isTween = false;

	@Override
	public void update(float dt) {
		handleInput();

		if (!FireFighterGame.res.getMusic("d_2").isPlaying()) {
			FireFighterGame.res.getMusic("d_2").play();
		}

		if (d.isFinished()) {
			if (!isTween) {
				Tween.to(white, SpriteTweenAccessor.ALPHA, 2f).target(1).ease(TweenEquations.easeNone)
						.start(lsm.getTweenManager());
				isTween = true;
			}

			if (white.getColor().a >= .95f) {
				FireFighterGame.res.getMusic("d_2").stop();
				lsm.setState(LevelStateManager.Level_4);
			}
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
			font.setColor(Color.RED);
		} else {
			font.setColor(Color.GREEN);
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

		batch.draw(bg, 0, 0, FireFighterGame.V_WIDTH, FireFighterGame.V_HEIGHT);
		batch.draw(bgBar, 0, FireFighterGame.V_HEIGHT - FireFighterGame.V_HEIGHT / 4, FireFighterGame.V_WIDTH - 50,
				FireFighterGame.V_HEIGHT / 6);
		batch.draw(teenGirl.getFrame(), FireFighterGame.V_WIDTH - teenGirl.getFrame().getRegionWidth() * 1.5f, 5, 500,
				500);
		batch.draw(teenAni.getFrame(), 5, 5, 500, 500);

		font.draw(batch, d.getName(), FireFighterGame.V_WIDTH / 2 - 15, 625);
		font.draw(batch, d.getCharacterLine(), FireFighterGame.V_WIDTH / 2 - width / 2, 600);
		white.draw(batch);
		batch.end();

		d.render(batch);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		d.dispose();
		font.dispose();
		FireFighterGame.res.removeSound("playerS");
		FireFighterGame.res.removeSound("girldS");
		FireFighterGame.res.removeSound("catS");
		FireFighterGame.res.getMusic("d_2").dispose();
		;
	}

}
