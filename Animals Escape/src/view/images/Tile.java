package view.images;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Tile extends GameImage{
	
	Shape region;
	Color fill;
	
	public Tile(double[][] vertices, Color fill){
		region = new Hexagon(vertices);
		this.fill = fill;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(fill);
		g2.fill(region);
		g2.setPaint(Color.BLACK);
		g2.draw(region);
	}
	
	////////////////////////// Inner Class //////////////////////////////////
	
	private class Hexagon implements Shape{
		private Path2D path;
		
		//////////////////// Constructor and Initializers ///////////////////////////
		
		Hexagon(double[][] vertices){
			
			initPath(vertices);
		}
		
		private void initPath(double[][] vertices) {
			//TODO add assert statements.
			
			path = new Path2D.Double();
			
			//Moves path to point a 0 radians
			path.moveTo(vertices[0][0], vertices[1][0]);
			for(int i = 1; i < 6; i++) {
				path.lineTo(vertices[0][i], vertices[1][i]);
			}
			path.closePath();
		}
		
		////////////////////// Overrides ////////////////////////////////
		@Override
		public boolean contains(Point2D arg0) {return path.contains(arg0);}

		@Override
		public boolean contains(Rectangle2D arg0) {return path.contains(arg0);}

		@Override
		public boolean contains(double arg0, double arg1) {return path.contains(arg0, arg1);}

		@Override
		public boolean contains(double arg0, double arg1, double arg2, double arg3) {return path.contains(arg0, arg1, arg2, arg3);}

		@Override
		public Rectangle getBounds() {return path.getBounds();}

		@Override
		public Rectangle2D getBounds2D() {return path.getBounds2D();}

		@Override
		public PathIterator getPathIterator(AffineTransform arg0) {return path.getPathIterator(arg0);}

		@Override
		public PathIterator getPathIterator(AffineTransform arg0, double arg1) {return path.getPathIterator(arg0, arg1);}

		@Override
		public boolean intersects(Rectangle2D arg0) {return path.intersects(arg0);}

		@Override
		public boolean intersects(double arg0, double arg1, double arg2, double arg3) {return path.intersects(arg0, arg1, arg2, arg3);}
		

		
	}//End of Hexagon
	


}
