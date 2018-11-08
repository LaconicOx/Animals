package model;

import game.GameParameters;


/**
 * The model relies on the apothem of the hex tiles as its base unit.
 * Whatever the apothem of any hex tile drawn to the screen is equivalent
 * to one.
 *
 */
public class ModelParameters extends GameParameters{
	
	//Constants governing model units
	private static final double UNIT_APOTHEM = 1.0;
	private static final double UNIT_RADIUS = (2 * Math.sqrt(3)) / 3.0;
	private static final int EXTENSION = 1;// Constant for extending the cells beyond the edge of the screen to create smooth animation.
	//Constant for converting node values to model values.
	private static final double[] MODEL_VECTOR = {1.5 * UNIT_RADIUS, UNIT_APOTHEM};
	
	//Constants governing character movement.
	private static final double SPEED = 1.0;
	
	private static ModelParameters uniqueInstance = null;
	
	////////////////////////////// Constructor ////////////////////////////////
	
	private ModelParameters(){
		super();
	}
	
	public static ModelParameters getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ModelParameters();
		return uniqueInstance;
	}
	
	///////////////////////////// Accessor Methods ////////////////////////////////
	
	public double getUnitRadius() {return UNIT_RADIUS;}
	
	public double getUnitApothem() { return UNIT_APOTHEM; }
	
	public double getSpeed() { return SPEED; }
	
	public double getExtension() { return getTileRadius()* 2 * EXTENSION; };
	
	public double[] getUnitBoundaries(int[] center) {
		
		/*
		 * boundaries[0] represents the left boundary.
		 * boundaries[1] represents the right boundary.
		 * boundaries[2] represents the top boundary.
		 * boundaries[3] represents the bottom boundary.
		 */
		
		double[] boundaries = new double[4];
		
		//Distance from the center to the top or bottom boundaries of the panel.
		double vertical = pixelToModel(getPanelHeight()) / 2.0;
		//Distance from the center to the left of right boundaries of the panel.
		double horizontal = pixelToModel(getPanelWidth()) / 2.0;
		
		boundaries[0] = center[0] - horizontal;
		boundaries[1] = center[0] + horizontal;
		boundaries[2] = center[1] - vertical;
		boundaries[3] = center[1] + vertical;
		
		return boundaries;		
	}
	
	//////////////////////// Conversion Methods /////////////////////////
	
	/**
	 * 
	 * @param - pixel units from the screen
	 * @return - model units based on the apothem.
	 */
	private double pixelToModel(double pixels) {
		return pixels/getTileApothem();
	}
	
	public double[] nodeToModel(int[] key) {
		return new double[] {MODEL_VECTOR[0] * key[0], MODEL_VECTOR[1] * key[1]};
	}
	
	////////////////////// Enum ////////////////////////////////
	
	public enum Direction{
		NE (1, -1),
		N (0, -2),
		NW(-1, -1),
		SW(-1, 1),
		S(0, 2),
		SE(1, 1);
		
		private int x;
		private int y;
		
		Direction(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		
		public int[] getNeighborKey(int[] key) {
			return new int[]{key[0] + this.x, key[1] + this.y};
		}
		
		public static Direction getDirection(double[] start, double[] end) {
			/*
			 * This function uses trigonometry to calculate the player's direction
			 * relative to a cell's center.
			 * 
			 */
			double width = end[0] - start[0];
			double height = end[1] - start[1];
			
			//Uses trig to find the angle.
			double hypot = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
			double angle;
			if (height > 0)
				if ( width != 0)//Protects against division by zero.
					angle = Math.acos(width / hypot);
				else 
					angle = Math.PI/2;
			else
				if (width != 0)
					angle = 2*Math.PI - Math.acos(width / hypot);
				else
					angle = 3*Math.PI/2;
			

			//Calculates the side of the hexagon by dividing by 60 degrees in radians and rounding up.
			//The sides, counting counter-clockwise, start at 1 for northeast to 6 for southeast.
			int step = (int)Math.ceil(angle/(Math.PI/3));
			Direction dir = null;
			switch(step) {
				case 1: dir = Direction.NE;
					break;
				case 2: dir = Direction.N;
					break;
				case 3: dir = Direction.NW;
					break;
				case 4: dir = Direction.SW;
					break;
				case 5: dir = Direction.S;
					break;
				case 6: dir = Direction.SE;
					break;
			}
			return dir;
		}
		
		public static Direction getOpposite(Direction dir) {
			Direction output = null;
			switch(dir) {
				
				case NE: output = Direction.SW;
					break;
				case N: output = Direction.S;
					break;
				case NW: output = Direction.SE;
					break;
				case SW: output = Direction.NE;
					break;
				case S: output = Direction.N;
					break;
				case SE: output = Direction.NW;
					break;
			}
			return output;
		}
	}//End of Direction


}//End of ModelParameters
