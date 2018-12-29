package image_library;

import java.util.HashMap;

import game.Directions.Direction;
import view.SwingFacade;

public class SwingTree extends SwingTile{
	
	//Class Fields
	private static final String[] PATHS = {"Tree/Tree_Northeast/Tree_Northeast", 
											"Tree/Tree_North/Tree_North", 
											"Tree/Tree_Northwest/Tree_Northwest", 
											"Tree/Tree_Southwest/Tree_Southwest", 
											"Tree/Tree_South/Tree_South",
											"Tree/Tree_Southeast/Tree_Southeast"};
	private static final int TREE_FRAMES = 62;
	private static final double[] TREE_DIM = {311.0, 270.0};
	private static final HashMap<Direction, SwingAnimation> ANIMATIONS;
	static {
		ANIMATIONS = new HashMap<>();
		//keys must match PATHS in length and order.
		Direction[] keys = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
		for(int i = 0; i < keys.length; i++) {
			ANIMATIONS.put(keys[i], new SwingAnimation(PATHS[i], TREE_FRAMES, TREE_DIM));
		}
	}
	
	///////////////////////////// Constructor /////////////////////////////////
	
	public SwingTree(SwingFacade sf, double[] coords, double[] modelDimensions){
		super(sf, coords, modelDimensions, ANIMATIONS);
	}

}
