package model.nodes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import game.Directions.Direction;
import image_library.Tile;



public abstract class Node implements Comparable<Node>{
	
	//State Fields
	private final Base base;
	private NodeState state;
	private NodeState updateState;
	private final ModelKey key;
	private Map<Direction, Node> neighbors;
	
	private final Tile tile;
	private final Tile bord;//Strip this out before final.
	
	/////////////////// Constructor and Initializers /////////////////////////
	protected Node(ModelKey key, Tile tile, Tile bord) {
		this.key = key;
		
		this.tile = tile;
		this.bord = bord;
		
		//Initializes states
		base = new Base(this);
		
		//Sets states
		updateState = null;
		state = new Off(base);
		
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
			neighbors.put(dir, NodeFactory.getNode(key.getNeighborKey(dir), true));
		}
	}
	
	public void initState(NodeState state){
		this.state = state;
	}
	
	////////////////////// Accessor Methods /////////////////////////////////
	
	/**
	 * Forwarding method. 
	 * @return Array representing the unit vector for the node.
	 */
	public double[] getCenter() {
		return key.getCenter();
		}
	
	abstract double getFoodFactor();
	
	public final ModelKey getKey() {
		return this.key;
		}
	
	/**
	 * Returns neighboring node if it exists, or creates new node to return.
	 */
	public final Node getNeighbor(Direction dir) {
		
		//updateNeighbors();
		Node n = neighbors.get(dir);
		//Creates node if none exists.
		if (n == null) {
			Node newNode = NodeFactory.getNode(key.getNeighborKey(dir), false);
			neighbors.put(dir, newNode);
			return newNode;
		}
		else
			return n;
	}
	
	ModelKey getNeighborKey(Direction dir) {
		return key.getNeighborKey(dir);
	}
	
	
	abstract double getWindFactor();
	
	public final double getScent() {
		return state.getScent();
	}
	
	/////////////////////////// Mutator Methods //////////////////////////////
	
	final void advance() {
		tile.advance();
	}
	
	public final double eat(double rate) {
		return state.eat(rate);
	}
	
	public final void setScent(double scent) {
		state.setScent(scent);
	}
	
	final void setWind(double[] wind) {
		state.setWind(wind);
	}
	
	final void sendTile() {
		tile.send();
	}
	
	public final void sendBorder() {
		//TODO Strip out of final
		bord.send();
	}
	
	public final void setState(NodeState state) {
		this.updateState = state;
	}
	
	final void setTileFacing(Direction facing) {
		tile.setFacing(facing);
	}
	
	/**
	 * Any underlying Borderwrapper should strip
	 * themselves out when updating.
	 */
	void transfer(Border bord) {
		
		//TODO
		
	}
	
	public final void turnOn() {
		
	}
	
	/**
	 * Performs update according to the node's internal state
	 * @return - true if the node's internal state changed, otherwise false.
	 */
	public final void update() {
		if (updateState != null) {
			//System.out.println(this + " to " + updateState);
			state = updateState;
			updateState = null;
			
		}
		state.update();
	}
	
	/////////////////////////////// Checkers ////////////////////////////
	
	public final boolean checkInterior() {
		return state.checkInterior();
	}
	
	public final boolean checkBorder() {
		return state.checkBorder();
	}
	
	abstract boolean checkDeer();
	
	public boolean checkOff() {
		return state.checkOff();
	}
	
	public abstract boolean checkPassable();
	
	public boolean checkPoint(double[] point) {
		return key.checkPoint(point);
	}
	
	boolean checkResting() {
		return tile.checkResting();
	}
	
	///////////////////////// Object Overrides ////////////////////////////
	
	@Override
	public String toString() {
		return key.toString() + state.toString();
	}
	
	/**
	 * Nodes are first ordered by their internal state by the rule: border < active < off.
	 * They are then ordered by column and finally by rows.
	 * @param other
	 * @return
	 */
	@Override
	public int compareTo(Node other) {
		/*
		 * Cells are first ordered by columns and then by rows.
		 * Thus, the top-left cell is the very least, while the bottom-right cell
		 * is the very greatest.
		 */
		
		int lesser = -1;
		int equal = 0;
		int greater = 1;
		
		//Orders nodes according to the state rule: border < active < off
		if (checkBorder() && (other.checkInterior()|| other.checkOff())) {
			return lesser;
		}
		else if (checkInterior()) {
			if (other.checkBorder())
				return greater;
			else if (other.checkOff())
				return lesser;
		}
		else if(checkOff() && (other.checkInterior() || other.checkBorder()))
				return greater;
		
		double[] currentCenter = getCenter();
		double[] otherCenter = other.getCenter();
		
		//Orders nodes according to columns
		if (currentCenter[0] < otherCenter[0])
			return lesser;
		else if (currentCenter[0] > otherCenter[0])
			return greater;
		//Orders nodes according to rows
		else{
			//Remember: the y-components have been relfected to better match screen coordinates.
			if(currentCenter[1] < otherCenter[1])
				return lesser;
			else if (currentCenter[1] > otherCenter[1])
				return greater;
			else
				return equal;
		}
	}
	
	
	@Override
	public boolean equals(Object ob) {
		//Returns false if not a node
		if(ob.getClass() != Node.class)
			return false;
		
		//Returns true if both nodes have the same center
		Node other = (Node)ob;
		double[] otherCenter = other.getCenter();
		double[] currentCenter = getCenter();
		if((currentCenter[0] != otherCenter[0]) || currentCenter[1] != otherCenter[1]) {
			return false;
		}
		else {
			//Prompts errors if states are different. 
			if((checkInterior() && !other.checkInterior()) || (checkBorder() && !other.checkBorder()) || checkOff() && !other.checkOff()) {
				System.err.println("Error in Node.equals: same node coordinates but different states");
				System.exit(0);
			}
			return true;
		}
	}
	
	@Override
	public int hashCode() {
		double[] center = getCenter();
		return Objects.hash(center[0], center[1]);
		
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
