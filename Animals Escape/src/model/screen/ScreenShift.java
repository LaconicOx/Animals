package model.screen;


import java.util.Iterator;

import game.Directions.Direction;
import model.nodes.Node;

public class ScreenShift extends ScreenState{
	
	private Direction toward;
	
	public ScreenShift(Screen screen) {
		super(screen);
		this.toward = null;
	}
	
	////////////////////////// Mutators ///////////////////////////////
	
	@Override
	final void shift(Direction toward) {
		this.toward = toward;
	}//End of Shift
	
	@Override
	public final void update() {
		Iterator<Node> border = screen.getShiftIterator(toward);
		
		while(border.hasNext()) {
			Node current = border.next();
			
			//Transfers current node's border to it's neighbor.
			Node neighbor = current.getNeighbor(toward);
			if(neighbor.checkOff()) {
				neighbor.transfer(current);
				screen.add(neighbor);
			}
			else {
				neighbor.transfer(current);
			}
			
			
			//Changes current node's internal state based on the node in the opposite direction.
			Direction opposite = Direction.getOpposite(toward);
			if(current.checkInterior(opposite)) {
				current.setInterior();
			}
			else if(current.checkOff(opposite)) {
				current.setOff();
			}
			else if(current.checkBorder(opposite)) {
				//Do nothing. State will be transfered by the node in the opposite direction.
			}
			else {
				//TODO error code.
			}
		}
		
		//Change screen's state back to normal.
		screen.setState(screen.getStateNormal());
	}
}
