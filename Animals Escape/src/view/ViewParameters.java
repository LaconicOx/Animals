package view;

import game.GameParameters;

public class ViewParameters extends GameParameters{
	
	private static ViewParameters uniqueInstance = null;
	
	private ViewParameters() {
		
	}
	
	public static ViewParameters getInstance() {
		if(uniqueInstance == null)
			uniqueInstance = new ViewParameters();
		return uniqueInstance;
	}
	
	///////////////////////// Accessor Methods ///////////////////////
	
	public double getCornerX(double x) {
		return x - getScale() * getImageWidth() / 2.0;
	}
	
	public double getCornerY(double y) {
		return y - getScale() * getImageHeight()/2.0;
	}
}
