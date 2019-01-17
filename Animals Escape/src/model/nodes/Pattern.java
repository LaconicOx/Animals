package model.nodes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import game.Directions.Direction;

public class Pattern {
	
	//Class Fields
	public enum States {INTERIOR, BORDER, OFF};
	private static final HashSet<Pattern> PERMITTED;	
	static {
		//The arrays store the direction in the following indexes {NE, N, NW, SW, S, SE}
		PERMITTED = new HashSet<>();
		//Permitted patterns for the top-left corner.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.OFF, States.OFF, States.OFF, States.BORDER, States.INTERIOR}, false));//Top-left corner with a border node at NE and an active node at SE.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.OFF, States.OFF, States.OFF, States.BORDER, States.BORDER}, false));//Top-left corner with a border node at NE and no active node at SE.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.OFF, States.BORDER, States.INTERIOR}, false));//Top-left corner with no border node at NE and an active node at SE.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.OFF, States.BORDER, States.BORDER}, false));//Top-left corner with no border node at NE and no active node at SE.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.INTERIOR}, false));//Top-left middle corner
		
		//Permitted patterns for the top-right corner
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.INTERIOR, States.BORDER, States.OFF}, false));//Top-right corner with no border node at NW and an active node at SW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.BORDER, States.BORDER, States.OFF}, false));//Top-right corner with no border node at NW and no active node at SW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.BORDER, States.OFF}, false));//Top-right corner with a border node at NW and an active node at SW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.BORDER, States.OFF}, false));//Top-right corner with a border node at NW and no active node at SW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.INTERIOR, States.BORDER}, false));//Top-right middle corner
		
		//Permitted patterns for the bottom-left corner
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.OFF, States.OFF}, false));//Bottom-left corner with no border node at SE and an active node at NE.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.BORDER, States.OFF, States.OFF, States.OFF, States.OFF}, false));//Bottom-left corner with no border node at SE and no active node at NE.
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.OFF, States.BORDER}, false));//Bottom-left corner with a border node at SE and an active node at NE.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.BORDER, States.OFF, States.OFF, States.OFF, States.BORDER}, false));//Bottom-left corner with a border node at SE and no active node at NE.
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.BORDER}, false));//Bottom-left middle corner.
		
		//Permitted patterns for the bottom-right corner
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.INTERIOR, States.OFF, States.OFF, States.OFF}, false));//Bottom-right corner with no border node at SW and an active node at NW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.BORDER, States.OFF, States.OFF, States.OFF}, false));//Bottom-right corner with no border node at SW and no active node at NW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.INTERIOR, States.BORDER, States.OFF, States.OFF}, false));//Bottom-right corner with a border node at SW and an active node at NW.
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.BORDER, States.BORDER, States.OFF, States.OFF}, false));//Bottom-right corner with a border node at SW and no active node at NW.
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.INTERIOR, States.INTERIOR, States.BORDER, States.OFF, States.OFF}, false));//Bottom-right middle corner.
	
		//Permitted patterns for the top boundary
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.OFF, States.OFF, States.BORDER, States.INTERIOR, States.BORDER}, false));//Upper top boundary
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.OFF, States.BORDER, States.INTERIOR, States.INTERIOR, States.INTERIOR}, false));//Lower top boundary
		
		//Permitted patterns for the bottom boundary
		PERMITTED.add(new Pattern(new States[] {States.BORDER, States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.OFF}, false));//Lower bottom boundary
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.INTERIOR, States.INTERIOR, States.BORDER, States.OFF, States.BORDER}, false));//Lower bottom boundary
		
		//Permitted pattern for the right boundary
		PERMITTED.add(new Pattern(new States[] {States.OFF, States.BORDER, States.INTERIOR, States.INTERIOR, States.BORDER, States.OFF}, false));
		
		//Permitted pattern for the left boundary
		PERMITTED.add(new Pattern(new States[] {States.INTERIOR, States.BORDER, States.OFF, States.OFF, States.BORDER, States.INTERIOR}, false));
	}
	
	//Instance Fields
	private final HashMap<Direction, States> pattern;
	private final HashSet<Direction> actives;
	
	/**
	 * Private constructor used for initializing PERMITTED.
	 * @param pattern  - array storing states with directions corresponding to indexes as {NE, N, NW, SW, S, SE}
	 * @param build - Flag for distinguishing between building objects vs initializing PERMITTED.
	 */
	public Pattern(States[] candidate, boolean build) {
		this.pattern = new HashMap<>();
		actives = new HashSet<>();
		
		Direction[] directions = Direction.getNodeDirections();
		for(int i = 0; i < 6; i++) {
			if(candidate[i] == null) {
				System.err.println("Error in Pattern.arrayToMap");
				System.exit(0);
			}
			else if(candidate[i] == States.INTERIOR) {
				pattern.put(directions[i],candidate[i]);
				actives.add(directions[i]);
			}
			else {
				pattern.put(directions[i],candidate[i]);
			}
		}
		if(build) {
			if(!PERMITTED.contains(this)) {
				System.err.println("Error in Pattern: pattern not permitted");
			}
		}
	}
	
	/////////////////// Accessors ///////////////////////
	
	
	public final Direction getRandomDir() {
		Direction[] act = actives.toArray(new Direction[actives.size()]);
		return act[ThreadLocalRandom.current().nextInt(act.length)];
	}
	
	///////////////// Mutators //////////////////////////
	
	
	
	//////////////////////// Checkers ////////////////////
	
	public final boolean checkBorder(Direction dir) {
		if(pattern.get(dir) == States.BORDER)
			return true;
		else
			return false;
	}
	
	public final boolean checkInterior(Direction dir) {
		if(pattern.get(dir) == States.INTERIOR)
			return true;
		else
			return false;
	}
	
	public final boolean checkOff(Direction dir) {
		if(pattern.get(dir) == States.OFF)
			return true;
		else
			return false;
	}
	
	////////////////////// Object Overrides /////////////////
	
	@Override
	public final int hashCode(){
		return Objects.hash(pattern.get(Direction.NE), pattern.get(Direction.N), pattern.get(Direction.NW), pattern.get(Direction.SW), pattern.get(Direction.S), pattern.get(Direction.SE));
	}
	
	@Override
	public final String toString() {
		StringBuilder output = new StringBuilder();
		pattern.forEach((key, entry) -> output.append(entry + ", "));
		return output.toString();
	}
	
	@Override
	public final boolean equals(Object obj) {
		if(obj.getClass() != Pattern.class)
			return false;
		Pattern other = (Pattern) obj;
		
		Direction[] directions = Direction.getNodeDirections();
		for(Direction dir : directions) {
			if(pattern.get(dir) == States.BORDER && !other.checkBorder(dir)) {
				return false;
			}
			else if(pattern.get(dir) == States.INTERIOR && !other.checkInterior(dir)) {
				return false;
			}
			else if(pattern.get(dir) == States.OFF && !other.checkOff(dir)) {
				return false;
			}
			else {
				continue;
			}
				
		}
		return true;
	}
	
	///////////////////////// Inner Class ////////////////////////////////////////////////
	
	public static class Builder{
		private States[] candidate;
		private static Builder build = null;
		
		private  Builder() {
			candidate = new States[] {null, null, null, null, null, null};
		}
		
		public final Pattern build() {
			build = null;
			return new Pattern(candidate, true);
		}
		
		public final static Builder getInstance() {
			if(build == null) {
				build = new Builder();
			}
			else {
				System.err.println("Error in Pattern.Builder.init");
				System.exit(0);
			}
			return build;
		}
		
		private int dirToIndex(Direction dir) {
			int output = -1;
			switch(dir) {
			case NE: output = 0;
				break;
			case N: output = 1;
				break;
			case NW: output = 2;
				break;
			case SW: output = 3;
				break;
			case S: output = 4;
				break;
			case SE: output = 5;
				break;
			case E:
			case W:{
				System.err.println("Error in dirToIndex");
				System.exit(0);
				}
			}
			return output;
		}
		
		public final void border(Direction dir){
			candidate[dirToIndex(dir)] = States.BORDER;
		}
		
		public final void interior(Direction dir) {
			candidate[dirToIndex(dir)] = States.INTERIOR;
		}
		
		public final void off(Direction dir) {
			candidate[dirToIndex(dir)] = States.OFF;
		}
	}
}
