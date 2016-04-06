package com.picklegames.handlers;

import static com.picklegames.handlers.B2DVars.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public final class CreateBox2D {
	
	public static Body createBodyLoader(World world, float x, float y, float width, float height, String userData, BodyEditorLoader bel, String name){
		BodyDef bdef = new BodyDef();
		bdef.position.set(x / PPM, y / PPM);
		bdef.type = BodyType.DynamicBody;

		// create body from the body definition
		Body body = world.createBody(bdef);
		
		// create fixture definition
		FixtureDef fdef = new FixtureDef();
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		
		// attach polygon
		bel.attachFixture(body, name, fdef, 1);

		// create player collision box fixture
		body.getFixtureList().get(0).setUserData(userData);


		// final tweaks, manually set the player body mass to 1 kg
		MassData md = body.getMassData();
		md.mass = 1;
		body.setMassData(md);
		
		return body;
	}
	
	public static Body createBox(World world, float x, float y, float width, float height, Vector2 center, String userData){

		// create body definition
		BodyDef bdef = new BodyDef();
		bdef.position.set(x / PPM, y / PPM);
		bdef.type = BodyType.DynamicBody;

		// create body from the body definition
		Body body = world.createBody(bdef);

		// create box shape for player collision
		PolygonShape shape = new PolygonShape();
		//shape.set(new float[]{0,,1,1});
		shape.setAsBox(width / PPM, height / PPM, new Vector2(center.x / PPM, center.y / PPM), 0);

		// create fixture definition
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;

		// create player collision box fixture
		body.createFixture(fdef).setUserData(userData);
		shape.dispose();

		// final tweaks, manually set the player body mass to 1 kg
		MassData md = body.getMassData();
		md.mass = 1;
		body.setMassData(md);

		return body;
	}
	
	public static Body createCircle(World world, float x, float y, float radius, boolean isSensor, BodyType bodyType  ,String userData){

		// create body definition
		BodyDef bdef = new BodyDef();
		bdef.position.set(x / PPM, y / PPM);
		bdef.type = bodyType;

		// create body from the body definition
		Body body = world.createBody(bdef);

		// create box shape for player collision
		CircleShape shape = new CircleShape();
		shape.setRadius(radius / PPM);
		
		
		
		// create fixture definition
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = isSensor;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;

		// create player collision box fixture
		body.createFixture(fdef).setUserData(userData);
		shape.dispose();

		// final tweaks, manually set the player body mass to 1 kg
		MassData md = body.getMassData();
		md.mass = 1;
		body.setMassData(md);

		return body;
	}
	
	public static Body createPolygon(World world, float x, float y, float[] vertices, String userData){

		// create body definition
		BodyDef bdef = new BodyDef();
		bdef.position.set(x / PPM, y / PPM);
		bdef.type = BodyType.DynamicBody;

		// create body from the body definition
		Body body = world.createBody(bdef);

		// create box shape for player collision
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		
		// create fixture definition
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;

		// create player collision box fixture
		body.createFixture(fdef).setUserData(userData);
		shape.dispose();

		// final tweaks, manually set the player body mass to 1 kg
		MassData md = body.getMassData();
		md.mass = 1;
		body.setMassData(md);

		return body;
	}
}
