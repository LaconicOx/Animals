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
		
		//Calculates angles using arctan.
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
		else {
			//TODO error code.
		}
		System.out.println(angle);
		System.out.println(5.0 * Math.PI/6);
		if(angle == 11.0 * Math.PI/6 || angle == 5.0 * Math.PI/6) {
			System.out.println("On line");
			
		}
		
		if(angle <= 5.0 * Math.PI / 6.0) {
			return greater;
		}
		else if(angle <= 11.0* Math.PI /6.0) {
			return lesser;
		}
		else
			return equal;
	}
	
	public static void main(String[] args) {
		ViewInterface view = new SwingFacade();
		NodeFactory nf = NodeFactory.getInstance(view);
		
		Node origin = NodeFactory.getOrigin();
		Node se = origin.getNeighbor(Direction.SE);
		Node sese = se.getNeighbor(Direction.SE);
		Node nw = origin.getNeighbor(Direction.NW);
		
		System.out.println(compare(origin, nw));
	}

}
