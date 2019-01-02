package model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import game.Directions.Direction;
import image_library.Tile;
import model.ModelKey;


public abstract class Node {
	
	//Class Fields
	private static final double[] NO_WIND = {0,0};
	private static final double WIND_FORCE = 5.0;
	private static final double WIND_PROB = 0.001;
	private static final double WIND_CUTOOF = 0.1;
	private static final double SCENT_MIN = 0.0;
	private static final double SCENT_FADE = 0.1;
	
	//Instance Fields
	private double scent;
	private final ModelKey key;
	private Map<Direction, Node> neighbors;
	private ArrayList<double[]> windInputs;//Stores wind vectors received from neighboring nodes.
	
	/////////////////// Constructor and Initializers /////////////////////////
	Node(ModelKey key) {
		this.key = key;
		scent = SCENT_MIN;
		windInputs = new ArrayList<>();
		windInputs.add(NO_WIND);
		
		/*
		 * First creates keys
		 * for hashmap. Sets references in the neighbors hashmap if they exist 
		 * in the total board; otherwise, sets a null object. If the hashmap
		 * contains null values, neighborsCompelete is set to false.
		 */
		neighbors = new HashMap<Direction, Node>();
		neighbors.put(Direction.NE, null);
		neighbors.put(Direction.N, null);
		neighbors.put(Direction.NW, null);
		neighbors.put(Direction.SW, null);
		neighbors.put(Direction.S, null);
		neighbors.put(Direction.SE, null);
		
		for(Direction dir : neighbors.keySet()) {
			neighbors.put(dir, TotalBoard.getNode(key.getNeighborKey(dir), true));
		}
	}

	////////////////////// Accessor Methods /////////////////////////////////
	
	public abstract Tile getTile();
	
	private final Node getNeighbor(Direction dir) {
		/*
		 * Returns neighboring node if it exists, or creates new node to return.
		 */
		//updateNeighbors();
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
	
	public final ModelKey getKey() {
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
	
	/**
	 * Uses values received from neighboring cells to calculate the cell's wind value and to 
	 * send wind values to neighboring cells. Stored values are then cleared.
	 * @return vector representing cell's wind value.
	 */
	public final double[] getWind() {
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
		if(magnitude < WIND_CUTOOF) {
			return NO_WIND;
		}
		//Determines neighboring nodes to send wind.
		else {
			Direction main = Direction.getDirection(NO_WIND, wind);
			
			Direction counterclockwise = null;
			Direction clockwise = null;
			
			//System.out.println(main);
			
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
	
	
	public final double getScent() {
		return scent;
	}
	
	/////////////////////////// Mutator Methods //////////////////////////////
	
	/**
	 * Recieves vector representing wind a wind value and stores it in a list.
	 * @param wind - vector representing a wind value.
	 */
	protected final void recieveWind(double[] wind) {
		windInputs.add(wind);
	}
	
	/**
	 * Arbitrarily clears wind inputs. 
	 * Only to be called by border cells.
	 */
	public final void clearWind() {
		windInputs.clear();
		windInputs.add(NO_WIND);
	}
	
	public final void genWind() {
		double prob = ThreadLocalRandom.current().nextDouble(1.0);
		if (prob <= WIND_PROB) {
			Iterator<Direction> neighIt = neighbors.keySet().iterator();
			
			while (neighIt.hasNext()) {
				Direction dir = neighIt.next();
				Node node = getNeighbor(dir);
				node.recieveWind(dir.scaledVector(WIND_FORCE));
			}
		}
	}
	
	public abstract void updateFood();
	
	public final void updateScent() {
		if (scent > SCENT_MIN) {
			scent -= SCENT_FADE;
		}
	}
	
	public final void setScent(double scent) {
		this.scent = scent;
	}
	
	public double eatFood(double rate) {
		return 0.0;
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
