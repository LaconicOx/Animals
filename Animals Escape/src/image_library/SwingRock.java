package image_library;

import java.util.HashMap;

import game.Directions.Direction;
import view.SwingFacade;

public class SwingRock extends SwingTile{
	
	//Class Fields
	private static final String PATH = "Rock/Rock";
	private static final int ROCK_FRAMES = 1;
	private static final double[] ROCK_DIM = {311.0, 270.0};
	private static final HashMap<Direction, SwingAnimation> ANIMATIONS;
	static {
		ANIMATIONS = new HashMap<>();
		//keys must match PATHS in length and order.
		Direction[] keys = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
		SwingAnimation anim = new SwingAnimation(PATH, ROCK_FRAMES, ROCK_DIM);
		
		for(Direction key : keys) {
			ANIMATIONS.put(key, anim);
			}
	}
	
	public SwingRock(SwingFacade sf, double[] coords, double[] modelDimensions){
		super(sf, coords, modelDimensions, ANIMATIONS);
	}
 
}
