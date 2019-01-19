package model.nodes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import game.Directions.Direction;
import image_library.Tile;



abstract class ConcreteNode implements Node{
	
	//State Fields
	private final Base base;
	private NodeState state;
	private final ModelKey key;
	private Map<Direction, Node> neighbors;
	
	private final Tile tile;
	private final Tile bord;//TODO Strip this out before final.
	
	/////////////////// Constructor and Initializers /////////////////////////
	protected ConcreteNode(ModelKey key, Tile tile, Tile bord) {
		this.key = key;
		
		this.tile = tile;
		this.bord = bord;
		
		//Initializes state
		base = new Base(this);
		state = new Off(base);
		
		/*
		 * First creates keys
		 * for hashmap. Sets references in the neighbors hashmap if they exist 
		 * in the total board; otherwise, sets a null object. If the hashmap
		 * contains null values, neighborsCompelete is set to false.
		 */
		neighbors = new HashMap<>();
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

	////////////////////// Accessor Methods /////////////////////////////////
	
	/**
	 * Forwarding method. 
	 * @return Array representing the unit vector for the node.
	 */
	@Override
	public final double[] getCenter() {
		return key.getCenter();
		}
	
	abstract double getFoodFactor();
	
	final ModelKey getKey() {
		return this.key;
		}
	
	/**
	 * Returns neighboring node if it exists, or creates new node to return.
	 */
	@Override
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
	
	final Pattern getPattern() {
		return state.getPattern();
	}
	
	abstract double getWindFactor();
	
	@Override
	public final double getScent() {
		return state.getScent();
	}
	
	/////////////////////////// Mutator Methods //////////////////////////////
	
	final void advance() {
		tile.advance();
	}
	
	@Override
	public final double eat(double rate) {
		return state.eat(rate);
	}
	
	@Override
	public final void setBorder() {
		if(state.checkInterior()) {
			state = new Border(state);
		}
		else {
			System.err.println("Error in Node.setBorder");
			System.exit(0);
		}
	}
	
	@Override
	public final void setInterior() {
		//Handles transition from border to interior.
		if(state.checkBorder()) {
			NodeState striped = state.getWrapped();
			if(striped.checkInterior()) {
				state = striped;
			}
			else {
				System.err.println("Error in Node.setInterior");
				System.exit(0);
			}
		}
		//Contributes to transition from off to border.
		else if(state.checkOff()) {
			state = new On(base);
		}
		else {
			System.err.println("Error in Node.setInterior");
			System.exit(0);
		}
		
		
	}
	
	@Override
	public final void setOff() {
		state = new Off(base);
	}
	
	@Override
	public final void setScent(double scent) {
		state.setScent(scent);
	}
	
	final void setTileFacing(Direction facing) {
		tile.setFacing(facing);
	}
	
	final void setWind(double[] wind) {
		state.setWind(wind);
	}
	
	final void sendTile() {
		tile.send();
	}
	
	final void sendBorder() {
		//TODO Strip out of final
		bord.send();
	}
	
	@Override
	public final void transfer(Node node) {
		//Casting is better than exposing these methods to the interface.
		ConcreteNode cn = (ConcreteNode)node;
		Pattern pat = cn.getPattern();
		Border bord = null;
		
		//Handles transition from off to border
		if(state.checkOff()) {
			bord = new Border(new On(base));
		}
		//Handles transition from border to border
		else if(state.checkBorder()) {
			bord = (Border) state;
		}
		//Handles transition from interior to border
		else if(state.checkInterior()) {
			bord = new Border(state);
		}
		else {
			System.err.println("Error in Node.transfer");
			System.exit(0);
		}
		
		bord.transfer(pat);
		state = bord;
	}
	
	/**
	 * Performs update according to the node's internal state
	 */
	@Override
	public final boolean update() {
		return state.update();
	}
	
	/////////////////////////////// Checkers ////////////////////////////
	
	@Override
	public final boolean checkBorder() {
		return state.checkBorder();
	}
	
	@Override
	public final boolean checkBorder(Direction dir) {
		return state.checkBorder(dir);
	}
	
	abstract boolean checkDeer();
	
	@Override
	public final boolean checkInterior() {
		return state.checkInterior();
	}
	
	@Override
	public final boolean checkInterior(Direction dir) {
		return state.checkInterior(dir);
	}
	
	@Override
	public final boolean checkOff() {
		return state.checkOff();
	}
	
	@Override
	public final boolean checkOff(Direction dir) {
		return state.checkOff(dir);
	}
	
	@Override
	public abstract boolean checkPassable();
	
	@Override
	public final boolean checkPoint(double[] point) {
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
	
	
	
	
	@Override
	public boolean equals(Object ob) {
		//Returns false if not a node
		if(ob.getClass() != ConcreteNode.class)
			return false;
		
		//Returns true if both nodes have the same center
		ConcreteNode other = (ConcreteNode)ob;
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
