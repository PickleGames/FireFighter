package com.picklegames.levelStates;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.Animation;
import com.picklegames.handlers.dialogue.Dialogue;
import com.picklegames.managers.LevelStateManager;

public class Level3 extends LevelState{
	
	private Dialogue d;
	private BitmapFont font;
	
	private Animation teenGirl;
	private TextureRegion[] girlReg;
	
	public Level3(LevelStateManager lsm) {
		super(lsm);
		// TODO Auto-generated constructor stub
		init();
		FireFighterGame.res.loadTexture("image/Character/teenGirl.png", "girl");
		FireFighterGame.res.loadTexture("image/Character/youFace.png", "you");
		
		girlReg = TextureRegion.split(FireFighterGame.res.getTexture("girl"), 300, 300)[0];
		teenGirl = new Animation();
		teenGirl.setFrames(girlReg,  6f);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		d = new Dialogue("dialogue/dialogue1.txt");
		font = new BitmapFont();

	
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		d.update(dt);
		if(d.isFinished()) System.out.println("done");
		
		teenGirl.update(dt);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		batch.begin();

			batch.draw(teenGirl.getFrame(), 650, 50);
			batch.draw(FireFighterGame.res.getTexture("you"), 55, 50);
			
			font.draw(batch, d.getName(), 450, 625);
			font.draw(batch, d.getCharacterLine(), 400 - d.getCharacterLine().length()*font.getScaleX() * 2, 600);
		batch.end();
	}

	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		d.dispose();
	}

}
