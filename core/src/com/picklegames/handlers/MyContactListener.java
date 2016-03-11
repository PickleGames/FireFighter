package com.picklegames.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
	
	private boolean contactMade = false;
	
	public MyContactListener(){
		super();
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if(fa.getUserData() != null && fa.getUserData().equals("ai")){
			contactMade = true;
		}
		if(fb.getUserData() != null  && fb.getUserData().equals("ai")){
			contactMade = true;
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if(fa.getUserData() != null && fa.getUserData().equals("ai")){
			contactMade = false;
		}
		if(fb.getUserData() != null  && fb.getUserData().equals("ai")){
			contactMade = false;
		}
	}
	
	public boolean isContactMade(){
		return contactMade;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
