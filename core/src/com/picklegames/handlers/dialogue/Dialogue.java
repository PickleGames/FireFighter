package com.picklegames.handlers.dialogue;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Dialogue {

	private BitmapFont font;
	private ArrayList<Line> dialogue;
	private Scanner diaScanner;

	private Line currentLine;
	
	private String characterLine = "";
	private String name = "";



	private float timeElap = 0;

	public Dialogue(String filePath) {

		FileHandle file = Gdx.files.internal(filePath);
		diaScanner = new Scanner(file.readString());
		dialogue = new ArrayList<Line>();

		font = new BitmapFont();

		while (diaScanner.hasNextLine()) {
			String s = diaScanner.nextLine();
			dialogue.add(new Line(s));
		}

	}

	public void handleInput() {
		// TODO Auto-generated method stub

	}

	int pointer = 0;
	int dialogueIndex = 0;
	float delayBetween;
	float timeElapsed2;

	
	public boolean isFinished(){
		if(dialogueIndex == dialogue.size() - 1 && letterIndex == currentLine.getLetter().length)
			return true;
		return false;
	}
	
	public void update(float dt) {
		// TODO Auto-generated method stub
		// timeElap += dt;

		
		if(dialogueIndex < dialogue.size()){
			currentLine = dialogue.get(dialogueIndex);
			name = currentLine.getName();
			delayBetween = currentLine.getWait();
			if (dialogueIndex < dialogue.size()) {
				if (letterIndex >= currentLine.getLetter().length) {
					timeElapsed2 += dt;
					//System.out.println(timeElapsed2);
					if (timeElapsed2 >= delayBetween) {
						printLine(dt, currentLine);
						timeElapsed2 = 0;
					}
				} else {
					printLine(dt, currentLine);
				}
			}
		}
		
		System.out.println("dialogue Index: "+ dialogueIndex);
		System.out.println("letter Index: "+ letterIndex);
		System.out.println("dialogue size "+ dialogue.size());
		System.out.println("letter size "+ currentLine.getLetter().length);
	}

	int letterIndex = 0;
	float letterDelay = .03f;

	private void printLine(float dt, Line line) {
		timeElap += dt;
		
		if(letterIndex == line.getLetter().length){
			if (dialogueIndex < dialogue.size() - 1){
				dialogueIndex++;
				letterIndex = 0;
			}
			characterLine = "";
			return;
		}
		
		if (timeElap > letterDelay) {
			if (letterIndex < line.getLetter().length) {
				String currentLetter = line.getLetter()[letterIndex++];
				if(!isToken(currentLetter))
					characterLine += currentLetter;
					timeElap = 0;
			} 
		}

	}

	private boolean isToken(String line){
		if(line.equals("|")){
			return true;
		}return false;
	}
	
	public String getCharacterLine(){
		return characterLine;
	}
	
	public String getName(){
		return name;
	}

	public void dispose() {
		font.dispose();

	}

}
