package game;

import model.ModelFacade;
import view.SwingFacade;
import view.ViewInterface;

public class Game {
	
	//Game Components
	GameLoop loop;
	ViewInterface view;
	ModelFacade model;
	
	/////////////////////////////// Constructor ///////////////////////////////////
	public Game(String mode) {
		
		if (mode.equals("swing")) {
			view = new SwingFacade(this);
		}
		else {
			System.err.println("Unrecognized mode");
			System.exit(0);
		}
		
		model = new ModelFacade(this, view);
		model.init();//TODO: Temporary fix to circular call.
		loop = new GameLoop(this, view, model);
	}
	
	////////////////////////////// Mutator Methods //////////////////////////////
	
	public void updatePlayer(double angle) {
		
	}

	
	////////////////////////////// Main Function //////////////////////////////////
	public static void main(String[] args) {
		String mode = "swing";
		new Game(mode);
	}
}
