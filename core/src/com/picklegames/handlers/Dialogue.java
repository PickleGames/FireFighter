package com.picklegames.handlers;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dialogue {

	private BitmapFont font;
	private ArrayList<Line> dialogue;
	private Scanner diaScanner;

	private Line currentLine;

	private String top = "";
	private String bottom = "";

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
	int ho = 0;
	float delayBetween;
	float timeElapsed2;

	public void update(float dt) {
		// TODO Auto-generated method stub
		// timeElap += dt;

		if(ho < dialogue.size()){
			currentLine = dialogue.get(ho);
			delayBetween = currentLine.getWait();
			if (ho < dialogue.size()) {
				if (letterIndex >= currentLine.getLetter().length) {
					timeElapsed2 += dt;
					System.out.println(timeElapsed2);
					if (timeElapsed2 >= delayBetween) {
						printLine(dt, currentLine);
						timeElapsed2 = 0;
					}
				} else {
					printLine(dt, currentLine);
				}
			}
		}
		
	}

	int letterIndex = 0;
	float letterDelay = .2f;

	private void printLine(float dt, Line line) {
		timeElap += dt;
		
		if(letterIndex == line.getLetter().length){
			if (ho < dialogue.size())ho++;
			letterIndex = 0;
			if (line.getName().equals("MOM")) {
				bottom = "";
			} else if (line.getName().equals("YOU")) {
				top = "";
			}
			return;
		}
		
		if (timeElap > letterDelay) {
			if (letterIndex < line.getLetter().length) {
				String current = line.getLetter()[letterIndex++];
				if (line.getName().equals("MOM")) {
					if(!isToken(current))
						top += current + " ";
					timeElap = 0;
				} else if (line.getName().equals("YOU")) {
					if(!isToken(current))
						bottom += current + " ";
					timeElap = 0;
				}
			} else {
//				ho++;
//				letterIndex = 0;

			}
		}

	}

	private boolean isToken(String line){
		if(line.equals("<.>")){
			return true;
		}return false;
	}
	
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		System.out.println("top: " + top);
		System.out.println("bototm : " + bottom);
		font.draw(batch, "MOM: " + top, 100, 400);
		font.draw(batch, "YOU: " + bottom, 100, 100);
	}

	public void dispose() {
		font.dispose();

	}

}
