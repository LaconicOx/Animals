package model.screen;

import game.Directions.Direction;
import model.nodes.NodeFactory;

public abstract class ScreenState {
	
	//Class Fields
	private static final double EXTENSION = 0.5;//Extends cell beyond edge of screen to prevent flickering
	
	//Instance Fields
	protected final Screen screen;
	
	protected ScreenState(Screen screen) {
		this.screen = screen;
	}
	
	
	///////////////////// Accessors /////////////////////////
	/**
	 * 
	 * @param shift - the view's shift relative to the origin as expressed in model units.
	 * @param pixelDim - the view's dimensions in pixels.
	 * @param pixelsToUnit - unit rate for number of pixels per model unit
	 * @return boundaries[0] represents the left boundary, boundaries[1] represents the right boundary, 
	 * 	       boundaries[2] represents the top boundary, and boundaries[3] represents the bottom boundary.
	 */
	protected double[] getModelBoundaries(double[] shift, double[] pixelDim, double pixelsToUnit) {
		
		//Converts screen dimensions form pixels to model units.
		double[] dim = new double[] {pixelDim[0] / pixelsToUnit, pixelDim[1] / pixelsToUnit};//Dimensions in model units
		double[] vector = NodeFactory.getHexDim();//Vector representing dimensions of a hexagon.
		
		double[] boundaries = new double[4];
		
		//Distance from the center to the top or bottom boundaries of the panel.
		double vertical = dim[0] / 2.0 + vector[0] * EXTENSION;
		//Distance from the center to the left of right boundaries of the panel.
		double horizontal = dim[1] / 2.0 + vector[1] * EXTENSION;
		
		boundaries[0] = shift[0] - horizontal;
		boundaries[1] = shift[0] + horizontal;
		boundaries[2] = shift[1] - vertical;
		boundaries[3] = shift[1] + vertical;
		
		return boundaries;	
	}
	
	/////////////////////// Mutators ///////////////////////
	
	void dilate(double factor) {
		ScreenState state = screen.getStateDilate();
		state.dilate(factor);
		screen.setState(state);
	}
	
	void shift(Direction toward) {
		ScreenState state = screen.getStateShift();
		state.shift(toward);
		screen.setState(state);
	}
	
	abstract void update();
}
