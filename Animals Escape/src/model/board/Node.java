package model.board;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import model.Directions.Direction;
import model.screen.Cell;
import units.CellKey;
import units.NodeKey;


public abstract class Node {
	//////////////// Instance Variables /////////////////////
	private NodeKey key;
	private Map<Direction, Node> neighbors;
	private boolean neighborsComplete = false;
	private Cell cell = null;
	private TotalBoard board;
	
	/////////////////// Constructor and Initializers /////////////////////////
	Node(NodeKey center) {
		this.key = center;
		board = TotalBoard.getInstance();
		neighbors = new HashMap<Direction, Node>();
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

	//////////////////// Helper Methods ///////////////////////////////////
	
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
		
		Node n = board.getNode(new NodeKey(dir.getNeighborKey(key.getCenter())));
		if (n == null && neighborsComplete == true)
			neighborsComplete = false;
		return n;
	}
	
	protected int getRanInt() {
		return ThreadLocalRandom.current().nextInt(1, 101);//Uppperbound is exclusive.
	}
	
	////////////////////// Accessor Methods /////////////////////////////////
	
	public Cell getCell() {
		/*
		 * Returns cell or null.
		 */
		return cell;
	}
	
	public Node getNeighbor(Direction dir) {
		/*
		 * Returns neighboring node if it exists, or creates new node to return.
		 */
		updateNeighbors();
		Node n = neighbors.get(dir);
		//Creates node if none exists.
		if (n == null) {
			int[] newCenter= dir.getNeighborKey(key.getCenter());
			Node newNode = board.getNodeInstance(newCenter);
			neighbors.put(dir, newNode);
			return newNode;
		}
		else
			return n;
	}
	
	public NodeKey getNodeKey() {return this.key;}
	
	/**
	 * Forwarding method. 
	 * @return Array representing the unit vector for the node.
	 */
	public int[] getCenter() {return key.getCenter();}
	
	
	/////////////////////////// Mutator Methods //////////////////////////////
	
	
	
	/////////////////////////////// Abstract Methods ////////////////////////////
	
	public abstract boolean checkPassable();
	public abstract void getCommand(CellKey key);
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
	
	void displayCell() {System.out.println(cell);}
	 
}
