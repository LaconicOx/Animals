package model.board.node_states;

import java.util.ArrayList;

import game.Directions.Direction;
import model.board.Node;

public class WindWrapper extends OnStates{
	
	//Class Fields
	private static final double[] NO_WIND = {0,0};
	private static final double WIND_CUTOFF = 0.1;
	private static final double NO_SCENT = 0.0;
	private static final double SCENT_CUTOFF = 0.1;
	
	//Instance Fields
	private Direction facing;
	private final double interference;
	private double scent;
	private ArrayList<Double> scentInputs;
	private double[] wind;
	private ArrayList<double[]> windInputs;//Stores wind vectors received from neighboring nodes.
	
	
	
	
	public WindWrapper(Active active, double interference) {
		super(active);
		scent = 0;
		scentInputs = new ArrayList<>();
		scentInputs.add(NO_SCENT);
		windInputs = new ArrayList<>();
		windInputs.add(NO_WIND);
		this.interference = interference;
		facing = Direction.NE;
	}
	
	///////////////////// Accessors //////////////////////
	

	///////////////////// Mutators ///////////////////////
	
	/**
	 * Calculates the node's prevailing scent
	 * Returns TRUE if the scent is greater than the cutoff,
	 * FALSE otherwise.
	 */
	private final void findScent() {
		double canScent = 0;
		
		//Sums inputs
		for(double input : scentInputs) {
			canScent += input;
		}
		
		//Clears scent input
		scentInputs.clear();
		scentInputs.add(NO_SCENT);
		
		canScent *= interference;
		
		//Gate to cut off tailing calculations.
		if(canScent < SCENT_CUTOFF) {
			scent = 0;
		}
		else {
			scent = canScent;
		}
	}
	
	/**
	 * Calculates the node's prevailing wind
	 * Returns TRUE if the wind is greater than the cutoff,
	 * FALSE otherwise.
	 */
	private final void findWind() {
		double[] canWind = {0, 0};
		
		//Sums vectors to calculate wind's direction and magnitude.
		for (double[] vector : windInputs) {
			canWind[0] += vector[0];
			canWind[1] += vector[1];
		}
		
		//Resets wind inputs
		windInputs.clear();
		windInputs.add(NO_WIND);
		
		//Scalar multiplication to represent wind interference.
		canWind[0] *= interference;
		canWind[1] *= interference;
		
		double magnitude = Math.sqrt(Math.pow(canWind[0], 2.0) + Math.pow(canWind[1], 2.0));
		
		//Gate to cut off tailing calculations.
		if(magnitude < WIND_CUTOFF) {
			wind = NO_WIND;
		}
		else {
			wind = canWind;
		}
	}
	
	private final void blowing() {
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
		
		//Wind and Scent are distributed by a simplified normal distribution.
		double bulk = 0.68;
		double tail = 0.21;
		
		Node mainNode = active.getNeighbor(main);
		mainNode.receiveWind(new double[] {wind[0] * bulk, wind[1] * bulk});
		mainNode.receiveScent(scent * bulk);
		
		Node ccwNode = active.getNeighbor(counterclockwise);
		ccwNode.receiveWind(new double[] {wind[0] * tail, wind[1] * tail});
		ccwNode.receiveScent(scent * tail);
		
		
		Node cwNode = active.getNeighbor(clockwise);
		cwNode.receiveWind(new double[] {wind[0] * tail, wind[1] * tail});
		cwNode.receiveScent(scent * tail);
		
		//Handles animation
		facing = main;
		active.setTileFacing(facing);
		active.advance();
	}
	
	@Override
	public final void receiveScent(double scent) {
		scentInputs.add(scent);
	}
	
	@Override
	public final void receiveWind(double[] wind) {
		windInputs.add(wind);
	}
	
	
	
	@Override
	public final boolean update() {
		
		boolean finished = false;
		
		findScent();
		findWind();
		
		//Wrapper should remain if there is wind.
		if((wind[0] != NO_WIND[0]) && (wind[1] != NO_WIND[1])) {
			blowing();
		}
		//Wrapper should remain if the animation is not resting
		else if(active.checkResting()) {
			active.advance();
		}
		//Wrapper should remain if there is scent.
		else if(scent != NO_SCENT){
			finished = false;
		}
		//Otherwise, wrapper has finished.
		else {
			finished = true;
		}
		
		if(active.update()) {
			active = active.getWrapped();
		}
		
		return finished;
	}

	////////////////////////// Object Overrides //////////////////////////////////////////
	
	@Override
	public final String toString() {
		return active.toString() + " with a wind Wrapper";
	}
}
