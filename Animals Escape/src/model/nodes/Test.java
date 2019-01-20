package model.nodes;

import game.Directions.Direction;
import view.SwingFacade;
import view.ViewInterface;

class Test {
	
	public static int compare(Node left, Node right) {
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
		
		if(angle - ray2 >= error) {
			return lesser;
		}
		else if(angle - ray1 >= error) {
			return lesser;
		}
		else
			return greater;
	}
	
	public static void main(String[] args) {
		ViewInterface view = new SwingFacade();
		NodeFactory nf = NodeFactory.getInstance(view);
		
		Node origin = NodeFactory.getOrigin();
		Node se = origin.getNeighbor(Direction.SE);
		Node sw = origin.getNeighbor(Direction.SW);
		Node ne = origin.getNeighbor(Direction.NE);
		Node sese = se.getNeighbor(Direction.SE);
		Node nw = origin.getNeighbor(Direction.NW);
		
		System.out.println(compare(origin, sw));
	}

}
