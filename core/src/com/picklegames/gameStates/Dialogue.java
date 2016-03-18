package com.picklegames.gameStates;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.picklegames.managers.GameStateManager;

public class Dialogue extends GameState {

	private BitmapFont font;
	
	private Scanner diaScanner;
	private int scene = 1;
	
	private String name;
	private ArrayList<String> dialogue;
	private String line;
	
	private float timeElap;
	
	public Dialogue(GameStateManager gsm) throws FileNotFoundException{
		super(gsm);
		//diaScanner = new Scanner(setFile("dialogue.txt"));
		diaScanner = new Scanner("dialogue/dialogue.txt");
		setInputCursor(diaScanner);

	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		while(getScene() != getScene()+ 1){
			dialogue = readLine(diaScanner);
			line = dialogue.get(0);
			timeElap+=dt;
			if(timeElap>3){
				line += dialogue.get(+1);
			}
		
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		font.draw(spriteBatch, line, 100, 100);
	}

	//@param Scanner
	//post: sets input cursor to the end of scene identifier
	private void setInputCursor(Scanner input) {
		String sceneStart = "1";
		while (input.hasNextLine() && Integer.parseInt(sceneStart.substring(0, 1)) != getScene()) {
			sceneStart = input.nextLine();
		}
	}
	
	//@param Scanner
	//@return returns an ArrayList of tokens in line
	//pre: Scanner input cursor remains in current scene
	//post: input cursor moved to next line, name is set 
	private ArrayList<String> readLine(Scanner input){
		String line;
		ArrayList<String> dialogue = new ArrayList<String>();
		Scanner lineScan;
		if(input.hasNextLine()){
			line = input.nextLine();
			Scanner tokenScan = new Scanner(line);
			setName(tokenScan.next());
			while(tokenScan.hasNext()){
				String cToken = tokenScan.next();
				dialogue.add(cToken + " ");
			}
		}
		
		return dialogue;
	}
	
	private File setFile(String fileName) {

		return new File("dialogue/" + fileName);
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
