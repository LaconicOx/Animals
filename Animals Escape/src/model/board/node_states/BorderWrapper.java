package model.board.node_states;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import game.Directions.Direction;
import model.board.Node;


public class BorderWrapper extends OnStates{
	
	//Class Fields
	private enum States {INTERIOR, BORDER, OFF};
	private static final double WIND_FORCE = 5.0;
	private static final double WIND_PROB = 0.001;
	private static final HashSet<Pattern> PERMITTED;
		
	static {
		//The arrays store the direction in the following indexes {NE, N, NW, SW, S, SE}
		PERMITTED = new HashSet<>();
		//Permitted patterns for the top-left corner.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.OFF, States.OFF, States.OFF, States.BORDER, States.INTERIOR}));//Top-left corner with a border node at NE and an active node at SE.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.OFF, States.OFF, States.OFF, States.BORDER, States.BORDER}));//Top-left corner with a border node at NE and no active node at SE.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.OFF, States.BORDER, States.INTERIOR}));//Top-left corner with no border node at NE and an active node at SE.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.OFF, States.BORDER, States.BORDER}));//Top-left corner with no border node at NE and no active node at SE.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.INTERIOR}));//Top-left middle corner
		
		//Permitted patterns for the top-right corner
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.INTERIOR, States.BORDER, States.OFF}));//Top-right corner with no border node at NW and an active node at SW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.BORDER, States.BORDER, States.OFF}));//Top-right corner with no border node at NW and no active node at SW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.BORDER, States.OFF}));//Top-right corner with a border node at NW and an active node at SW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.BORDER, States.OFF}));//Top-right corner with a border node at NW and no active node at SW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.INTERIOR, States.BORDER}));//Top-right middle corner
		
		//Permitted patterns for the bottom-left corner
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.OFF, States.OFF}));//Bottom-left corner with no border node at SE and an active node at NE.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.BORDER, States.OFF, States.OFF, States.OFF, States.OFF}));//Bottom-left corner with no border node at SE and no active node at NE.
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.OFF, States.BORDER}));//Bottom-left corner with a border node at SE and an active node at NE.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.BORDER, States.OFF, States.OFF, States.OFF, States.BORDER}));//Bottom-left corner with a border node at SE and no active node at NE.
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.BORDER}));//Bottom-left middle corner.
		
		//Permitted patterns for the bottom-right corner
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.INTERIOR, States.OFF, States.OFF, States.OFF}));//Bottom-right corner with no border node at SW and an active node at NW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.BORDER, States.OFF, States.OFF, States.OFF}));//Bottom-right corner with no border node at SW and no active node at NW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.INTERIOR, States.BORDER, States.OFF, States.OFF}));//Bottom-right corner with a border node at SW and an active node at NW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.BORDER, States.BORDER, States.OFF, States.OFF}));//Bottom-right corner with a border node at SW and no active node at NW.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.INTERIOR, States.INTERIOR, States.BORDER, States.OFF, States.OFF}));//Bottom-right middle corner.
	
		//Permitted patterns for the top boundary
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.BORDER}));//Upper top boundary
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.OFF, States.BORDER, States.INTERIOR, States.INTERIOR, States.INTERIOR}));//Lower top boundary
		
		//Permitted patterns for the bottom boundary
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.OFF}));//Lower bottom boundary
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.INTERIOR, States.INTERIOR, States.BORDER, States.OFF, States.BORDER}));//Lower bottom boundary
		
		//Permitted pattern for the right boundary
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.INTERIOR, States.INTERIOR, States.BORDER, States.OFF}));
		
		//Permitted pattern for the left boundary
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.BORDER, States.INTERIOR}));
	}
	
	//Instance Fields
	private Pattern pattern;
	private Pattern updatePattern;
	private boolean stripFlag;
	
	//////////////////////////Constructor and Initializers ////////////////////////////
	
	public BorderWrapper(Active active) {
		super(active);
		pattern = null;
		updatePattern = null;
		stripFlag = false;
	}
	
	public void initPattern() {
		if(pattern == null) {
			Direction[] directions = Direction.getNodeDirections();
			Pattern candidate = new Pattern();
			
			System.out.println("******************* " + active + "*************************");
			
			Node neighbor;
			for(int i = 0; i < 6; i++) {
				neighbor = active.getNeighbor(directions[i]);
				
				System.out.println(neighbor);
				System.out.println("Acitve: " + neighbor.checkActive() + ", Border: " + neighbor.checkBorder() + ", Off: " + neighbor.checkOff());
				
				if(neighbor.checkActive()) {
					candidate.set(directions[i], States.INTERIOR);
				}
				else if(neighbor.checkBorder()) {
					candidate.set(directions[i], States.BORDER);
				}
				else if(neighbor.checkOff()) {
					candidate.set(directions[i], States.OFF);
				}
				else {
					System.err.println("Error in Border.initState: unhandled state.");
					System.exit(0);
				}
			}
			
			System.out.println("******************* " + candidate + "*************************");
			
			if(PERMITTED.contains(candidate)) {
				pattern = candidate;
			}
			else {
				StringBuilder error = new StringBuilder();
				error.append("Error in Border.initState: " + active + " has unrecognized pattern of " + candidate);
				System.err.println(error.toString());
				System.exit(0);
			}
		}
		
	}//End of initPattern()
	
	/////////////////////////// Accessors //////////////////////////////////////////
	
	final Pattern getPattern(){
		return pattern;
	}
	
	//////////////////////////// Mutators /////////////////////////////////////

	private final void genWind() {
		
		double prob = ThreadLocalRandom.current().nextDouble(1.0);
		//TODO: Rewrite genWind to send a single wind vector.
		if (prob <= WIND_PROB) {
			Iterator<Direction> actIt = pattern.getActiveIterator();
			while(actIt.hasNext()) {
				Direction next = actIt.next();
				active.receiveWind(next.scaledVector(WIND_FORCE));
			}
		}
	}
	
	@Override
	public final Node shift(Direction toward) {
		
		//Transfer border wrapper to the toward node.
		Node neighNode = active.getNeighbor(toward);
		On neighOn = neighNode.getOn();
		neighOn.transfer(this);
		neighNode.setState(neighOn);
		
		//Changes the current node's internal state based on the state of the node
		//in the direction opposite of the toward node.
		States oppState = pattern.getState(Direction.getOpposite(toward));
		
		//If the opposite node is INSIDE, this border wrapper should be stripped off.
		if(oppState == States.INTERIOR) {
			
		}
		//If the opposite node is OFF, this node should be turned off.
		else if(oppState == States.OFF) {
			active.turnOff();
		}
		//If the opposite node is BORDER, the node in that direction will transfer its state to this node,
		//this wrapper must be stripped off.
		else if(oppState == States.BORDER) {
		}
		else {
			System.err.println("Error in shift: unhandled state");
			System.exit(0);
		}
		
		stripFlag = true;
		return neighNode;
	}//End of Shift
	
	final void transfer(BorderWrapper wrap) {
		updatePattern = wrap.getPattern();
	}
	
	@Override
	public final boolean update() {
		
		if(stripFlag) {
			return true;
		}
		else {
			//Changes pattern if transfered.
			if(updatePattern != null) {
				pattern = updatePattern;
				updatePattern = null;
			}
			initPattern();
			genWind();
			
		if(active.update())
			active = active.getWrapped();
		//active.sendBorder();
			return false;
		}
		
	}
	
	///////////////////////////////// Checkers /////////////////////////////////////
	
	public boolean checkDuplicates(boolean previous) {
		//TODO Strip out of final version
		if(previous) {
			return true;
		}
		else {
			return active.checkDuplicates(true);
		}
	}
	
	@Override
	public final boolean checkInterior() {
		return false;
	}
	
	@Override
	public final boolean checkBorder() {
		return true;
	}
	
	//////////////////////////////////Object Overrides ///////////////////////////////
	
	@Override
	public final String toString() {
		return active.toString() + " with a border wrapper";
	}
	
	///////////////////////////////// Inner Class ///////////////////////////////////
	
	private static class Pattern{
		
		private States[] pattern;
		private HashSet<Direction> actives;
		
		//////////////// Constructors //////////////////////
		Pattern(){
			pattern = new States[6];
			
			actives = new HashSet<>();
			Direction[] directions = Direction.getNodeDirections();
			for(int i = 0; i < 6; i++) {
				if(pattern[i] == States.INTERIOR) {
					actives.add(directions[i]);
				}
			}
		}
		
		Pattern(States[] pattern) {
			this.pattern = pattern;
			actives = new HashSet<>();
		}
		
		/////////////////// Accessors ///////////////////////
		
		Iterator<Direction> getActiveIterator(){
			return actives.iterator();
		}
		
		States getState(Direction dir) {
			return pattern[dirToIndex(dir)];
		}
		
		States[] getArray() {
			return pattern;
		}
		
		///////////////// Mutators //////////////////////////
		void set(Direction dir, States st){
			int index = dirToIndex(dir);
			if(pattern[index] == null) {
				if (st == States.INTERIOR) {
					actives.add(dir);
				}
				pattern[dirToIndex(dir)] = st;
			}
			else {
				System.err.println("Patterns are permanent");
				System.exit(0);
			}
			
		}
		
		
		private int dirToIndex(Direction dir) {
			int index = -1;
			switch(dir) {
			case NE: index = 0;
				break;
			case N: index = 1;
				break;
			case NW: index = 2;
				break;
			case SW: index = 3;
				break;
			case S: index = 4;
				break;
			case SE: index = 5;
				break;
			case E:
			case W: {
				System.err.println("Error in Pattern: unrecognized direction");
				System.exit(0);
				}
				break;
			}
			return index;
		}
		
		@Override
		public int hashCode(){
			return Objects.hash(pattern[0], pattern[1], pattern[2], pattern[3], pattern[4], pattern[5]);
		}
		
		@Override
		public String toString() {
			StringBuilder output = new StringBuilder();
			for(States st : pattern) {
				output.append(st + ", ");
			}
			return output.toString();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj.getClass() != Pattern.class)
				return false;
			Pattern other = (Pattern) obj;
			States[] otherArray = other.getArray();
			for(int i = 0; i < 6; i++) {
				if(pattern[i] != otherArray[i])
					return false;
			}
			return true;
		}
	}
}
