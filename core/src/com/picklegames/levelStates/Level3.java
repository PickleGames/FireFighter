package com.picklegames.levelStates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.Animation;
import com.picklegames.handlers.dialogue.Dialogue;
import com.picklegames.managers.LevelStateManager;

public class Level3 extends LevelState{
	
	private Dialogue d;
	private BitmapFont font;
	private GlyphLayout layout;
	
	private Texture bg;
	private Texture bgBar;
	
	private Animation teenGirl;
	private TextureRegion[] girlReg;
	
	private Animation teenAni;
	private TextureRegion[] teenReg;
	
	
	public Level3(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		init();
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		d = new Dialogue("dialogue/dialogue1.txt");
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.setColor(Color.WHITE);
		font.getData().scaleX = .4f;
		layout = new GlyphLayout(); //dont do this every frame! Store it as member

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
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(float dt) {
		d.update(dt);
		if(d.isFinished()){
			lsm.setState(lsm.Level_4);
		}
		
		
		teenGirl.update(dt);
		teenAni.update(dt);
		
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
		float height = layout.height; // contains the height of the current set text
		batch.begin();
		
			batch.draw(bg, 0, 0, FireFighterGame.V_WIDTH, FireFighterGame.V_HEIGHT);
			batch.draw(bgBar, 0, FireFighterGame.V_HEIGHT -  FireFighterGame.V_HEIGHT / 4, FireFighterGame.V_WIDTH - 50, FireFighterGame.V_HEIGHT / 6);
			batch.draw(teenGirl.getFrame(), FireFighterGame.V_WIDTH - 505, 5 , 500, 500);
			batch.draw(teenAni.getFrame(), 5, 5 ,500, 500);
			
			font.draw(batch, d.getName(), FireFighterGame.V_WIDTH/2  - 15, 625);
			font.draw(batch, d.getCharacterLine(),  FireFighterGame.V_WIDTH/2 - width/2, 600);
		batch.end();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		d.dispose();
	}

}
