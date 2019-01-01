package model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import game.Directions.Direction;
import image_library.Tile;
import model.ModelKey;


public abstract class Node {
	
	//Class Fields
	private static final double[] NO_WIND = {0,0};
	
	//Instance Fields
	private final ModelKey key;
	private Map<Direction, Node> neighbors;
	private boolean neighborsComplete = false;
	private ArrayList<double[]> windInputs;//Stores wind vectors received from neighboring nodes.
	
	/////////////////// Constructor and Initializers /////////////////////////
	Node(ModelKey key) {
		this.key = key;
		neighbors = new HashMap<Direction, Node>();
		windInputs = new ArrayList<>();
		windInputs.add(NO_WIND);
		initNeighbors();
	}
	
	private void initNeighbors() {
		/*
		 * Helper function called only by the constructor. First creates keys
		 * for hashmap. Sets references in the neighbors hashmap if they exist 
		 * in the total board; otherwise, sets a null object. If the hashmap
		 * contains null values, neighborsCompelete is set to false.
		 */
		
		neighbors.put(Direction.NE, null);
		neighbors.put(Direction.N, null);
		neighbors.put(Direction.NW, null);
		neighbors.put(Direction.SW, null);
		neighbors.put(Direction.S, null);
		neighbors.put(Direction.SE, null);
		
		neighborsComplete = true;
		for(Direction dir : neighbors.keySet()) {
			neighbors.put(dir, findNeighbor(dir));
		}
	}

	////////////////////// Accessor Methods /////////////////////////////////
	
	protected int getRanInt() {
		return ThreadLocalRandom.current().nextInt(1, 101);//Uppperbound is exclusive.
	}
	
	public abstract Tile getTile();
	
	public Node getNeighbor(Direction dir) {
		/*
		 * Returns neighboring node if it exists, or creates new node to return.
		 */
		updateNeighbors();
		Node n = neighbors.get(dir);
		//Creates node if none exists.
		if (n == null) {
			Node newNode = TotalBoard.getNode(key.getNeighborKey(dir), false);
			neighbors.put(dir, newNode);
			return newNode;
		}
		else
			return n;
	}
	
	public ModelKey getNodeKey() {
		return this.key;
		}
	
	/**
	 * Forwarding method. 
	 * @return Array representing the unit vector for the node.
	 */
	public double[] getCenter() {
		return key.getCenter();
		}
	
	public ModelKey getNeighborKey(Direction dir) {
		return key.getNeighborKey(dir);
	}
	
	protected abstract double getWindFactor();
	
	/////////////////////////// Mutator Methods //////////////////////////////
	
	private void updateNeighbors() {
		if (neighborsComplete ==  false) {
			neighborsComplete = true;
			for (Direction dir : neighbors.keySet())
				neighbors.computeIfAbsent(dir, x -> findNeighbor(x));
		}
	}
	
	private Node findNeighbor(Direction dir) {
		/*
		 * Helper function for setNeighbors and updateNeighbors. It is particularly
		 * necessary for the completeIfAbsent function used in updateNeighbors. It has the
		 * side effect of setting neighborsComplete to false if it returns any null values.
		 */
		
		Node n = TotalBoard.getNode(key.getNeighborKey(dir), true);
		if (n == null && neighborsComplete == true)
			neighborsComplete = false;
		return n;
	}
	
	protected final void recieveWind(double[] wind) {
		windInputs.add(wind);
	}
	
	public final void killWind() {
		windInputs.clear();
		windInputs.add(NO_WIND);
	}
	public final double[] findWind() {
		double[] wind = {0, 0};
		
		//Sums vectors to calculate wind's direction and magnitude.
		for (double[] vector : windInputs) {
			wind[0] += vector[0];
			wind[1] += vector[1];
		}
		
		//Resets wind inputs
		windInputs.clear();
		windInputs.add(NO_WIND);
		
		//Scalar multiplication to represent wind interference.
		double interference= getWindFactor();
		wind[0] *= interference;
		wind[1] *= interference;
		
		double magnitude = Math.sqrt(Math.pow(wind[0], 2.0) + Math.pow(wind[1], 2.0));
		
		//Gate to cut off tailing calculations.
		if(magnitude > 0.1) {
			return NO_WIND;
		}
		//Determines neighboring nodes to send wind.
		else {
			Direction main = Direction.getDirection(NO_WIND, wind);
			
			Direction counterclockwise = null;
			Direction clockwise = null;
			
			switch(main) {
			case NE:{
				counterclockwise = Direction.N;
				clockwise = Direction.SE;
			}
			break;
			case N:{
				counterclockwise = Direction.NW;
				clockwise = Direction.NE;
			}
			break;
			case NW:{
				counterclockwise = Direction.SW;
				clockwise = Direction.N;
			}
			break;
			case SW:{
				counterclockwise = Direction.S;
				clockwise = Direction.NW;
			}
			break;
			case S:{
				counterclockwise = Direction.SE;
				clockwise = Direction.SW;
			}
			break;
			case SE: {
				counterclockwise = Direction.NE;
				clockwise = Direction.S;
			}
			break;
			case E:
			case W:{
				System.err.println("Error in findWind(): E or W selected as direction");
				System.exit(0);
			}
			}
			
			//Wind is distributed by a simplified normal distribution.
			double bulk = 0.68;
			double tail = 0.21;
			getNeighbor(main).recieveWind(new double[] {wind[0] * bulk, wind[1] * bulk});
			getNeighbor(counterclockwise).recieveWind(new double[] {wind[0] * tail, wind[1] * tail});
			getNeighbor(clockwise).recieveWind(new double[] {wind[0] * tail, wind[1] * tail});
			
			return wind;
		}
	}
	
	/////////////////////////////// Checkers ////////////////////////////
	
	public boolean checkPoint(double[] point) {
		return key.checkPoint(point);
	}
	
	public abstract boolean checkPassable();
	public abstract boolean checkDeer();
	
	///////////////////////// Overridden Methods ////////////////////////////
	
	public String toString() {
		return key.toString();
	}
	
	//////////////////////////// Debugging Methods ///////////////////////////
	
	void printNeighbors() {
		StringBuilder message = new StringBuilder("The neighbors of " + key.toString() +" are ");
		for (Direction dir : neighbors.keySet()) {
			if (neighbors.get(dir) == null)
				message.append("None, ");
			else
				message.append(neighbors.get(dir).toString() + ", ");
		}
			
		System.out.println(message.toString());
	}
	
	 
}
