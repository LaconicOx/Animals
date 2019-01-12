package model.screen;

import game.Directions.Direction;
import model.ModelKey;
import model.board.Node;
import model.board.TotalBoard;
import model.board.node_states.NodeState;
import model.board.node_states.On;
import view.ViewInterface;

public class ScreenStart extends ScreenState{
	
	//Class Fields
	private static final double EXTENSION = 0.5;//Extends cell beyond edge of screen to prevent flickering
	private static final Direction[] CELL_DIRECTIONS = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
	
	//Instance Fields
	private final ViewInterface view;
	
	public ScreenStart(Screen screen, ViewInterface view) {
		super(screen);
		this.view = view;
	}
	
	////////////////////////// Accessors ////////////////////////////////////
	
	private double[] getModelBoundaries(double[] center) {
		/*
		 * boundaries[0] represents the left boundary.
		 * boundaries[1] represents the right boundary.
		 * boundaries[2] represents the top boundary.
		 * boundaries[3] represents the bottom boundary.
		 */
		
		//Converts screen dimensions form pixels to model units.
		double[] screenDim = view.getScreenDim();//Screen dimensions in pixels
		double scale = view.getScale();//number of pixels per model unit
		double[] dim = new double[] {screenDim[0] / scale, screenDim[1] / scale};//Dimensions in model units
		double[] vector = ModelKey.getDimensions();//Vector representing dimensions of a hexagon.
		
		double[] boundaries = new double[4];
		
		//Distance from the center to the top or bottom boundaries of the panel.
		double vertical = dim[0] / 2.0 + vector[0] * EXTENSION;
		//Distance from the center to the left of right boundaries of the panel.
		double horizontal = dim[1] / 2.0 + vector[1] * EXTENSION;
		
		boundaries[0] = center[0] - horizontal;
		boundaries[1] = center[0] + horizontal;
		boundaries[2] = center[1] - vertical;
		boundaries[3] = center[1] + vertical;
		
		return boundaries;	
	}
	
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
		
		//Iterates through neighbors
		for (Direction dir : CELL_DIRECTIONS) {
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
		double[] boundaries = getModelBoundaries(new double[] {0.0, 0.0});
		Node center = TotalBoard.getNode(new ModelKey(new int[] {0,0}), false);
		
		initScreen(center, boundaries);//Initializes screen.
		
		
		// Sets state to normal.
		screen.setState(screen.getStateNormal());
	}


}
