package model.screen;



import model.nodes.NodeFactory;
import view.SwingFacade;
import view.ViewInterface;

public class Test {
	
	
	
	
	
	
	
	public static void main(String[] args) {
		ViewInterface view = new SwingFacade();
		NodeFactory nf = NodeFactory.getInstance(view);
		CurrentScreen screen = new CurrentScreen(view);
		screen.update();
		screen.update();
		//screen.displayRows();
		
		

		
		
		
		
	}

}
