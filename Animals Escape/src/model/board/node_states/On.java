package model.board.node_states;

import game.Directions.Direction;
import model.board.Node;

public class On implements NodeState{
	
	private final Node node;
	private final OnBase base;
	private Active state;
	private Active updateState;
	
	
	public On(Node node) {
		this.node = node;
		base = new OnBase(this);
		state = base;
	}
	
	/////////////////////// Accessors ///////////////////////////////
	
	@Override
	public double eat(double amount) {
		return state.eat(amount);
	}
	
	final Node getNeighbor(Direction dir) {
		return node.getNeighbor(dir);
	}
	
	@Override
	public double getScent() {
		return state.getScent();
	}
	
	//////////////////////////// Mutators ///////////////////////////////
	
	void advance() {
		node.advance();
	}
	
	
	double changeFood(double amount) {
		if(updateState == null) {
			updateState = new FoodWrapper(state, node.getFood());
		}
		else {
			updateState = new FoodWrapper(updateState, node.getFood());
		}
		
		return updateState.eat(amount);
	}
	
	void changeScent(double scent) {
		if(updateState == null) {
			updateState = new WindWrapper(state, node.getWindFactor());
		}
		else {
			updateState = new WindWrapper(updateState, node.getWindFactor());
		}
		
		updateState.receiveScent(scent);
	}
	
	void changeWind(double[] wind) {
		if(updateState == null) {
			updateState = new WindWrapper(state, node.getWindFactor());
		}
		else {
			updateState = new WindWrapper(updateState, node.getWindFactor());
		}
		
		updateState.receiveWind(wind);
	}
	
	public final void initInterior() {
		//TODO
	}
	
	public final void initBorder() {
		if(updateState == null) {
			state = new BorderWrapper(state);
		}
		else {
			System.err.println("Error in initBorder");
			System.exit(0);
		}
	}

	@Override
	public void receiveScent(double scent) {
		state.receiveScent(scent);
		
	}

	@Override
	public void receiveWind(double[] wind) {
		state.receiveWind(wind);
		
	}
	
	public final void reset() {
		state = base;
	}
	
	final void send() {
		node.sendTile();
	}
	
	final void sendBorder() {
		//TODO Strip out of final
		node.sendBorder();
	}
	
	final void setTileFacing(Direction facing) {
		node.setTileFacing(facing);
	}
	
	@Override
	public Node shift(Direction toward) {
		if(state.checkDuplicates(false)) {
			System.err.println("Error in On.shift: duplicates found");
			System.exit(0);
		}
		return state.shift(toward);
	}
	
	void transfer(BorderWrapper wrap) {
		/*
		 * Any underlying Borderwrapper should strip
		 * themselves out when updating.
		 */
		BorderWrapper wrapped;
		
		if(updateState == null) {
			wrapped = new BorderWrapper(state);
		}
		else {
			wrapped = new BorderWrapper(updateState);
		}
		wrapped.transfer(wrap);
		updateState = wrapped;
		
	}
	
	
	final void turnOff() {
		node.setState(node.getOff());
	}

	@Override
	public void update() {
		if(updateState != null) {
			state = updateState;
			updateState = null;
		}
		
		if(state.update()) {
			state = state.getWrapped();
		}
		
		if(state.checkDuplicates(false)) {
			System.err.println("Error in On.update: duplicates found");
			System.exit(0);
		}
	}
	
	/////////////////////////// Checkers ////////////////////////////////
	
	@Override
	public boolean checkInterior() {
		return state.checkInterior();
	}

	@Override
	public boolean checkBorder() {
		return state.checkBorder();
	}

	@Override
	public boolean checkOff() {
		return state.checkOff();
	}
	
	final boolean checkResting() {
		return node.checkResting();
	}
	
	//////////////////////////// Object Overrides ///////////////////////////
	
	



	

	

	

	

	
}
