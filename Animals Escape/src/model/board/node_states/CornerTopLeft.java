package model.board.node_states;


import game.Directions.Direction;
import model.board.Node;

public class CornerTopLeft extends Border{
	
	public CornerTopLeft(Node node) {
		super(node);
	}
	
	@Override
	public void shift(Direction toward) {
		if(toward == Direction.E || toward == Direction.W) {
			System.err.println("Error in Shift(): East or west selected");
			System.exit(0);
		}
		else if (toward == )
	}
}
