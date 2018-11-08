/**
 * 
 */
package game;

import java.awt.Dimension;

/**
 * @author dvdco
 *
 */
public class GameParameters {
	//Default constants for initializing game.
	
	private static final Dimension DEFAULT_PANEL =  new Dimension(300, 300);
	
	/*
	 * All hex image files are saved with the dimensions below. Since the image
	 * dimensions do not change, it makes sense to use them as game constant.
	 */
	
	private static final double DEFAULT_DILATION = 0.25;
	private static final double IMAGE_WIDTH = 300.0;
	private static final double IMAGE_HEIGHT = 261.0;
	private static final double IMAGE_RADIUS = IMAGE_WIDTH / 2.0;
	private static final double IMAGE_APOTHEM = (IMAGE_RADIUS / 2.0) * Math.sqrt(3.0);
	
	private static double[] screenCenter;
	private static double dilation;
	private static Dimension panel;
	
	/////////////////////////// Constructor /////////////////////////////////
	protected GameParameters() {

		dilation = DEFAULT_DILATION;
		panel = DEFAULT_PANEL;
		screenCenter = new double[]{panel.getWidth() / 2, panel.getHeight() / 2};
	}
	
	/*
	 * Note: the mutator and accessor methods should not be static because the parameter
	 * classes listen to the GUI frameworks. Consequently, instance variables must be initialized.
	 * Setting the mutator and accessor methods to static would allow them to access these classes'
	 * fields before they're initialized, potentially leading to difficult side effects.
	 */
	
	////////////////////// Accessor Methods ////////////////////////////
	protected double getTileRadius() {return dilation * IMAGE_RADIUS;}
	
	protected double getTileApothem() {return dilation * IMAGE_APOTHEM;}
	
	protected double getTileHeight() {return dilation * IMAGE_HEIGHT;}
	
	protected double getTileWidth() {return dilation * IMAGE_WIDTH;}
	
	protected Dimension getPanel() {return (Dimension)panel.clone();}
	
	protected double  getPanelHeight(){return panel.getHeight();}
	
	protected double getPanelWidth() {return panel.getWidth();}
	
	protected double getDilation() { return dilation; }
	
	protected double[] getScreenCenter() { return screenCenter.clone(); }
	
	
	
	//////////////////////// Mutator Methods //////////////////////////
	
	public void setDilation(double factor) {dilation *= factor;}
	
}
