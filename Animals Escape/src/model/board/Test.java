package model.board;

import model.ModelKey;
import model.board.node_states.On;
import view.SwingFacade;

public class Test {

	public static void main(String[] args) {
		TotalBoard tb = TotalBoard.getInstance(new SwingFacade());
		
		Node n = TotalBoard.getNode(new ModelKey(new int[] {0,0}), false);
		On on = n.getOn();
		on.initBorder();
		n.initState(on);
		System.out.println(n.checkBorder());
	}

}
