package model;

import game.Directions.Direction;
import model.Pattern.States;

public class Test {
	
	public static void main(String[] args) {
		//Pattern correct = new Pattern(new States[] {States.BORDER, States.OFF, States.OFF, States.OFF, States.BORDER, States.INTERIOR});
		Pattern.Builder build = Pattern.Builder.getInstance();
		build.border(Direction.NE);
		build.off(Direction.N);
		build.off(Direction.NW);
		build.off(Direction.SW);
		build.border(Direction.S);
		build.interior(Direction.SE);
		
		Pattern incorrect = build.build();
		
		//System.out.println(correct.equals(incorrect));
		System.out.println(incorrect.equals(incorrect));
	}
	
	
}

