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
	private static final double DEFAULT_SCALE =  0.25;
	private static final Dimension DEFAULT_PANEL =  new Dimension(500, 500);
	
	/*
	 * All hex image files are saved with the dimensions below. Since the image
	 * dimensions do not change, it makes sense to use them as game constant.
	 */
	
	private static final double IMAGE_WIDTH = 300.0;
	private static final double IMAGE_HEIGHT = 261.0;
	private static final double IMAGE_RADIUS = IMAGE_WIDTH / 2.0;
	private static final double IMAGE_APOTHEM = (IMAGE_RADIUS / 2.0) * Math.sqrt(3.0);
	
	private static double scale;
	private static Dimension panel;
	
	/////////////////////////// Constructor /////////////////////////////////
	protected GameParameters() {
		scale = DEFAULT_SCALE;
		panel = DEFAULT_PANEL;
	}
	
	/*
	 * Note: the mutator and accessor methods should not be static because the parameter
	 * classes listen to the GUI frameworks. Consequently, instance variables must be initialized.
	 * Setting the mutator and accessor methods to static would allow them to access these classes'
	 * fields before they're initialized, potentially leading to difficult side effects.
	 */
	
	////////////////////// Accessor Methods ////////////////////////////
	public double getTileRadius() {return scale * IMAGE_RADIUS;}
	
	public double getTileApothem() {return scale * IMAGE_APOTHEM;}
	
	public Dimension getPanel() {return (Dimension)panel.clone();}
	
	public double  getPanelHeight(){return panel.getHeight();}
	
	public double getPanelWidth() {return panel.getWidth();}
	
	public double getScale() {return scale;}
	
	protected double getImageHeight() {return IMAGE_HEIGHT;}
	
	protected double getImageWidth() {return IMAGE_WIDTH;}
	//////////////////////// Mutator Methods //////////////////////////
	
	public void setScale(double factor) {scale *= factor;}
	
}
