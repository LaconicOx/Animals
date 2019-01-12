package image_library;

import java.util.HashMap;

import game.Directions.Direction;
import view.SwingFacade;

public class SwingBorder extends SwingTile{
	//Class Fields
	private static final String[] PATHS = {"Border/Border"};
	private static final int BUSH_FRAMES = 1;
	private static final double[] BUSH_DIM = {311.0, 270.0};
	private static final HashMap<Direction, SwingAnimation> ANIMATIONS;
	static {
		ANIMATIONS = new HashMap<>();
		//keys must match PATHS in length and order.
		Direction[] keys = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
		for(int i = 0; i < keys.length; i++) {
				ANIMATIONS.put(keys[i], new SwingAnimation(PATHS[0], BUSH_FRAMES, BUSH_DIM));
		}
	}
		
		////////////////////////// Constructor /////////////////////////////////
		
		public SwingBorder(SwingFacade sf, double[] coords, double[] modelDimensions){
			super(sf, coords, modelDimensions, ANIMATIONS);
		}

}
