package com.picklegames.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.picklegames.game.FireFighterGame;

public class HUD {

	private Animation weapons;
	private TextureRegion[] weapReg;
	private Texture box;
	
	public enum HudState {
		EXTINGUISHER, AXE
	}
	
	public HudState hudState;
	
	public HUD(){
		init();
	}
	
	public void init(){
		FireFighterGame.res.loadTexture("image/Objects/weaponsHud.png", "weapons");
		FireFighterGame.res.loadTexture("image/Objects/hudBox.png", "hbox");
		
		weapons = new Animation();
		weapReg = TextureRegion.split(FireFighterGame.res.getTexture("weapons"), 100, 100)[0];
		weapons.setFrames(weapReg);
		
		box = FireFighterGame.res.getTexture("hbox");
		
		hudState = HudState.EXTINGUISHER;
	}
	
	public void update(float dt){
		
		weapons.update(dt);
		
		if(hudState.equals(HudState.AXE)){
			weapons.setCurrentFrame(0);
		}else{
			weapons.setCurrentFrame(1);
		}
		
	}
	
	public void render(SpriteBatch batch){
		
		batch.begin();
		batch.draw(box, 10, FireFighterGame.V_HEIGHT - 110);
		batch.draw(weapons.getFrame(), 10, FireFighterGame.V_HEIGHT - 110);
		batch.end();
	}
	
}
