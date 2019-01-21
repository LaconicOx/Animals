package model.screen;


import java.util.Iterator;

import model.nodes.Node;

public class ScreenNormal extends ScreenState{
	
	public ScreenNormal(Screen screen) {
		super(screen);
	}
	
	@Override
	public final void update() {
		Iterator<Node> screenIt = screen.getScreenIterator();
		
		while(screenIt.hasNext()) {
			//Updates and checks whether node must be removed.
			Node node = screenIt.next();
			boolean flag = !node.update();
			if(flag) {
				System.out.println("flagged");
			}
			
			if(flag) {
				screenIt.remove();
			}
		}
	}

	

}
