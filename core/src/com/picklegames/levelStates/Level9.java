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

public class Level9 extends LevelState{
	
	private Dialogue d;
	private BitmapFont font;
	private GlyphLayout layout;
	
	private Texture bg;
	private Texture bgBar;
	
	private Animation ani1;
	private TextureRegion[] reg1;
	
	private Animation ani2;
	private TextureRegion[] reg2;
	
	private Sound playerS, girlS, catS, currentSound;
	
	public Level9(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		d = new Dialogue("dialogue/dialogue5.txt", "Spring, 1995");
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.setColor(Color.WHITE);
		font.getData().scaleX = .4f;
		layout = new GlyphLayout(); //dont do this every frame! Store it as member

		FireFighterGame.res.loadTexture("image/Character/firemanFace.png", "manFace");
		FireFighterGame.res.loadTexture("image/Character/vetFace.png", "vet");
		FireFighterGame.res.loadTexture("image/Backgrounds/vetClinic.png", "vet_bg");
		FireFighterGame.res.loadTexture("image/Backgrounds/diaBar.png", "diaBox");
		
		
		reg1 = TextureRegion.split(FireFighterGame.res.getTexture("manFace"), 300, 300)[0];
		ani1 = new Animation();
		ani1.setFrames(reg1);
		
		reg2 = TextureRegion.split(FireFighterGame.res.getTexture("vet"), 300, 300)[0];
		ani2 = new Animation();
		ani2.setFrames(reg2);
		
		
		bg = FireFighterGame.res.getTexture("vet_bg");
		bgBar = FireFighterGame.res.getTexture("diaBox");
		
		FireFighterGame.res.loadSound("sound/wac.mp3", "playerS");
		FireFighterGame.res.loadSound("sound/girlWomp.mp3", "girlS");
		FireFighterGame.res.loadSound("sound/wac.mp3", "catS");
		playerS = FireFighterGame.res.getSound("playerS");
		girlS = FireFighterGame.res.getSound("girlS");
		catS = FireFighterGame.res.getSound("catS");
		currentSound = playerS;
		
		//cam.position.set(FireFighterGame.V_WIDTH / 2 , FireFighterGame.V_HEIGHT/ 2 , 0);
		cam.update();
		System.out.println(cam.position.toString());
		
		FireFighterGame.res.loadMusic("sound/Dialogue 5.mp3", "d_5");
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
		if(Gdx.input.isKeyPressed(Keys.P)){
			FireFighterGame.res.getMusic("d_5").stop();
			lsm.setState(LevelStateManager.Level_10);
		}
		
		if(!FireFighterGame.res.getMusic("d_5").isPlaying()){
			FireFighterGame.res.getMusic("d_5").play();
		}

		if(d.isFinished()){
			FireFighterGame.res.getMusic("d_5").stop();
			lsm.setState(LevelStateManager.Level_10);
		}
		
		if(d.getName().equals("YOU")){
			currentSound = playerS;
		}else if(d.getName().equals("VET JOYCE")){
			currentSound = girlS;
		}else if(d.getName().equals("CAT")){
			currentSound = catS;

		}
		
		d.update(dt, currentSound);
		
		if(d.isIntroDone()){
			//teenGirl.update(dt);
			//teenAni.update(dt);
			
			if(d.getName().equals("YOU")){
				ani1.setCurrentFrame(d.getCurrentLine().getAnimationIndex());
			}else if(d.getName().equals("VET JOYCE")){
				ani2.setCurrentFrame(d.getCurrentLine().getAnimationIndex());
			}
		}
		
		if(d.getName().equals("YOU")){
			font.setColor(Color.BLUE);
		}else if(d.getName().equals("VET JOYCE")){
			font.setColor(Color.PURPLE);
		}else{
			font.setColor(Color.GREEN);
		}
		
		
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		layout.setText(font, d.getCharacterLine());
		
		float width = layout.width;// contains the width of the current set text
		//float height = layout.height; // contains the height of the current set text
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
			batch.draw(bg, 0, 0, cam.viewportWidth, cam.viewportHeight);
			batch.draw(bgBar, 25, cam.viewportHeight -  cam.viewportHeight / 4, cam.viewportWidth - 50, cam.viewportHeight / 6);
			batch.draw(ani1.getFrame(), 5, 5 ,500, 500);
			batch.draw(ani2.getFrame(), cam.viewportWidth - 505, 5 , 500, 500);
			
			font.draw(batch, d.getCharacterLine(),  cam.viewportWidth/2 - width/2, 600);
			layout.setText(font, d.getName());
			width = layout.width;
			font.draw(batch, d.getName(), cam.viewportWidth/2  - width/2, 625);
			
		batch.end();
		
		d.render(batch);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		d.dispose();
		FireFighterGame.res.removeSound("playerS");
		FireFighterGame.res.removeSound("girldS");
		FireFighterGame.res.removeSound("catS");
		FireFighterGame.res.getMusic("d_5").dispose();

	}

}
