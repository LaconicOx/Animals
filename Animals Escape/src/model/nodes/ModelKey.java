package model.nodes;

import java.util.Objects;

import game.Directions.Direction;


/**
 * @author dvdco
 * This class regulates model units.The apothem of a hexagon has been chosen as the unit measurement. Thus, the height of
 * each hexagon is two units. The radius of the hexagon is calculated relative to the apothem. Thus, the width of each hexagon
 * is two times the radius.
 * Another note is that, because of how the hexagons are initialized, the steps should be combinations of (even, even) or (odd, odd).
 * Model units do not correspond to the cartesian plane. The vertical components have been reflected across the x-axis. This allows 
 * them to mirror screen coordinates more closely. 
 */
class ModelKey {
	
	//Class Fields
	private static final double UNIT_APOTHEM = 1.0;//One model unit is equivalent to the unit apothem of a hexagon.
	private static final double UNIT_RADIUS = (2 * Math.sqrt(3)) / 3.0;
	private static final double[] DIM = {2.0* UNIT_RADIUS, 2.0*UNIT_APOTHEM};//Rectangular length and width
	
	//Instance Fields
	private final double[] center;
	private final int[] steps;
	private final Hexagon region;
	
	ModelKey(int[] steps) {
		/*
		 * Tests coordinates. Accepts (even, even) or (odd, odd).
		 * Rejects (even, odd) and (odd, even).
		 */
		if (!((steps[0] * steps[1] % 2 != 0)||((steps[0] % 2 == 0)&&(steps[1] % 2 == 0)))) {
			System.err.println("(" + steps[0] + ","+ steps[1] + ") is an invald node key. Must be formatted as (even, even) or (odd, odd).");
			System.exit(0);
		}
		this.steps = steps;
		center = new double[] {steps[0] * 1.5* UNIT_RADIUS, steps[1] * UNIT_APOTHEM};
		region = new Hexagon();
	}
	
	
	////////////////// Accessors ///////////////////////////
	
	double[] getCenter() {
		return center.clone();
	}
	
	static double[] getDimensions() {
		return DIM;
	}
	
	ModelKey getNeighborKey(Direction dir) {
		int[] neighbor = steps.clone();
		switch(dir) {
		case NE:{
			neighbor[0] += 1;
			neighbor[1] += -1;
		}
		break;
		case N:{
			neighbor[0] += 0;
			neighbor[1] += -2;
		}
		break;
		case NW:{
			neighbor[0] += -1;
			neighbor[1] += -1;
		}
		break;
		case SW:{
			neighbor[0] += -1;
			neighbor[1] += 1;
		}
		break;
		case S:{
			neighbor[0] += 0;
			neighbor[1] += 2;
		}
		break;
		case SE:{
			neighbor[0] += 1;
			neighbor[1] += 1;
		}
		break;
		case E:{
			System.err.println("Error in getNeighborKey: no Eastward hexagon");
			System.exit(0);
		}
		break;
		case W:{
			System.err.println("Error in getNeighborKey: no Westward hexagon");
			System.exit(0);
		}
		break;
		}//End of switch block
		return new ModelKey(neighbor);
	}
	
	////////////////////// Checkers ///////////////////////
	
	boolean checkPoint(double[] point) {
		return region.contains(point);
	}
	
	/////////////////// Overrides //////////////////////////
	
	@Override
	public boolean equals(Object o) {
		//Gates against comparing unlike objects.
		if (o.getClass() != this.getClass())
			return false;
		ModelKey p = (ModelKey) o;
		double[] otherCenter = p.getCenter();
		if (center[0] == otherCenter[0] && center[1] == otherCenter[1])
			return true;
		else { 
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		//Must hash on the primitives for different keys with the same 
		//coordinates to be equal. Hashing on the int array caused a bug.
		return Objects.hash(center[0], center[1]);
		
	}
	
	@Override
	public String toString() {
		return "(" + center[0] + ", " + center[1] + ")";
	}
	
	//////////////////////////// Inner Class /////////////////////////////////////
	
	private class Hexagon {
		
		//Represents the hexagon as six unit vectors.
		private final double[][] UNIT_MATRIX = {{1.0, 0.5, -0.5, -1.0, -0.5, 0.5},
													{0.0, -Math.sqrt(3.0)/2, -Math.sqrt(3.0)/2, 0.0, Math.sqrt(3.0)/2, Math.sqrt(3.0)/2}};
		
		private double[][] vertices = new double[2][6];
		
		/////////////////////////// Constructor /////////////////////////////////////////////
		
		Hexagon() {
			updateVertices();
		}
		
		/////////////////////// Helper Method //////////////////////////////////////////
		
		private void updateVertices(){
			double center[] = getCenter();
				for(int i = 0; i < 2; i++) {
					for(int j = 0; j < 6; j++) {
						vertices[i][j] = UNIT_RADIUS * UNIT_MATRIX[i][j] + center[i];
					}
				}
		}
		
		/////////////////////// Checker Methods /////////////////////////////////////////
		
		/**
		 * Method tests point (x, y) lies with a hexagon's region or on its boundary.
		 * @param x
		 * @param y
		 * @return true if point is contained; otherwise returns false.
		 */
		public boolean contains(double[] coords) {
			/*
			 * Since only regular hexagons are being tested, I have simplified my testing.
			 * Rather than relying on ray testing or a system of inequalities, I rely on distance
			 * from the center. To find the distance of the hexagon's edges from the center, I transposed
			 * a triangle with a varying central angle on an equilateral triangle so that both triangles
			 * share one leg that's the length of the radius and share one constant pi/3 radian angle.
			 * I then applied the law of sines to find the length of the side spanning the center and the 
			 * hexagon's edge. 
			 */
			
			//Applies distance formula to find point's distance from center.
			double[] cen = getCenter();
			double width = coords[0] - cen[0];
			double height = coords[1] - cen[1];
			double pointDistance = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
			
			//Calculates angle in radians.
			double angle;
			//Gates for undefined values
			if(height >= 0 && width == 0)
				angle = Math.PI/2;
			else if(height < 0 && width == 0)
				angle = 3 * Math.PI/2;
			//Conditional for second and third quadrants
			else if (width < 0)
				angle = Math.atan(height/width) + Math.PI;
			//Conditional for first quadrant
			else if (height >= 0 && width > 0)
				angle = Math.atan(height/width);
			//Conditional for fourth quadrant
			else
				angle = Math.atan(height/width) + 2 * Math.PI;
			
			boolean top;//Flag for resolving overlap.
			if (angle <= Math.PI)
				top = true;
			else
				top = false;
			
			//Uses law of sines to calculate the distance from the center to the edge for the line containing
			// the point.
			angle %= Math.PI/3;//Only a domain of 0 to Pi/3 applies.
			double regionDistance = (UNIT_RADIUS * Math.sqrt(3)/2)/Math.sin(2*Math.PI/3 - angle);
			
			//Tricks java into rounding to 5 decimal places.
			//This is necessary to compensate for truncated values; otherwise, boundary points would be excluded.
			final double PLACES = Math.pow(10.0, 5); 
			pointDistance = Math.round(pointDistance * PLACES)/PLACES;
			regionDistance = Math.round(regionDistance * PLACES)/PLACES;
			
			//Resolves cases of overlap by making the top of the hexagon inclusive and the top exclusive.
			if(top && pointDistance <= regionDistance)
				return true;
			else if (!top && pointDistance < regionDistance)
				return true; 
			else 
				return false;
		}
		
		///////////////////////////// Overrides ////////////////////////////////////
		
		public String toString() {
			updateVertices();
			StringBuilder output = new StringBuilder();
			for(int i = 0; i < 2; i++) {
				output.append("[");
				for(int j = 0; j < 6; j++) {
					double num = vertices[i][j];
					if (j < 5)
						output.append(num + ", ");
					else
						output.append(num);
				}
				output.append("]\n");
			}
			return output.toString();
		}
		
	}//End of Hexagon
 

}
