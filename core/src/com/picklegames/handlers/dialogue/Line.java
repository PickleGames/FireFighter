package com.picklegames.handlers.dialogue;

public class Line {
	
	private String[] wholeLine;
	private String[] letter;
	private String name;
	private int animationIndex;
	private int wait;
	private String text;
	
	
	public Line(String s){
		wholeLine = s.split(":");
		
		name = wholeLine[0];
		wait = Integer.parseInt(wholeLine[1]);
		animationIndex = Integer.parseInt(wholeLine[2]);
		text = wholeLine[3];
		letter = text.split("");
	
	}

	public String getName() {
		return name;
	}

	public int getWait() {
		return wait;
	}
	
	public String[] getLetter(){
		return letter;
	}

	public String[] getLine() {
		return wholeLine;
	}
	
	public int getAnimationIndex() {
		return animationIndex;
	}

}
