package game;

import java.util.Iterator;

/**
 * Represents the cardinal and primary inter-cardinal directions.
 */
public class Directions{
	
	//The vertical components must be reflected to match screen coordinates.
	public enum Direction{
		E(new double[]{1.0, 0.0}), 
		NE(new double[]{Math.cos(Math.PI / 4.0), -1 * Math.sin(Math.PI / 4.0)}), 
		N(new double[]{0.0, -1.0}),
		NW(new double[]{Math.cos(3 * Math.PI / 4.0), -1 * Math.sin( 3 * Math.PI / 4.0)}),
		W(new double[]{-1.0, 0.0}), 
		SW(new double[]{Math.cos(5 * Math.PI / 4.0), -1 * Math.sin( 5 * Math.PI / 4.0)}), 
		S(new double[]{0.0, 1.0}), 
		SE(new double[]{Math.cos(7 * Math.PI / 4.0), -1 * Math.sin( 7 * Math.PI / 4.0)});
		
		private double[] vector;
		
		private Direction(double[] vector) {
			this.vector = vector;
		}
		
		public static Iterator<Direction> getNodeIterator(){
			return new NodeDirections();
		}
		
		public static Direction[] getNodeDirections() {
			return new Direction[] {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
		}
		
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
				case W: output = Direction.E;
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
	
		public double[] scaledVector(double scalar) {
			return new double[] {scalar * vector[0], scalar * vector[1]};
		}
		
		/////////////////////// Inner Class //////////////////////////
		
		private static class NodeDirections implements Iterator<Direction>{
			
			private static final Direction[] NODE_DIR = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
			private int next;
			
			NodeDirections(){
				next = 0;
			}
			
			@Override
			public boolean hasNext() {
				if(next < 6)
					return true;
				else
					return false;
			}

			@Override
			public Direction next() {
				Direction output = NODE_DIR[next];
				next++;
				return output;
			}
			
		}//End of NodeDirections
	}//End of Direction

}
