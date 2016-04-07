package com.picklegames.levelStates;

import static com.picklegames.handlers.Box2D.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.picklegames.TweenAccessor.EntityTweenAccessor;
import com.picklegames.TweenAccessor.FontTweenAccessor;
import com.picklegames.TweenAccessor.ParticleEffectTweenAccessor;
import com.picklegames.entities.Entity;
import com.picklegames.handlers.Box2D.B2DVars;
import com.picklegames.handlers.Box2D.CreateBox2D;
import com.picklegames.managers.LevelStateManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;


public class Level2 extends LevelState {

	private BitmapFont font;
	private Entity box;
	private Box2DDebugRenderer b2dr;
	
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmr;
	
	
	public Level2(LevelStateManager lsm) {
		super(lsm);
		//init();

	}


	@Override
	public void init() {
		Tween.registerAccessor(ParticleEffect.class, new ParticleEffectTweenAccessor());
		Tween.registerAccessor(Entity.class, new EntityTweenAccessor());
		Tween.registerAccessor(BitmapFont.class, new FontTweenAccessor());
		
		box = new Entity();
		box.setBody(CreateBox2D.createBox(game.getWorld(), 100, 100, 20, 20, new Vector2(0, 0), "box", B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));
		box.setAnimation(new TextureRegion(new Texture("fire.png")), .5f);
		
		b2dr = new Box2DDebugRenderer();
		
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.setColor(font.getColor().b, font.getColor().g, font.getColor().r, 0);
		font.getData().setScale(1);
		
		tileMap = new TmxMapLoader().load("map/catlevel.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	private float timeElapsed;


	@Override
	public void update(float dt) {
		timeElapsed += dt;
		
		if(lsm.getTe().isFinished()){
			Tween.to(font, FontTweenAccessor.ALPHA, 3f).target(1).ease(TweenEquations.easeNone).start(lsm.getTweenManager());
			System.out.println(font.getColor().a);
		}
		

		if (timeElapsed >= 3) {
//			for (ParticleEffect p : lsm.getTe().getEffectList()) {
//				//System.out.println("min " + p.getEmitters().first().getGravity().getHighMin());
//				//System.out.println("max " + p.getEmitters().first().getGravity().getHighMax());
//
//				//Tween.to(p, ParticleEffectTweenAccessor.GRAVITY, 1f).target(-500, -1000).ease(TweenEquations.easeNone).start(lsm.getTweenManager());
//				//Tween.to(p, ParticleEffectTweenAccessor.LIFE, 1f).delay(0f).target(0, 0).ease(TweenEquations.easeNone).start(lsm.getTweenManager());
//			}
			
			if(timeElapsed >= 5){
				Tween.to(box, EntityTweenAccessor.XY, 2f).target(500, 500).ease(TweenEquations.easeOutSine).start(lsm.getTweenManager());
				//System.out.println("postition xy: " + box.getPosition().x * PPM+ " "+box.getPosition().y * PPM);
				
			}
			
		}
		
		
		box.update(dt);
	}

	@Override
	public void render() {
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		tmr.setView(cam);
		
		batch.begin();
			if(timeElapsed >= 10){
				tmr.render();
				b2dr.render(game.getWorld(), cam.combined.scl(PPM));
			}
		batch.end();
		
		if(timeElapsed >= 10){
			box.render(batch);
		}
		
		batch.begin();
		if(lsm.getTe().isFinished())
			font.draw(batch, "Level 2, time: " + timeElapsed, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		tmr.dispose();
		font.dispose();
		b2dr.dispose();
	}

}
