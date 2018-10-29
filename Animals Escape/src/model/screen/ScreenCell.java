package model.screen;


import java.util.Objects;

import model.board.Node;

public class ScreenCell extends Cell {
	
	Hexagon region;
	
	/////////////////////// Constructor ///////////////////////////////////////
	public ScreenCell(Node node){
		super(node);
		region = new Hexagon();
	}
	
	////////////////////////// Checker Methods ///////////////////////////////
	
	public boolean isContained(double x, double y) {return region.contains(x, y);}
	
	
	/////////////////////////// Mutator Methods ////////////////////////////
	
	
	///////////////////////////// Overrides ///////////////////////////////
	
	@Override
	public String toString() {
		return "ScreenCell (" + getX() + "," + getY() + ") maps to Node " + node.toString();
		//return "Cell (" + center[0] + "," + center[1] + ")";
	}
	
	@Override
	public boolean equals(Object ob) {
		double[] center = getCenter();
		if (ob.getClass() != this.getClass())
			return false;
		ScreenCell obCell = (ScreenCell)ob;
		if((center[0] == obCell.getX()) && (center[1] == obCell.getY()))
			return true;
		else return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(node, region);
	}
	
	//////////////////////////// Debugging Methods /////////////////////////
	
	public void display() {
		System.out.println(region.toString());
	}
	
	/////////////////////////// Inner Class /////////////////////////////////////
	
	private class Hexagon {
		
		//Represents the hexagon as six unit vectors.
		private final double[][] UNIT_MATRIX = {{1.0, 0.5, -0.5, -1.0, -0.5, 0.5},
													{0.0, Math.sqrt(3.0)/2, Math.sqrt(3.0)/2, 0.0, -Math.sqrt(3.0)/2, -Math.sqrt(3.0)/2}};
		
		private double[][] vertices = new double[2][6];
		
		/////////////////////////// Constructor /////////////////////////////////////////////
		
		Hexagon() {
			updateVertices();
		}
		
		/////////////////////// Helper Method //////////////////////////////////////////
		
		private void updateVertices(){
			double center[] = {getX(), getY()};
				for(int i = 0; i < 2; i++) {
					for(int j = 0; j < 6; j++) {
						vertices[i][j] = parameters.getTileRadius() * UNIT_MATRIX[i][j] + center[i];
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
		public boolean contains(double x, double y) {
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
			double width = x - getX();
			double height = y - getY();
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
			double regionDistance = (parameters.getTileRadius() * Math.sqrt(3)/2)/Math.sin(2*Math.PI/3 - angle);
			
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
		
		///////////////////////// Accessor Methods /////////////////////////////
		
		
		double[][] getMatrix(){
			updateVertices();
			return vertices;
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
