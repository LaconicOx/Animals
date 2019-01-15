package model.screen;

import game.Directions.Direction;
import model.nodes.ModelKey;
import model.nodes.Node;
import model.nodes.NodeState;
import model.nodes.On;
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
		NodeState active = node.getOn();
		node.initState(active);
		screen.add(node);
		Direction[] directions = Direction.getNodeDirections();
		
		//Iterates through neighbors
		for (Direction dir : directions) {
			Node candidate = node.getNeighbor(dir);
			if(!screen.checkNode(candidate)) {
				double[] cen = candidate.getCenter();
				//Calls itself to add candidate node to screen if within boundaries;
				//otherwise, does nothing.
				if ( cen[0] >= boundaries[0] && cen[0] <= boundaries[1] && cen[1] >= boundaries[2] && cen[1] <= boundaries[3])
					initScreen(candidate, boundaries);
				else {
					On border = candidate.getOn();
					border.initBorder();
					candidate.initState(border);
					screen.add(candidate);
				}
			}
		}
	}
	
	@Override
	public void update() {
		double[] boundaries = getModelBoundaries(view.getShift(), view.getScreenDim(), view.getScale());
		Node center = NodeFactory.getNode(new ModelKey(new int[] {0,0}), false);
		
		initScreen(center, boundaries);//Initializes screen.
		
		
		// Sets state to normal.
		screen.setState(screen.getStateNormal());
	}


}
