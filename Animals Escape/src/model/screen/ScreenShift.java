package model.screen;

import java.util.HashSet;
import java.util.Iterator;

import game.Directions.Direction;
import model.board.Node;

public class ScreenShift extends ScreenState{
	
	private Direction toward;
	
	public ScreenShift(Screen screen) {
		super(screen);
		this.toward = null;
	}
	
	////////////////////////// Mutators ///////////////////////////////
	
	@Override
	public void shift(Direction toward) {
		this.toward = toward;
	}
	
	@Override
	public final void update() {
		Iterator<Node> borderIt = screen.getBorderIterator();
		HashSet<Node> altered = new HashSet<>();//Altered nodes must be resubmitted to Screen to change their position in the treeset.
		
		//Iterates through the border nodes so each performs its shift operations.
		Node current;
		while(borderIt.hasNext()) {
			current = borderIt.next();
			altered.add(current.shift(toward));
			altered.add(current);
		}
		screen.add(altered);
		screen.reposition();
		
		//Returns state to normal
		screen.setState(screen.getStateNormal());
	}
}
