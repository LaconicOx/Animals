package model.screen;

import java.util.HashSet;
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
	final Node shift(Direction toward) {
		
		//Transfer border wrapper to the toward node.
		Node neighNode = state.getNeighbor(toward);
		neighNode.transfer(this);
		
		//Changes the current node's internal state based on the state of the node
		//in the direction opposite of the toward node.
		Direction opposite = Direction.getOpposite(toward);
		
		//If the opposite node is INTERIOR, this border wrapper should be stripped off.
		if(pattern.checkInterior(opposite)) {
			
		}
		//If the opposite node is OFF, this node should be turned off.
		else if(pattern.checkOff(opposite)) {
			state.turnOff();
		}
		//If the opposite node is BORDER, the node in that direction will transfer its state to this node,
		//this wrapper must be stripped off.
		else if(pattern.checkBorder(opposite)) {
		}
		else {
			System.err.println("Error in shift: unhandled state");
			System.exit(0);
		}
		
		stripFlag = true;
		return neighNode;
	}//End of Shift
	
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
