package com.picklegames.entities;

import static com.picklegames.handlers.Box2D.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.picklegames.entities.weapons.Axe;
import com.picklegames.entities.weapons.Extinguisher;
import com.picklegames.entities.weapons.Weapon;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.Box2D.B2DVars;

import com.picklegames.handlers.Box2D.CreateBox2D;

public class Lamp extends Entity {

	private TextureRegion[] textureR;
	private Texture textureYoungStand;
	private Texture textureAdultStand;
	private Vector2 velocity;
	private Weapon[] weapons;
	private Weapon currentWeapon;


	private enum CharacterState {
		YOUNG, ADULT
	}

	private enum WeaponState {
		EXTINGUISHER, AXE, BEAR
	}

	public CharacterState characterState;
	public WeaponState weaponState;

	public Lamp() {
		super();
		init();
	}

	public Lamp(Body body) {
		super(body);
		init();
	}

	public void init() {

		characterState = CharacterState.ADULT;
		weaponState = WeaponState.EXTINGUISHER;

		FireFighterGame.res.loadTexture("image/Character/WalkingSprites.png", "YoungWalkLamp");
		FireFighterGame.res.loadTexture("image/Character/Stand_1.png", "Lamp_Stand_Young");
		FireFighterGame.res.loadTexture("image/Character/adult.png", "Lamp_Stand_Adult");
		Texture texture = FireFighterGame.res.getTexture("YoungWalkLamp");
		textureR = TextureRegion.split(texture, 80, 150)[0]; // 80, 150
		textureYoungStand = FireFighterGame.res.getTexture("Lamp_Stand_Young");
		textureAdultStand = FireFighterGame.res.getTexture("Lamp_Stand_Adult");

		animation.setFrames(textureR, 1 / 8f);

		width = textureR[0].getRegionWidth() * 1.5f;
		height = textureR[0].getRegionHeight() * 1.5f;

		velocity = new Vector2(0, 0);
		weapons = new Weapon[2];

	}

	public void createWeapon() {
		Extinguisher ex = new Extinguisher(CreateBox2D.createBox(FireFighterGame.world, getPosition().x,
				getPosition().y, 1, 1, new Vector2(0, 0),BodyType.DynamicBody, "extinguisher", B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));

		Axe axe = new Axe(CreateBox2D.createBox(FireFighterGame.world, getPosition().x, getPosition().y, 1, 1,
				new Vector2(0, 0), BodyType.DynamicBody ,"extinguisher", B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));

		weapons[0] = ex;
		weapons[1] = axe;

	}

	@Override
	public void update(float dt) {
		super.update(dt);
		// System.out.println("Body linear: "+
		// body.getLinearVelocity().toString());
		if (weaponState.equals(WeaponState.EXTINGUISHER))
			currentWeapon = weapons[0];
		else if (weaponState.equals(WeaponState.AXE))
			currentWeapon = weapons[1];

		if (characterState.equals(CharacterState.ADULT)) {
			currentWeapon.update(dt);
			currentWeapon.setPosition(this.getPosition().x, this.getPosition().y);
		}

	}

	public void use() {
		currentWeapon.use();
	}

	public Weapon getCurrentWeapon() {
		return currentWeapon;
	}

	public void setCurrentWeapon(Weapon currentWeapon) {
		this.currentWeapon = currentWeapon;
	}
	
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		
		if (characterState.equals(CharacterState.YOUNG)) {
			if (velocity.x == 0 && velocity.y == 0) {
				spriteBatch.draw(textureYoungStand, getPosition().x * PPM - width / 2,
						getPosition().y * PPM - height / 2, width, height);
				// spriteBatch.draw(textureStand, body.getPosition().x * PPM -
				// width
				// / 2, body.getPosition().y * PPM - height / 2, width / 2,
				// height /
				// 2, 80, 150, 1, 1, 0);
			} else {
				if (velocity.x > 0)
					spriteBatch.draw(animation.getFrame(), getPosition().x * PPM - width / 2,
							getPosition().y * PPM - height / 2, width / 2, height / 2, width, height, -1, 1, 0);
				else if (velocity.x < 0)
					spriteBatch.draw(animation.getFrame(), getPosition().x * PPM - width / 2,
							getPosition().y * PPM - height / 2, width / 2, height / 2, width, height, 1, 1, 0);
				else {
					spriteBatch.draw(animation.getFrame(), getPosition().x * PPM - width / 2,
							getPosition().y * PPM - height / 2, width / 2, height / 2, width, height, 1, 1, 0);
				}
			}
		} else if (characterState.equals(CharacterState.ADULT)) {
			spriteBatch.draw(textureAdultStand, getPosition().x * PPM - width / 2, getPosition().y * PPM - height / 2,
					width, height);
			currentWeapon.render(spriteBatch);
		}

		spriteBatch.end();
	}

	public void dispose() {
		super.dispose();
		for (Weapon w : weapons) {
			w.dispose();
		}
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public void setVelocity(float x, float y) {
		this.velocity = new Vector2(x, y);
	}

	public void setVelocityX(float x) {
		this.velocity.x = x;
	}

	public void setVelocityY(float y) {
		this.velocity.y = y;
	}
}
