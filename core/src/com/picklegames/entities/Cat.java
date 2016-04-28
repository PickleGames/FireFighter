package com.picklegames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.MathHelper;
import com.picklegames.handlers.Box2D.B2DVars;

public class Cat extends Entity{
	private TextureRegion[] catR, catF;
	public enum CatState{
		Stand, Fall
	}
	public CatState catState;
	public Cat(){
		super();
		init();
	}
	public Cat(Body body){
		super(body);
		init();
	}
	
	public void init(){
		catState = CatState.Stand;
		FireFighterGame.res.loadTexture("image/Character/Cat.png", "cat");
		FireFighterGame.res.loadTexture("image/Character/Catcute.png", "catF");
		Texture catr = FireFighterGame.res.getTexture("cat"); //500 116
		Texture catf = FireFighterGame.res.getTexture("catF"); //178 55
		catR = TextureRegion.split(catr, 500 / 5, 116)[0];
		catF = TextureRegion.split(catf, 178 / 2, 55)[0];
		
		animation.setFrames(catR, 1/8f);
		width = animation.getFrame().getRegionWidth() * .5f;
		height = animation.getFrame().getRegionHeight() * .5f;
	}
	
	boolean isCatS;
	boolean isCatF;
	@Override
	public void update(float dt) {
		super.update(dt);
		
		if(catState.equals(CatState.Stand)){
			if(!isCatS){
				animation.setFrames(catR, 1/8f);
				isCatS = true;
				isCatF = false;
			}
		}else if(catState.equals(CatState.Fall)){
			if(!isCatF){
				animation.setFrames(catF, 1f);
				isCatF = true;
				isCatS = false;
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(animation.getFrame(), body.getPosition().x * B2DVars.PPM - width / 2, body.getPosition().y * B2DVars.PPM - height / 2, width, height);
	}

	public boolean isInRange(float x2, float y2){
		float d = MathHelper.distanceEquation(getWorldPosition().x, getWorldPosition().y, x2, y2);
		
		return d < 450;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
