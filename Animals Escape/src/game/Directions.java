package game;


/**
 * Represents the cardinal and primary intercardinal directions.
 */
public class Directions{
	
	public enum Direction{
		E, NE, N, NW, W, SW, S, SE;
		
		public static Direction getDirection(double[] start, double[] end) {
			/*
			 * This function uses trigonometry to calculate the player's direction
			 * relative to a cell's center.
			 * 
			 */
			
			double width = end[0] - start[0];
			//Must use the opposites of the height coordinates for the trigonometry.
			double height = (-1 * end[1]) - (-1 * start[1]); 
			
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
				case E: output = Direction.W;
					break;
				case NE: output = Direction.SW;
					break;
				case N: output = Direction.S;
					break;
				case NW: output = Direction.SE;
					break;
				case W: output = Direction.W;
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

}
