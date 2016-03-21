package com.picklegames.gameStates;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dialogue{

	private BitmapFont font;

	private FileHandle file;
	private Scanner diaScanner;
	private int scene = 1;

	private String name;
	private ArrayList<String> dialogue;
	private String line = "";
	private FileHandle fileHandle;
	private float timeElap;

	public Dialogue() throws FileNotFoundException {
		// diaScanner = new Scanner(setFile("assets/dialogue/dialogue.txt"));
		// diaScanner = new Scanner(new File("assets/dialogue/dialogue.txt"));
		fileHandle = Gdx.files.internal("dialogue/dialogue.txt");
		diaScanner = new Scanner(fileHandle.readString());
//		while(diaScanner.hasNext()){
//			System.out.println(diaScanner.next());
//		}
		setInputCursor(diaScanner);
		dialogue = new ArrayList<String>();
		font = new BitmapFont();

	}

	public void handleInput() {
		// TODO Auto-generated method stub

	}
	int i = 0;
	float delay = .1f;
	public void update(float dt) {
		// TODO Auto-generated method stub
		timeElap += dt;
		
		//while (getScene() != getScene() + 1) {
//			for(String s: ){
//				
//			}
			dialogue = readLine(diaScanner);
			//if(i == 0) line = dialogue.get(i);

			if (timeElap > delay && !dialogue.isEmpty() && dialogue != null) {
				line += dialogue.get(i++);
				timeElap = 0;
			}

		//}
	}

	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		System.out.println(line);
		font.draw(batch, line, 100, 100);

	}

	// @param Scanner
	// post: sets input cursor to the end of scene identifier
	private void setInputCursor(Scanner input) {
		String sceneStart = "";
		while (input.hasNextLine()) {
			sceneStart = input.nextLine();
			if (sceneStart.length() < 3 && Integer.parseInt(sceneStart) == getScene()) {
				return;
			}
		}
	}

	// @param Scanner
	// @return returns an ArrayList of tokens in line
	// pre: Scanner input cursor remains in current scene
	// post: input cursor moved to next line, name is set
	private ArrayList<String> readLine(Scanner input) {
		String line;
		//ArrayList<String> dialogue = new ArrayList<String>();
		if (input.hasNextLine()) {
			line = input.nextLine();
			if(line.length() < 3){
				return new ArrayList<String>();
			}
			Scanner tokenScan = new Scanner(line);
			setName(tokenScan.next());
			while (tokenScan.hasNext()) {
				String cToken = tokenScan.next();
				dialogue.add(cToken + " ");
			}
			tokenScan.close();
		}

		return dialogue;
	}

	private String setFile(String fileName) {

		file = Gdx.files.internal(fileName);
		String s = file.readString();
		return s;
	}

	private int getScene() {
		return scene;
	}

	private void setScene(int i) {
		scene = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

}
