package model.screen;


import java.util.Iterator;

import model.board.Node;

public class ScreenNormal extends ScreenState{
	
	public ScreenNormal(Screen screen) {
		super(screen);
	}
	
	@Override
	public final void update() {
		Iterator<Node> screenIt = screen.getScreenIterator();
		
		while(screenIt.hasNext()) {
			screenIt.next().update();
		}
	}

	

}
