package model.screen;

import game.Directions.Direction;
import model.nodes.Node;
import model.nodes.NodeFactory;
import view.ViewInterface;

public class ScreenStart extends ScreenState{
	
	//Instance Fields
	private final ViewInterface view;
	
	public ScreenStart(Screen screen, ViewInterface view) {
		super(screen);
		this.view = view;
	}
	
	////////////////////////// Accessors ////////////////////////////////////
	
	/////////////////////////// Mutators ////////////////////////////////////
	
	/**
	 * Builds the screen recursively by traversing the node trees.
	 * All cells are set to active. initBorder must be initialize their state.
	 * @param n - node for the new cell to be created and added to screen.
	 * @param boundaries - inclusive boundaries for the screen.
	 */
	private void initScreen(Node node, double[] boundaries) {
		//Adds active node to screen.
		screen.add(node);
		Direction[] directions = Direction.getNodeDirections();
		
		//Iterates through neighbors
		for (Direction dir : directions) {
			Node candidate = node.getNeighbor(dir);
			//Checks if node has not been turned on.
			if(candidate.checkOff()) {
				double[] cen = candidate.getCenter();
				candidate.setInterior();
				//If node is within boundaries, node is turned on and recursion continues.
				if ( cen[0] >= boundaries[0] && cen[0] <= boundaries[1] && cen[1] >= boundaries[2] && cen[1] <= boundaries[3]) {
					initScreen(candidate, boundaries);
				}
				//Otherwise, node is set as border and added to the screen.
				else {
					candidate.setBorder();
					screen.add(candidate);
				}
			}
		}
	}
	
	@Override
	public void update() {
		double[] boundaries = getModelBoundaries(view.getShift(), view.getScreenDim(), view.getScale());
		Node origin = NodeFactory.getOrigin();
		origin.setInterior();
		initScreen(origin, boundaries);//Initializes screen.
		
		
		// Sets state to normal.
		screen.setState(screen.getStateNormal());
	}


}
