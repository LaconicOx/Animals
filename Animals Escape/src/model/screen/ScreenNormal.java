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
			if(node.update()) {
				screen.remove(node);
			}
		}
	}

	

}
