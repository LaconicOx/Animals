package image_library;

import java.util.HashMap;

import view.SwingFacade;
import game.Directions.Direction;

public class SwingGrass extends SwingTile{
	
	//Class Fields
	private static final String[] PATHS = {"Grass/Grass_Northeast/Grass_Northeast", 
											"Grass/Grass_North/Grass_North", 
											"Grass/Grass_Northwest/Grass_Northwest", 
											"Grass/Grass_Southwest/Grass_Southwest", 
											"Grass/Grass_South/Grass_South",
											"Grass/Grass_Southeast/Grass_Southeast"};
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
