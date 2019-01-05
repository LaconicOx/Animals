package model.board.node_states;

import model.board.Node;

public abstract class Border implements NodeState{
	
	//Instance Fields
	protected final Node node;
	
	protected Border(Node node) {
		this.node = node;
	}
}
