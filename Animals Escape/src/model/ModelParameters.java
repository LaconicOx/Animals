package model;

import game.GameParameters;
import model.node.Node;

public class ModelParameters extends GameParameters{

	private static int horzSteps, verSteps;
	private static double[] screenShift = {0,0};//Tracks the shift of the screen relative to its intialization.
	private static double[] screenCenter = {0,0};
	private static final int EXTENSION = 1;// Constant for extending the screen for smooth animation.
	private static ModelParameters uniqueInstance = null;
	
	////////////////////////////// Constructor ////////////////////////////////
	
	private ModelParameters(){
		super();
		screenCenter[0] = getPanelWidth() / 2;
		screenCenter[1] = getPanelHeight() / 2;
		createSteps();
	}
	
	public static ModelParameters getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ModelParameters();
		return uniqueInstance;
	}
	
	////////////////////////// Helper Method ////////////////////////////////
	private void createSteps(){
		/*
		 * Because the tiles defined as regular hexagons, we can use the properties of
		 * 30-60-90 triangles to convert to node units.
		 */
		double apothem = getTileApothem();
		verSteps = (int)Math.ceil((getPanelHeight()/2.0 - apothem)/(apothem*2)) + EXTENSION;
		horzSteps = (int)Math.ceil((getPanelWidth()/2.0)/(getTileRadius()*1.5)) + EXTENSION;
	}
	
	///////////////////////////// Accessor Methods ////////////////////////////////
	public int getHorzSetps() {return horzSteps;}
	
	public int getVerSteps() {return verSteps;}
	
	public double[] getScreenCenter() {return screenCenter.clone();}
	
	public double getScreenX() {return screenCenter[0];}//x component of the screen center
	
	public double getScreenY() {return screenCenter[1];}//y-component of the screen center.
	
	public double getShiftX() {return screenShift[0] * getScale();}
	
	public double getShiftY() {return screenShift[1] * getScale();}
	
	public double getExtension() { return getTileRadius()* 2 * EXTENSION; };
	
	public double getNodeCellX(Node n) {
		return (getScreenX() + getShiftX())+ n.getX() * 1.5 * getTileRadius();
	}
	
	public double getNodeCellY(Node n) {
		return (getScreenY() + getShiftY())- (n.getY() * getTileApothem());
		}
	/////////////////////// Mutator Methods //////////////////////
	
	
	void shift(double x, double y) {
		screenShift[0] += x;
		screenShift[1] += y;
	}
}
