package model.cell;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import model.ModelUtility.Edge;
import model.node.Node;
import model.node.TotalBoard;

public class CellFactory {
	TotalBoard total = TotalBoard.getInstance();
	
	public CellFactory() {
		
	}
	
	
	public TreeSet<Cell> screenInit() {
		/*
		 * Helper method that initializes the screen and the boundary sets.
		 */
		//Screen initialization.
		TreeSet<Cell> screen = new TreeSet<>();
		HashSet<Node> total =  TotalBoard.getAllNodes();
		Iterator<Node> totalIt = total.iterator();
		while (totalIt.hasNext()) {
			screen.add(getCellInstance(totalIt.next()));
		}

		return screen;
	}
	
	public Cell getBorderInstance(Cell c, Edge side) {
		Cell output = null;
		switch(side) {
			case TOP: output = new Top(c);
				break;
			case BOTTOM: output = new Bottom(c);
				break;
			case RIGHT: output = new Right(c);
				break;
			case LEFT: output = new Left(c);
				break;
		}
		return output;
	}
	
	public static Cell getCellInstance(Node n) {
		return new ConcreteCell(n);
	}
}
