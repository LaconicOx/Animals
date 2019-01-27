package model.screen;


import java.util.Comparator;
import java.util.Iterator;

import game.Directions.Direction;
import model.nodes.Node;

public class ScreenShift extends ScreenState{
	
	private Direction toward;
	
	public ScreenShift(Screen screen) {
		super(screen);
		this.toward = null;
	}

	////////////////////////// Mutators ///////////////////////////////
	
	@Override
	final void shift(Direction toward) {
		this.toward = toward;
	}//End of Shift
	
	@Override
	final void update() {
		
		//Selects appropriate comparator based on direction.
		Comparator<Node> comp = null;
		if(toward == Direction.NE)
			comp = new NorthEast();
		else if(toward == Direction.N)
			comp = new North();
		else if(toward == Direction.NW) {
			comp = new NorthWest();
		}
		else if(toward == Direction.SW)
			comp = new SouthWest();
		else if(toward == Direction.S)
			comp = new South();
		else if(toward == Direction.SE)
			comp = new SouthEast();
		else {
			//TODO Error code
		}
		
		Iterator<Node> border = screen.getBorderIterator(comp);
		
		
		
		
		while(border.hasNext()) {
			Node current = border.next();
			
			//Transfers current node's border to it's neighbor.
			Node neighbor = current.getNeighbor(toward);
			if(neighbor.checkOff()) {
				neighbor.transfer(current);
				screen.add(neighbor);
			}
			else {
				neighbor.transfer(current);
			}
			
			
			//Changes current node's internal state based on the node in the opposite direction.
			Direction opposite = Direction.getOpposite(toward);
			if(current.checkInterior(opposite)) {
				current.setInterior();
			}
			else if(current.checkOff(opposite)) {
				current.setOff();
			}
			else if(current.checkBorder(opposite)) {
				//Do nothing. State will be transfered by the node in the opposite direction.
			}
			else {
				//TODO error code.
			}
		}
		
		//Change screen's state back to normal.
		screen.setState(screen.getStateNormal());
	}

	///////////////////////// Inner Classes ///////////////////////////
	
	private final static class North implements Comparator<Node>{

		@Override
		public int compare(Node left, Node right) {
			int lesser = -1;
			int equal = 0;
			int greater = 1;
			
			double[] leftCen = left.getCenter();
			double[] rightCen = right.getCenter();
			
			double xDiff = leftCen[0] - rightCen[0];
			double yDiff = leftCen[1] - rightCen[1];
			//Remember the y-values have been reflected over the x-axis 
			//in order to mirror screen values.
			
			//The topmost nodes should be first.
			if(yDiff < 0)
				return lesser;
			else if(yDiff > 0)
				return greater;
			else {
				if(xDiff < 0)
					return lesser;
				else if(xDiff > 0)
					return greater;
				else
					return equal;
			}
		}
		
	}//End of North
	
	/**
	 * Comparator orders treeset for iterating when the shifting
	 * toward the south. Bottom is less than top and left is less than right.
	 * @author dvdco
	 *
	 */
	private final static class South implements Comparator<Node>{

		@Override
		public int compare(Node left, Node right) {
			int lesser = -1;
			int equal = 0;
			int greater = 1;
			
			double[] leftCen = left.getCenter();
			double[] rightCen = right.getCenter();
			
			double xDiff = leftCen[0] - rightCen[0];
			double yDiff = leftCen[1] - rightCen[1];
			//Remember the y-values have been reflected over the x-axis 
			//in order to mirror screen values.
			
			//The bottom-most nodes should be first.
			if(yDiff > 0)
				return lesser;
			else if(yDiff < 0)
				return greater;
			else {
				if(xDiff < 0)
					return lesser;
				else if(xDiff > 0)
					return greater;
				else
					return equal;
			}
		}
		
	}//End of South
	
	private final static class NorthEast implements Comparator<Node>{

		public int compare(Node left, Node right) {
			int lesser = -1;
			int equal = 0;
			int greater = 1;
			
			double[] leftCen = left.getCenter();
			double[] rightCen = right.getCenter();
			double xComp = rightCen[0] - leftCen[0];
			//Multiplying by negative one adjusting for the reflection
			double yComp = -1.0 * (rightCen[1] - leftCen[1]);
			double angle = 0.0;
			
			/*
			 * Calculates radians using arctan. The conditionals assure that 
			 * angle will always be positive and that 0 <= angle <= 2PI.
			 */
			if(xComp > 0.0 && yComp >= 0.0) {
				angle = Math.atan(yComp/xComp);
			}
			else if(xComp < 0.0) {
				angle = Math.PI + Math.atan(yComp/xComp);
			}
			else if(xComp > 0.0 && yComp < 0.0) {
				angle = 2.0 * Math.PI + Math.atan(yComp/xComp);
			}
			else if(xComp == 0.0 && yComp > 0.0) {
				angle = Math.PI / 2.0;
			}
			else if(xComp == 0.0 && yComp < 0.0) {
				angle = 3.0 * Math.PI / 2.0;
			}
			//The remaining case is xComp == 0 && yComp == 0.
			else {
				return equal;
			}
			
			/*
			 * ray1 and ray2 represent the two rays that make up 
			 * the diagonal. ray1 should come before ray2 when rotating
			 * counter-clockwise.
			 */
			double ray1 = 5.0 * Math.PI / 6.0;
			double ray2 = ray1 + Math.PI;
			double error = 0.00000000000001;
			
			/*
			 * Error checking is necessary because I found that these calculations
			 * don't always have the same number of significant digits.
			 */
			if(angle <= ray1 || Math.abs(angle - ray1) <= error) {
				return greater;
			}
			else if(angle <= ray2 || Math.abs(angle - ray2) <= error) {
				return lesser;
			}
			else
				return greater;
		}
	}//End of NorthEast
	
	private final static class NorthWest implements Comparator<Node>{
		public int compare(Node left, Node right) {
			int lesser = -1;
			int equal = 0;
			int greater = 1;
			
			double[] leftCen = left.getCenter();
			double[] rightCen = right.getCenter();
			double xComp = rightCen[0] - leftCen[0];
			//Multiplying by negative one adjusting for the reflection
			double yComp = -1.0 * (rightCen[1] - leftCen[1]);
			double angle = 0.0;
			
			/*
			 * Calculates radians using arctan. The conditionals assure that 
			 * angle will always be positive and that 0 <= angle <= 2PI.
			 */
			if(xComp > 0.0 && yComp >= 0.0) {
				angle = Math.atan(yComp/xComp);
			}
			else if(xComp < 0.0) {
				angle = Math.PI + Math.atan(yComp/xComp);
			}
			else if(xComp > 0.0 && yComp < 0.0) {
				angle = 2.0 * Math.PI + Math.atan(yComp/xComp);
			}
			else if(xComp == 0.0 && yComp > 0.0) {
				angle = Math.PI / 2.0;
			}
			else if(xComp == 0.0 && yComp < 0.0) {
				angle = 3.0 * Math.PI / 2.0;
			}
			//The remaining case is xComp == 0 && yComp == 0.
			else {
				return equal;
			}
			
			/*
			 * ray1 and ray2 represent the two rays that make up 
			 * the diagonal. ray1 should come before ray2 when rotating
			 * counter-clockwise.
			 */
			double ray1 = Math.PI / 6.0;
			double ray2 = ray1 + Math.PI;
			double error = 0.00000000000001;
			
			/*
			 * Error checking is necessary because I found that these calculations
			 * don't always have the same number of significant digits.
			 */
			if(angle >= ray2 || Math.abs(angle - ray2) <= error) {
				return lesser;
			}
			else if (angle >= ray1 || Math.abs(angle - ray1) <= error) {
				return greater;
			}
			else
				return lesser;
		}
	}//End of NorthWest
	
	private final static class SouthWest implements Comparator<Node>{
		public int compare(Node left, Node right) {
			int lesser = -1;
			int equal = 0;
			int greater = 1;
			
			double[] leftCen = left.getCenter();
			double[] rightCen = right.getCenter();
			double xComp = rightCen[0] - leftCen[0];
			//Multiplying by negative one adjusting for the reflection
			double yComp = -1.0 * (rightCen[1] - leftCen[1]);
			double angle = 0.0;
			
			/*
			 * Calculates radians using arctan. The conditionals assure that 
			 * angle will always be positive and that 0 <= angle <= 2PI.
			 */
			if(xComp > 0.0 && yComp >= 0.0) {
				angle = Math.atan(yComp/xComp);
			}
			else if(xComp < 0.0) {
				angle = Math.PI + Math.atan(yComp/xComp);
			}
			else if(xComp > 0.0 && yComp < 0.0) {
				angle = 2.0 * Math.PI + Math.atan(yComp/xComp);
			}
			else if(xComp == 0.0 && yComp > 0.0) {
				angle = Math.PI / 2.0;
			}
			else if(xComp == 0.0 && yComp < 0.0) {
				angle = 3.0 * Math.PI / 2.0;
			}
			//The remaining case is xComp == 0 && yComp == 0.
			else {
				return equal;
			}
			
			/*
			 * ray1 and ray2 represent the two rays that make up 
			 * the diagonal. ray1 should come before ray2 when rotating
			 * counter-clockwise.
			 */
			double ray1 = 5.0 * Math.PI / 6.0;
			double ray2 = ray1 + Math.PI;
			double error = 0.00000000000001;
			
			/*
			 * Error checking is necessary because I found that these calculations
			 * don't always have the same number of significant digits.
			 */
			if(angle <= ray1 || Math.abs(angle - ray1) <= error) {
				return lesser;
			}
			else if(angle <= ray2 || Math.abs(angle - ray2) <= error) {
				return greater;
			}
			else
				return lesser;
		}
	}//End of SouthWest
	
	private final static class SouthEast implements Comparator<Node>{
		public int compare(Node left, Node right) {
			int lesser = -1;
			int equal = 0;
			int greater = 1;
			
			double[] leftCen = left.getCenter();
			double[] rightCen = right.getCenter();
			double xComp = rightCen[0] - leftCen[0];
			//Multiplying by negative one adjusting for the reflection
			double yComp = -1.0 * (rightCen[1] - leftCen[1]);
			double angle = 0.0;
			
			/*
			 * Calculates radians using arctan. The conditionals assure that 
			 * angle will always be positive and that 0 <= angle <= 2PI.
			 */
			if(xComp > 0.0 && yComp >= 0.0) {
				angle = Math.atan(yComp/xComp);
			}
			else if(xComp < 0.0) {
				angle = Math.PI + Math.atan(yComp/xComp);
			}
			else if(xComp > 0.0 && yComp < 0.0) {
				angle = 2.0 * Math.PI + Math.atan(yComp/xComp);
			}
			else if(xComp == 0.0 && yComp > 0.0) {
				angle = Math.PI / 2.0;
			}
			else if(xComp == 0.0 && yComp < 0.0) {
				angle = 3.0 * Math.PI / 2.0;
			}
			//The remaining case is xComp == 0 && yComp == 0.
			else {
				return equal;
			}
			
			/*
			 * ray1 and ray2 represent the two rays that make up 
			 * the diagonal. ray1 should come before ray2 when rotating
			 * counter-clockwise.
			 */
			double ray1 = Math.PI / 6.0;
			double ray2 = ray1 + Math.PI;
			double error = 0.00000000000001;
			
			/*
			 * Error checking is necessary because I found that these calculations
			 * don't always have the same number of significant digits.
			 */
			if(angle >= ray2 || Math.abs(angle - ray2) <= error) {
				return greater;
			}
			else if(angle >= ray1 || Math.abs(angle - ray1) <= error) {
				return lesser;
			}
			else
				return greater;
		}
	}//End of SouthEast
}
