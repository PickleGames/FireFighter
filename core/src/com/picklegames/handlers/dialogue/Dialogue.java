package com.picklegames.handlers.dialogue;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.picklegames.game.FireFighterGame;

public class Dialogue {

	private BitmapFont font;
	private GlyphLayout layout;
	private float fontScaleDecrease = -.10f;

	private ArrayList<Line> dialogue;
	private Scanner diaScanner;

	private Line currentLine;


	private int animationIndex;
	private String date = "";
	private String characterLine = "";
	private String name = "";

	private float timeElap = 0;
	
	public Dialogue(String filePath, String date) {

		FileHandle file = Gdx.files.internal(filePath);
		diaScanner = new Scanner(file.readString());
		dialogue = new ArrayList<Line>();
		
		setDate(date);
		
		font = new BitmapFont(Gdx.files.internal("font/comicsan.fnt"));
		font.getData().scaleX = 15;
		font.getData().scaleY = 15;
		layout = new GlyphLayout();
		
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
	
	public boolean isFinished() {
		if (dialogueIndex == dialogue.size() - 1 && letterIndex == currentLine.getLetter().length)
			return true;
		return false;
	}

	public void update(float dt, Sound s) {
		// TODO Auto-generated method stub
		// timeElap += dt;
		
		font.getData().scaleX +=fontScaleDecrease;
		font.getData().scaleY +=fontScaleDecrease;
		
		if (isIntroDone()) {
			currentLine = dialogue.get(dialogueIndex);
			name = currentLine.getName();
			animationIndex = currentLine.getAnimationIndex();
			delayBetween = currentLine.getWait();
			if (dialogueIndex < dialogue.size()) {
				if (letterIndex >= currentLine.getLetter().length) {
					timeElapsed2 += dt;
					if (timeElapsed2 >= delayBetween) {
						printLine(dt, currentLine, s);
						timeElapsed2 = 0;
					}
				} else {
					printLine(dt, currentLine, s);
				}
		}

//		System.out.println("dialogue Index: " + dialogueIndex);
//		System.out.println("letter Index: " + letterIndex);
//		System.out.println("dialogue size " + dialogue.size());
//		System.out.println("letter size " + currentLine.getLetter().length);
	}
}
	int letterIndex = 0;
	float letterDelay = .03f;

	private void printLine(float dt, Line line, Sound s) {
		timeElap += dt;

		if (letterIndex == line.getLetter().length) {
			if (dialogueIndex < dialogue.size() - 1) {
				dialogueIndex++;
				letterIndex = 0;
			}
			characterLine = "";
			return;
		}

		if (timeElap > letterDelay) {
			if (letterIndex < line.getLetter().length) {
				String currentLetter = line.getLetter()[letterIndex++];
				if (!isToken(currentLetter))
					characterLine += currentLetter;
					if(currentLetter.equals(" "))
						s.play();
					timeElap = 0;
			} 
		}

	}
	public void render(SpriteBatch batch) {
		layout.setText(font, date);
		float width = layout.width;// contains the width of the current set text
		float height = layout.height;

		batch.begin();
		if(!isIntroDone())
		 font.draw(batch, getDate(), FireFighterGame.V_WIDTH / 2 - width / 2, FireFighterGame.V_HEIGHT / 2 - height / 2);

		batch.end();
	}

	private boolean isToken(String line) {
		if (line.equals("|")) {
			return true;
		}
		return false;
	}

	public void intro() {

	}

	public boolean isIntroDone() {
		return (font.getScaleX() < .2f);
	}

	public Line getCurrentLine() {
		return currentLine;
	}
	
	public String getCharacterLine() {
		return characterLine;
	}

	public String getName() {
		return name;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void dispose() {
		font.dispose();

	}

}
