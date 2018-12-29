package image_library;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import view.SwingFacade;
import game.Directions.Direction;

public class SwingGrass extends SwingTile{
	
	//Class Fields
	private static final String[] PATHS = {"Bush/Bush_North/Bush_Northeast", 
											"Bush/Bush_North/Bush_North", 
											"Bush/Bush_North/Bush_Northwest", 
											"Bush/Bush_North/Bush_Southwest", 
											"Bush/Bush_North/Bush_South",
											"Bush/Bush_North/Bush_Southeast"};
	private static final int GRASS_FRAMES = 62;
	private static final double[] GRASS_DIM = {311.0, 270.0};
	private static final HashMap<Direction, SwingAnimation> ANIMATIONS;
	static {
		ANIMATIONS = new HashMap<>();
		//keys must match PATHS in length and order.
		Direction[] keys = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
		for(int i = 0; i < keys.length; i++) {
			ANIMATIONS.put(keys[i], new SwingAnimation(PATHS[i], GRASS_FRAMES, GRASS_DIM));
		}
	}
	
	
	//////////////////////// Constructor /////////////////////////////////
	
	public SwingGrass(SwingFacade sf, double[] coords, double[] modelDimensions){
		super(sf, coords, modelDimensions, ANIMATIONS);
	}

}
