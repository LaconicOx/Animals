package model.screen;


import java.util.Iterator;

import model.nodes.ConcreteNode;

public class ScreenNormal extends ScreenState{
	
	public ScreenNormal(Screen screen) {
		super(screen);
	}
	
	@Override
	public final void update() {
		Iterator<ConcreteNode> screenIt = screen.getScreenIterator();
		
		while(screenIt.hasNext()) {
			screenIt.next().update();
		}
	}

	

}
