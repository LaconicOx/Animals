package view;

import java.awt.Dimension;

import game.GameParameters;
import model.board.Node;

public class ViewParameters extends GameParameters{
	
	//Tracks how far the screen has shifted in model units relative to the model's origin.
	//Should always be the same as the player's location in model units.
	private static double[] screenShift;
	
	
	private static ViewParameters uniqueInstance = null;
	
	private ViewParameters() {
		screenShift = new double[]{0.0, 0.0};
	}
	
	public static ViewParameters getInstance() {
		if(uniqueInstance == null)
			uniqueInstance = new ViewParameters();
		return uniqueInstance;
	}
	
	///////////////////////// Accessor Methods ///////////////////////
	
	public double getDilation() {
		return super.getDilation();
	}
	
	public Dimension getPanel() {
		return super.getPanel();
	}
	
	/**
	 * 
	 * @param coords - model units
	 * @return - screen coordinations of the corner of a hex image.
	 */
	public double[] getCorner(double[] coords) {
		double[] corner = new double[2];
		
		corner[0] = modelToPixel(coords[0]) - modelToPixel(screenShift[0]) - getTileWidth() / 2.0;
		corner[1] = modelToPixel(coords[1]) - modelToPixel(screenShift[1]) - getTileHeight() / 2.0;
		
		return corner;
	}
	
	///////////////////////// Mutator Methods //////////////////////
	
	private double modelToPixel(double units) {
		return units * getTileApothem();
	}
	
	///////////////////////// Requires Recoding //////////////////////
	
	
	
	public void shift(double x, double y) {
		screenShift[0] += x;
		screenShift[1] += y;
	}
	
	public double[] getShift() {
		double dilation = getDilation();
		double[] output = {screenShift[0] * dilation, screenShift[1] * dilation};
		return output;
	}

}
