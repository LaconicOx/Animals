package image_library;

import java.util.HashMap;

import game.Directions.Direction;
import view.SwingFacade;

public class SwingBush extends SwingTile{
	
	//Class Fields
	private static final String[] PATHS = {"Bush/Bush_Northeast/Bush_Northeast", 
											"Bush/Bush_North/Bush_North", 
											"Bush/Bush_Northwest/Bush_Northwest", 
											"Bush/Bush_Southwest/Bush_Southwest", 
											"Bush/Bush_South/Bush_South",
											"Bush/Bush_Southeast/Bush_Southeast"};
	private static final int BUSH_FRAMES = 62;
	private static final double[] BUSH_DIM = {311.0, 270.0};
	private static final HashMap<Direction, SwingAnimation> ANIMATIONS;
	static {
		ANIMATIONS = new HashMap<>();
		//keys must match PATHS in length and order.
		Direction[] keys = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
		for(int i = 0; i < keys.length; i++) {
			ANIMATIONS.put(keys[i], new SwingAnimation(PATHS[i], BUSH_FRAMES, BUSH_DIM));
		}
	}
	
	////////////////////////// Constructor /////////////////////////////////
	
	public SwingBush(SwingFacade sf, double[] coords, double[] modelDimensions){
		super(sf, coords, modelDimensions, ANIMATIONS);
	}

}
