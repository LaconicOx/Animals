package model.screen;

import game.Directions.Direction;

public abstract class ScreenState {
	
	protected final Screen screen;
	
	protected ScreenState(Screen screen) {
		this.screen = screen;
	}
	
	public abstract void update();
	
	public void shift(Direction toward) {
		ScreenState state = screen.getStateShift();
		state.shift(toward);
		screen.setState(state);
	}
}
