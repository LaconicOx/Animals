package units;

/**
 *    This class encapsulates all parameters for the model units. Model units are constant double values handling locations of objects.
 * Model units have two important features. Firstly, they are independent of the screen coordinates. This is necessary to avoid problems 
 * associated with the zoom features. Secondly, they represent the location of objects relative to where the player starts the game, which is 
 * represented as (0.0, 0.0). This makes the calculations for creating and destroying hexes much easier.
 *    The major complication of model units is relying on double primitives. They cannot be avoided because drawing hexagons creates
 * unseemly overloads because of rounding. The cost is that trig functions often round different relative to the location. This made testing whether
 * sets contain cells difficult. This is part area where the program spends much of it time, so I did not want to add functions to test estimates.
 * I introduced node units to solve this problem. As integer values, they can be quickly tested, and the Model_Vector constant quickly converts them.
 *
 */
public class ModelKey extends GameKey{
	
	private static final int EXTENSION = 1;// Constant for extending the cells beyond the edge of the screen to prevent flickering on the edges.
	private static final double UNIT_APOTHEM = 1.0;
	private static final double UNIT_RADIUS = (2 * Math.sqrt(3)) / 3.0;
	private static final double[] MODEL_VECTOR = {1.5 * UNIT_RADIUS, UNIT_APOTHEM};//Vector for converting node units to model units.
	
	private int update_counter;//update counter used for animation.
	
	public ModelKey() {
		super();
	}
	
	private double pixelsToUnits(double pixels) {
		return pixels / getRatio();
	}
	
	///////////////////////////// Mutator Methods ///////////////////////////////////
	
	public void stepCount() {
		update_counter++;
	}
	
	protected void resetCount() {
		update_counter = 0;
	}
	
	///////////////////////////// Accessor Methods ////////////////////////////////////
	
	public int getCount() {
		return update_counter;
	}
	
	protected double[] getVector() {
		return MODEL_VECTOR;
	}
	
	protected double getRadius() {
		return UNIT_RADIUS;
	}
	
	protected double getApothem() {
		return UNIT_APOTHEM;
	}
	
	private double[] getModelDim() {
		double[] dim = getScreenDim();
		return new double[] {pixelsToUnits(dim[0]), pixelsToUnits(dim[1])};
	}
	
	public double[] getModelBoundaries(double[] center) {
		/*
		 * boundaries[0] represents the left boundary.
		 * boundaries[1] represents the right boundary.
		 * boundaries[2] represents the top boundary.
		 * boundaries[3] represents the bottom boundary.
		 */
		
		double[] boundaries = new double[4];
		double[] dim = getModelDim();
		
		//Distance from the center to the top or bottom boundaries of the panel.
		double vertical = dim[0] / 2.0 + MODEL_VECTOR[0] * EXTENSION;
		//Distance from the center to the left of right boundaries of the panel.
		double horizontal = dim[1] / 2.0 + MODEL_VECTOR[1] * EXTENSION;
		
		boundaries[0] = center[0] - horizontal;
		boundaries[1] = center[0] + horizontal;
		boundaries[2] = center[1] - vertical;
		boundaries[3] = center[1] + vertical;
		
		return boundaries;	
	}
	
}
