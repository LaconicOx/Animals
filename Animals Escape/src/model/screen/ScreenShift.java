package model.screen;


import game.Directions.Direction;

public class ScreenShift extends ScreenState{
	
	private Direction toward;
	
	public ScreenShift(Screen screen) {
		super(screen);
		this.toward = null;
	}
	
	////////////////////////// Mutators ///////////////////////////////
	
	@Override
	final void shift(Direction toward) {
		//TODO
	}//End of Shift
	
	@Override
	public final void update() {
		//TODO
	}
}
