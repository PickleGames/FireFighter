package com.picklegames.levelStates;

import com.badlogic.gdx.Gdx;
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

public class Level7 extends LevelState{
	
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
	
	public Level7(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		d = new Dialogue("dialogue/dialogue3.txt", "Spring, 1995");
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.setColor(Color.WHITE);
		font.getData().scaleX = .4f;
		layout = new GlyphLayout(); //dont do this every frame! Store it as member

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
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		d.update(dt,currentSound);
		if(d.isFinished()){
			lsm.setState(LevelStateManager.Level_6);
		}
		
		if(d.isIntroDone()){
			//teenGirl.update(dt);
			//teenAni.update(dt);
			
			if(d.getName().equals("YOU")){
				collAni.setCurrentFrame(d.getCurrentLine().getAnimationIndex());
			}else if(d.getName().equals("MOM")){
				mom.setCurrentFrame(d.getCurrentLine().getAnimationIndex());
			}
		}
		if(d.getName().equals("YOU")){
			font.setColor(Color.BLUE);
		}else{
			font.setColor(Color.PURPLE);
		}
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		layout.setText(font, d.getCharacterLine());
		float width = layout.width;// contains the width of the current set text
		//float height = layout.height; // contains the height of the current set text
		
		batch.begin();
		
			batch.draw(bg, 0, 0, FireFighterGame.V_WIDTH, FireFighterGame.V_HEIGHT);
			batch.draw(bgBar, 0, FireFighterGame.V_HEIGHT -  FireFighterGame.V_HEIGHT / 4, FireFighterGame.V_WIDTH - 50, FireFighterGame.V_HEIGHT / 6);
			batch.draw(mom.getFrame(), FireFighterGame.V_WIDTH - 505, 5 , 500, 500);
			batch.draw(collAni.getFrame(), 5, 5 ,500, 500);
			
			font.draw(batch, d.getName(), FireFighterGame.V_WIDTH/2  - 15, 625);
			font.draw(batch, d.getCharacterLine(),  FireFighterGame.V_WIDTH/2 - width/2, 600);
		batch.end();
		
		d.render(batch);
	}

	@Override
	public void dispose() {
		font.dispose();
		
	}

}
