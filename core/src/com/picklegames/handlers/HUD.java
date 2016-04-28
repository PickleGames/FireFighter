package com.picklegames.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.picklegames.game.FireFighterGame;

public class HUD {

	private Animation weapons;
	private TextureRegion[] weapReg;
	private Texture box;
	private OrthographicCamera cam;
	public enum HudState {
		EXTINGUISHER, AXE
	}
	
	public HudState hudState;
	
	public HUD(OrthographicCamera cam){
		this.cam = cam;
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
		batch.draw(box, cam.position.x - cam.viewportWidth / 2, 0, 70, 70);
		batch.draw(weapons.getFrame(), cam.position.x - cam.viewportWidth / 2, 0, 70, 70);
		batch.end();
	}
	
}
