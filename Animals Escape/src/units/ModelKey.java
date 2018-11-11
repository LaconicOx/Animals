package units;

public class ModelKey extends GameKey{
	
	private static final int EXTENSION = 1;// Constant for extending the cells beyond the edge of the screen to create smooth animation.
	private static final double UNIT_APOTHEM = 1.0;
	private static final double UNIT_RADIUS = (2 * Math.sqrt(3)) / 3.0;
	private static final double[] MODEL_VECTOR = {1.5 * UNIT_RADIUS, UNIT_APOTHEM};
	
	public ModelKey() {
		super();
	}
	
	private double pixelsToUnits(double pixels) {
		return pixels / getRatio();
	}
	
	///////////////////////////// Accessor Methods ////////////////////////////////////
	
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
