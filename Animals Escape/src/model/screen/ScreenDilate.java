package model.screen;


import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;

import game.Directions.Direction;
import model.nodes.Node;
import view.ViewInterface;

public class ScreenDilate extends ScreenState{
	
	//Instance Fields
	private final ViewInterface view;
	private double factor;
	
	public ScreenDilate(Screen screen, ViewInterface view) {
		super(screen);
		this.view = view;
	}
	
	/**
	 * 
	 * @param node
	 * @param boundaries
	 * @return false if exclusively outside of boundaries true if inclusively inside boundaries.
	 */
	private final boolean checkBoundary(Node node, double[] boundaries) {
		double[] center = node.getCenter();
		if(center[0] < boundaries[0] || center[0] > boundaries[1] || center[1] < boundaries[2] || center[1] > boundaries[3])
			return false;
		else
			return true;
	}
	
	private final void decrease(double factor) {
		
		//TODO
	
		
	}
	
	@Override
	public final void dilate(double factor) {
		this.factor = factor;
	}
	
	private final void increase(double factor) {
		view.updateScale(factor);
		double[] boundaries = getModelBoundaries(view.getShift(), view.getScreenDim(), view.getScale());
		
		//Iterates through border setting nodes to interior and adding new nodes.
		//If new nodes are within boundary they are added to incompletes for futher processing.
		Queue<Node> incompletes = new ArrayDeque<>();
		Iterator<Node> borderIt = screen.getBorderIterator(new CounterClockwise(view.getShift()));
		Node node;
		
		while(borderIt.hasNext()) {
			node = borderIt.next();
			//Gates for nodes already outside of the boundaries. 
			if (!checkBoundary(node, boundaries)){
				continue;
			}
			//Iterates through the node's neighbors.
			Iterator<Direction> dirIt = Direction.getNodeIterator();
			while(dirIt.hasNext()) {
				Direction toward = dirIt.next();
				//A way to avoid creating duplicates is to focus on off nodes that have interior nodes
				//in the opposite direction.
				if(node.checkOff(toward) && node.checkInterior(Direction.getOpposite(toward))) {
					Node candidate = node.getNeighbor(toward);
					//If candidate node is within boundary, sets it to interior
					//and adds it to imcompletes for further processing.
					if(checkBoundary(candidate, boundaries)) {
						candidate.setInterior();
						screen.add(candidate);
						incompletes.add(candidate);
					}
					//Otherwise, node is set as border and no further processing is required.
					else {
						candidate.setInterior();
						candidate.setBorder();
						screen.add(candidate);
					}
				}
			}
		}//End of while-loop
		
		//I switched from an iterator to a queue in order to support adding nodes while iterating.
		while(!incompletes.isEmpty()) {
			node = incompletes.poll();
		}
	}
	
	@Override
	public final void update() {
		if(factor > 1.0) {
			increase(factor);
		}
		else if(factor < 1.0) {
			decrease(factor);
		}
		else {
			//Do nothing.
		}
		
		//Returns state to normal
		screen.setState(screen.getStateNormal());
	}
	
	///////////////////////////// Inner Class //////////////////////////////////////////
	
	private final class  CounterClockwise implements Comparator<Node>{
		
		private final double[] shift;
		
		CounterClockwise(double[] shift){
			this.shift = shift;
		}
		
		private double findAngle(double[] coords) {
			
			double xComp = coords[0] - shift[0];
			//Multiplying by negative one adjusting for the reflection
			double yComp = -1.0 * (coords[1] - shift[1]);
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
				//TODO error code
			}
			
			return angle;
		}
		
		@Override
		public int compare(Node left, Node right) {
			int lesser = -1;
			int equal = 0;
			int greater = 1;
			
			double lfAngle = findAngle(left.getCenter());
			double rtAngle = findAngle(right.getCenter());
			
			if(lfAngle < rtAngle)
				return lesser;
			else if(lfAngle > rtAngle)
				return greater;
			else
				return equal;
		}
		
	}//End of CounterClockwise
}
