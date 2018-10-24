package model.node;

import java.util.HashMap;
import java.util.Map;

import model.ModelUtility.Direction;
import model.cell.Cell;


public abstract class Node {
	//////////////// Instance Variables /////////////////////
	private NodeKey center;
	private Map<Direction, Node> neighbors;
	private boolean neighborsComplete = false;
	
	private Cell[] cell = new Cell[1];
	
	/////////////////// Constructor and Initializers /////////////////////////
	Node(NodeKey center) {
		this.center = center;
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
		for(Direction key : neighbors.keySet()) {
			neighbors.put(key, findNeighbor(key));
		}
	}


	
	//////////////////// Helper Methods ///////////////////////////////////
	
	private void updateNeighbors() {
		if (neighborsComplete ==  false) {
			neighborsComplete = true;
			for (Direction key : neighbors.keySet())
				neighbors.computeIfAbsent(key, x -> findNeighbor(x));
		}
	}
	
	private Node findNeighbor(Direction key) {
		/*
		 * Helper function for setNeighbors and updateNeighbors. It is particularly
		 * necessary for the completeIfAbsent function used in updateNeighbors. It has the
		 * side effect of setting neighborsComplete to false if it returns any null values.
		 */
		int x = center.getX();
		int y = center.getY();
		Node n = TotalBoard.getNode(new NodeKey(key.getNeighborCoord(x, y)));
		if (n == null && neighborsComplete == true)
			neighborsComplete = false;
		return n;
	}
	

	////////////////////// Default Accessor Methods /////////////////////////////////
	
	public Cell getCell() {
		/*
		 * Returns cell or null.
		 */
		return cell[0];
	}
	
	public Node getNeighbor(Direction dir) {
		/*
		 * Returns neighboring node if it exists, or creates new node to return.
		 */
		updateNeighbors();
		Node n = neighbors.get(dir);
		//Creates node if none exists.
		if (n == null) {
			int[] newCenter= dir.getNeighborCoord(getX(), getY());
			Node newNode = TotalBoard.getNodeInstance(newCenter[0], newCenter[1]);
			neighbors.put(dir, newNode);
			return newNode;
		}
		else
			return n;
	}
	
	public NodeKey getCenter() {return this.center;}
	
	public int getX() {return center.getX();}
	
	public int getY() { return center.getY();}

	/////////////////////////// Default Mutator Methods //////////////////////////////
	
	public void setCell(Cell c) { cell[0] = c;}
	
	public void clearCell() {cell[0] = null;}
	
	/////////////////////////////// Abstract Methods ////////////////////////////
	
	public abstract boolean isPassable();
	public abstract void getCommand(double[] center);
	
	///////////////////////// Overridden Methods ////////////////////////////
	
	public String toString() {
		return center.toString();
	}
	
	//////////////////////////// Debugging Methods ///////////////////////////
	
	void printNeighbors() {
		StringBuilder message = new StringBuilder("The neighbors of " + center.toString() +" are ");
		for (Direction key : neighbors.keySet()) {
			if (neighbors.get(key) == null)
				message.append("None, ");
			else
				message.append(neighbors.get(key).toString() + ", ");
		}
			
		System.out.println(message.toString());
	}
	
	void displayCell() {System.out.println(cell[0]);}
	 
}
