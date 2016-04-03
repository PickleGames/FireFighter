package com.picklegames.gameStates;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.picklegames.handlers.Line;

public class Dialogue {

	private BitmapFont font;
	private ArrayList<Line> dialogue;
	private Scanner diaScanner;

	private Line currentLine;
	private Line previousLine;

	private String top = "";
	private String bottom;

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

	public void update(float dt) {
		// TODO Auto-generated method stub

		
		//for(int i = 0; i <dialogue.size(); i++){
			currentLine = dialogue.get(ho);
			timeElap += dt;
			if(timeElap > .2f){
				if(pointer < currentLine.getLetter().length -1){
					pointer++;
				}else{
					top = "";
					pointer = 0;
					if(ho < dialogue.size() - 1){
						ho++;		
					}		
				}
				top += currentLine.getLetter()[pointer] + " ";
				System.out.println(currentLine.getLetter()[pointer]);
				timeElap = 0;
			}
			//printLine(dt);
		//}

	}
	int gh = 0;
//	public void printLine(float dt){
//		timeElap += dt;
//		//for(String s: currentLine.getText()){
//		if(timeElap > 1f){
//			top+= currentLine.getText()[gh++] + " ";
//			timeElap = 0;
//		}
//		
//		//}
//	}

	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		System.out.println(top);
		font.draw(batch, top, 100, 100);

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

}
