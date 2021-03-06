package model.characters;

import java.util.HashMap;

import game.Directions.Direction;
import image_library.PlayerImage;
import model.nodes.Node;
import model.nodes.NodeFactory;
import model.screen.CurrentScreen;
import view.ViewInterface;

public class Player {
	
	//Class Fields
	private static final double SPEED = 0.01;//The distance, in model unit, the player moves in each update.
	private static final double[] DIM = {0.75, 1.5};//Player's dimensions in model units.
	private static final HashMap<Direction, double[]> UNIT_DIRECTION;//Unit vectors representing direction of movement.
	static {
		UNIT_DIRECTION = new HashMap<>();
		UNIT_DIRECTION.put(Direction.E, new double[] {Math.cos(0.0 * Math.PI/4.0), Math.sin(0.0 * Math.PI/4.0)});
		UNIT_DIRECTION.put(Direction.NE, new double[] {Math.cos(1.0 * Math.PI/4.0), Math.sin(1.0 * Math.PI/4.0)});
		UNIT_DIRECTION.put(Direction.N, new double[] {Math.cos(2.0 * Math.PI/4.0), Math.sin(2.0 * Math.PI/4.0)});
		UNIT_DIRECTION.put(Direction.NW, new double[] {Math.cos(3.0 * Math.PI/4.0), Math.sin(3.0 * Math.PI/4.0)});
		UNIT_DIRECTION.put(Direction.W, new double[] {Math.cos(4.0 * Math.PI/4.0), Math.sin(4.0 * Math.PI/4.0)});
		UNIT_DIRECTION.put(Direction.SW, new double[] {Math.cos(5.0 * Math.PI/4.0),Math.sin( 5.0 * Math.PI/4.0)});
		UNIT_DIRECTION.put(Direction.S, new double[] {Math.cos(6.0 * Math.PI/4.0), Math.sin(6.0 * Math.PI/4.0)});
		UNIT_DIRECTION.put(Direction.SE, new double[] {Math.cos(7.0 * Math.PI/4.0), Math.sin(7.0 * Math.PI/4.0)});
	}
	
	//Component Fields
	private ViewInterface view;
	private CurrentScreen screen;
	private Node node;
	
	private PlayerImage image;
	
	//State Fields
	private double[] center;//Character's center in model units.
	private Direction facing = Direction.E;
	private boolean moveFlag = false;
	
	public Player(CurrentScreen screen, ViewInterface view) {
		this.screen = screen;
		this.view = view;
		
		node = NodeFactory.getOrigin();
		this.center =  node.getCenter();
		image = view.getPlayer(center, DIM);
	}
	
	////////////////////// Mutators //////////////////////////////////
	
	public void move(double angle) {
		moveFlag = true;
		
		if (angle > 15.0 * Math.PI / 8.0)
			facing = Direction.E;
		else if (angle > 13.0 * Math.PI / 8.0)
			facing = Direction.SE;
		else if (angle > 11.0 * Math.PI / 8.0)
			facing = Direction.S;
		else if (angle > 9.0 * Math.PI / 8.0)
			facing = Direction.SW;
		else if (angle > 7.0 * Math.PI / 8.0)
			facing = Direction.W;
		else if (angle > 5.0 * Math.PI / 8.0)
			facing = Direction.NW;
		else if (angle > (3.0 * Math.PI / 8.0))
			facing = Direction.N;
		else if (angle > 1.0 * Math.PI / 8.0)
			facing = Direction.NE;
		else
			facing = Direction.E;
	}
	
	public void stop() {
		moveFlag = false;
	}
	
	private void processMove() {
		double[] unitVector = UNIT_DIRECTION.get(facing);
		double[] destination = {center[0] + SPEED * unitVector[0], center[1] - SPEED * unitVector[1]};
		
		//Gates on whether the destination is within the cell.
		if (node.checkPoint(destination)) {
			view.updateShift(destination);
			center = destination;
			image.advance(facing);
		}
		else {
			Direction dir = Direction.getDirection(node.getCenter(), destination);
			Node destNode = node.getNeighbor(dir);
			//Gates on whether the destination is passable.
			if (destNode.checkPassable()) {
				node = destNode;
				view.updateShift(destination);
				center = destination;
				screen.shift(dir);
				image.advance(facing);
			}
		}
	}
	
	private void processRest() {
		//Checks whether animation is in the resting position.
		//If not, advances the animation.
		if(!image.checkResting())
			image.advance(facing);
	}
	
	public void update() {
		if (moveFlag)
			processMove();
		else
			processRest();
		image.send(center);
	}
}
