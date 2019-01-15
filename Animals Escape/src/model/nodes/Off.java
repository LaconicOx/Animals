package model.nodes;

import game.Directions.Direction;

class Off extends BaseWrapper{
	
	
	Off(NodeState state) {
		super(state);
	}
	
	//////////////////// Accessors ////////////////////////
	
	@Override
	double eat(double amount) {
		return 0;
	}
	
	@Override
	final protected double getFoodFactor() {
		System.err.println("Off.getFoodFactor called");
		System.exit(0);
		return 0;
	}
	
	@Override
	final protected Node getNeighbor(Direction dir) {
		System.err.println("Off.getNeighbor called");
		System.exit(0);
		return null;
	}
	
	@Override
	final protected double getWindFactor() {
		System.err.println("Off.getWindFactor called");
		System.exit(0);
		return 0;
	}
	
	@Override
	final double getScent() {
		System.err.println("Off.getScent called");
		System.exit(0);
		return 0;
	}
	
	/////////////////////// Mutators ///////////////////////
	
	@Override
	final protected void advance() {
		System.err.println("Off.getScent called");
		System.exit(0);
	}
	
	@Override
	final protected void sendBorder() {
		System.err.println("Off.sendBorder called");
		System.exit(0);
	}
	
	@Override
	final protected void sendTile() {
		System.err.println("Off.sendTile called");
		System.exit(0);
	}
	
	@Override
	final protected void setTileFacing(Direction facing) {
		System.err.println("Off.setTileFacing called");
		System.exit(0);
	}
	
	@Override
	void setScent(double scent) {
		// Do nothing
		
	}

	@Override
	void setWind(double[] wind) {
		// Do nothing
	}

	
	@Override
	final boolean update() {
		//TODO
		return false;
	}
	
	//////////////////////// Checkers //////////////////////////
	
	@Override
	boolean checkOff() {
		return true;
	}
	
	////////////////////// Object Overrides ///////////////////////
	
	@Override
	public String toString() {
		return " is Off";
	}

	

}
