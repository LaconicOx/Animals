package model;

import commands.swing.CommandFactory;
import game.Game;
import model.board.TotalBoard;
import model.screen.Screen;

public class ModelFacade{
	
	//Game Components
	TotalBoard total;
	Screen active;
	Game game;
	ModelParameters parameters;
	
	////////////////////////////// Constructor //////////////////////////////////
	
	public ModelFacade(Game game){
		this.game = game;
		parameters = ModelParameters.getInstance();
		total = TotalBoard.getInstance();
		active = Screen.getInstance();
		active.init();//Temporary fix. See note in Screen.
	}
	
	
	///////////////////////// Accessor Methods ///////////////////////////
	
	public void getDrawCommands() {
		active.getTileCommands();
		CommandFactory.getPingView();
	}
	/////////////////////////// Mutator Methods //////////////////////////////////
	
	public void updateCharacters() {
		
	}
	
	/**
	 * Responsible for shifting the screen to move the player and, if necessary
	 * creates new cells.
	 * @param x - represents the horizontal component of the player's movement
	 * @param y - represents the vertical component of the player's movement.
	 */
	public void movePlayer(double angle){
		active.movePlayer(angle);
		CommandFactory.getPingView();
	}
	
	/////////////////////// Display Methods ////////////////////////////////////////
	public void displayTotal() {System.out.println(total);}
	
	public void displayActive() {System.out.println(active);}
	
}
