package model;


import model.characters.CharacterFactory;
import model.characters.Player;
import model.nodes.NodeFactory;
import model.screen.Screen;
import view.ViewInterface;

public class ModelFacade{
	
	//Game Components
	private NodeFactory total;
	//private Screen active;
	private Screen active;
	private ViewInterface view;
	private Player player;
	private CharacterFactory factory;
	
	////////////////////////////// Constructor //////////////////////////////////
	
	public ModelFacade(ViewInterface view){
		this.view = view;
		total = NodeFactory.getInstance(view);
		active = new Screen(view);
		
		factory = CharacterFactory.getInstance(active, this, view);
		player = new Player(active, view);
		
		
	}
	
	///////////////////////// Accessor Methods ///////////////////////////
	
	
	/////////////////////////// Mutator Methods //////////////////////////////////
	
	
	/**
	 * Responsible for shifting the screen to move the player and, if necessary
	 * creates new cells.
	 * @param x - represents the horizontal component of the player's movement
	 * @param y - represents the vertical component of the player's movement.
	 */
	public void movePlayer(double angle){
		player.move(angle);
	}
	
	public void screenDilate(double factor) {
		active.dilate(factor);
	}
	
	public void stopPlayer() {
		player.stop();
	}
	
	public void update() {
		player.update();
		active.update();
	}
	
	/////////////////////// Display Methods ////////////////////////////////////////
	public void displayTotal() {System.out.println(total);}
	
	public void displayActive() {System.out.println(active);}
	
}
