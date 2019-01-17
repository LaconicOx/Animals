package model.characters;


import game.Directions.Direction;
import model.nodes.ConcreteNode;

public abstract class Characters {
	
	protected ConcreteNode node;//cell containing the character.
	protected Direction facing;//the direction the animation should be facing/moving.
	protected double[] center;//Character's center in model units.
	protected double[]  moveVector;//unit vector for movement.
	
	public Characters(ConcreteNode node) {
		this.node = node;
		this.center = node.getCenter();
	}
	
	//////////////////// Mutator Methods ////////////////////////
	
	
	public abstract void update();
	
	//////////////////// Checker Methods ////////////////////////
	
	
	
	public boolean offScreen(ConcreteNode test) {
		if (test.equals(node))
			return true;
		else
			return false;
	}
	
	
}
