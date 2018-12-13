package model;


import game.Game;
import model.board.TotalBoard;
import model.screen.Screen;
import view.ViewInterface;

public class ModelFacade{
	
	//Game Components
	TotalBoard total;
	Screen active;
	Game game;
	ViewInterface view;
	
	////////////////////////////// Constructor //////////////////////////////////
	
	public ModelFacade(Game game, ViewInterface view){
		this.game = game;
		this.view = view;
		total = TotalBoard.getInstance(view);
		active = new Screen(view);
		
	}
	
	public void init() {
		active.init();//Temporary fix. See note in Screen.
	}
	
	///////////////////////// Accessor Methods ///////////////////////////
	
	/////////////////////////// Mutator Methods //////////////////////////////////
	
	public void primeAnimation() {
		active.pumpAnimation();
	}
	
	/**
	 * Responsible for shifting the screen to move the player and, if necessary
	 * creates new cells.
	 * @param x - represents the horizontal component of the player's movement
	 * @param y - represents the vertical component of the player's movement.
	 */
	public void movePlayer(double angle){
		active.movePlayer(angle);
	}
	
	public void update() {
		active.updateCharacters();
	}
	
	/////////////////////// Display Methods ////////////////////////////////////////
	public void displayTotal() {System.out.println(total);}
	
	public void displayActive() {System.out.println(active);}
	
}
