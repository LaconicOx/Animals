package model.screen;

import java.util.Iterator;

import game.Directions.Direction;
import model.nodes.Node;
import model.nodes.NodeFactory;
import view.SwingFacade;
import view.ViewInterface;

public class Test {

	public static void main(String[] args) {
		ViewInterface view = new SwingFacade();
		NodeFactory nf = NodeFactory.getInstance(view);
		Screen screen = new Screen(view);
		screen.update();
		screen.update();
		//screen.displayRows();
		
		
		Iterator<Node> borderIt = screen.getScreenIterator();
		
		
		while(borderIt.hasNext()) {
			System.out.println(borderIt.next());
			borderIt.remove();
		}
		
		borderIt = screen.getScreenIterator();
		
		int i = 0;
		while(borderIt.hasNext()) {
			
			i++;
		}
		System.out.println(i);
	}

}
