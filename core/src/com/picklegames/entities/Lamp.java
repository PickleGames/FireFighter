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
import com.picklegames.entities.weapons.NoWep;
import com.picklegames.entities.weapons.Weapon;
import com.picklegames.game.FireFighterGame;
import com.picklegames.handlers.Box2D.B2DVars;
import com.picklegames.handlers.Box2D.CreateBox2D;

public class Lamp extends Entity {

	private TextureRegion[] textureR, textureAdult_ext, textureAdult_axe, textureAdult_ext_use, textureAdult_axe_use;
	private Texture textureYoungStand;


	private Vector2 velocity;
	private Weapon[] weapons;
	private Weapon currentWeapon;

	public enum CharacterState {
		YOUNG, ADULT
	}

	public enum WeaponState {
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

		// FireFighterGame.res.loadTexture("image/Character/FireManSide.png",
		// "Lamp_Stand_Adult");

		FireFighterGame.res.loadTexture("image/Character/FiremanAxe_walk.png", "FiremanAxe_walk");
		FireFighterGame.res.loadTexture("image/Character/FiremanExst_walk.png", "FiremanExst_walk");
		FireFighterGame.res.loadTexture("image/Character/FiremanAxe_use.png", "FiremanAxe_use");
		FireFighterGame.res.loadTexture("image/Character/FiremanExst_use.png", "FiremanExst_use");

		Texture texture = FireFighterGame.res.getTexture("YoungWalkLamp");
		Texture texture1 = FireFighterGame.res.getTexture("FiremanExst_walk");
		Texture texture2 = FireFighterGame.res.getTexture("FiremanAxe_walk");
		Texture texture3 = FireFighterGame.res.getTexture("FiremanExst_use");
		Texture texture4 = FireFighterGame.res.getTexture("FiremanAxe_use");

		textureR = TextureRegion.split(texture, 80, 150)[0]; // 80, 150
		textureAdult_ext = TextureRegion.split(texture1, 1600 / 5, 750)[0];
		textureAdult_axe = TextureRegion.split(texture2, 1800 / 5, 750)[0];
		textureAdult_ext_use = TextureRegion.split(texture3, 997 / 3, 605)[0];
		textureAdult_axe_use = TextureRegion.split(texture4, 1231 / 3, 633)[0];

		// textureYoungStand =
		// FireFighterGame.res.getTexture("Lamp_Stand_Young");

		animation.setFrames(textureR, 1 / 8f);

		width = textureR[0].getRegionWidth() * 1.5f;
		height = textureR[0].getRegionHeight() * 1.5f;

		velocity = new Vector2(0, 0);
		weapons = new Weapon[3];
		Extinguisher ex = new Extinguisher(CreateBox2D.createBox(FireFighterGame.world, 0, 0, 1, 1, new Vector2(0, 0),
				BodyType.DynamicBody, "extinguisher", B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));

		Axe axe = new Axe(CreateBox2D.createBox(FireFighterGame.world, 0, 0, 1, 1, new Vector2(0, 0),
				BodyType.DynamicBody, "extinguisher", B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));

		NoWep nowep = new NoWep(CreateBox2D.createBox(FireFighterGame.world, 0, 0, 1, 1, new Vector2(0, 0),
				BodyType.DynamicBody, "noWep", B2DVars.BIT_PLAYER, B2DVars.BIT_GROUND));

		weapons[0] = ex;
		weapons[1] = axe;
		weapons[2] = nowep;

		currentWeapon = weapons[2];
	}

	public void createWeapon() {

	}

	boolean isSetAxeWalk = false;
	boolean isSetExtWalk = false;
	boolean isSetAxeUse = false;
	boolean isSetExtUse = false;

	@Override
	public void update(float dt) {
		super.update(dt);
		// animation.play(dt, Animation.INDEFINITE);
		// System.out.println("Body linear: "+
		// body.getLinearVelocity().toString());

		if (characterState.equals(CharacterState.ADULT)) {
			if (weaponState.equals(WeaponState.EXTINGUISHER)) {
				if (currentWeapon.isUse() && !isSetExtUse) {
					animation.setFrames(textureAdult_ext_use, 1 / 8f);
					isSetExtUse = true;
					isSetExtWalk = false;
					isSetAxeUse = false;
				}
				if (!currentWeapon.isUse() && !isSetExtWalk) {
					currentWeapon = weapons[0];
					// currentWeapon.reset();
					animation.setFrames(textureAdult_ext, 1 / 8f);
					isSetExtWalk = true;
					isSetAxeWalk = false;
					isSetExtUse = false;
				}
			} else if (weaponState.equals(WeaponState.AXE)) {
				if (currentWeapon.isUse() && !isSetAxeUse) {
					animation.setFrames(textureAdult_axe_use, 1 / 7f);
					isSetAxeUse = true;
					isSetAxeWalk = false;
					isSetExtUse = false;
				}
				if (!currentWeapon.isUse() && !isSetAxeWalk) {
					currentWeapon = weapons[1];
					// currentWeapon.reset();
					animation.setFrames(textureAdult_axe, 1 / 8f);
					isSetAxeWalk = true;
					isSetExtWalk = false;
					isSetAxeUse = false;
				}
			}
			currentWeapon.update(dt);
			if(currentWeapon instanceof Extinguisher){
				currentWeapon.setPosition(this.getPosition().x + 30 / PPM, this.getPosition().y + 60 / PPM);
			}else if(currentWeapon instanceof Axe){
				currentWeapon.setPosition(this.getPosition().x + 30 / PPM, this.getPosition().y - 60 / PPM);
			}
			if (currentWeapon.isUse()) {
				setVelocity(0, 0);
			}
		} else if (characterState.equals(CharacterState.YOUNG)) {

		}

	}

	public void use() {
		if (!characterState.equals(CharacterState.YOUNG)) {
			currentWeapon.use();
		}
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
			}
		} else if (characterState.equals(CharacterState.ADULT)) {
			currentWeapon.render(spriteBatch);
			if (velocity.x == 0 && velocity.y == 0 && !currentWeapon.isUse()) {
				animation.setCurrentFrame(0);
				spriteBatch.draw(animation.getFrame(), getPosition().x * PPM - width / 2,
						getPosition().y * PPM - height / 2, width, height);
			}
		}
		
		if (velocity.x > 0)
			spriteBatch.draw(animation.getFrame(), getPosition().x * PPM - width / 2,
					getPosition().y * PPM - height / 2, width / 2, height / 2, width, height, 1, 1, 0);
		else if (velocity.x < 0)
			spriteBatch.draw(animation.getFrame(), getPosition().x * PPM - width / 2,
					getPosition().y * PPM - height / 2, width / 2, height / 2, width, height, -1, 1, 0);
		else {
			spriteBatch.draw(animation.getFrame(), getPosition().x * PPM - width / 2,
					getPosition().y * PPM - height / 2, width / 2, height / 2, width, height, 1, 1, 0);
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
