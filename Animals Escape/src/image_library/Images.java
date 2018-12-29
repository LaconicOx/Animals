package image_library;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

/**
 * @author dvdco
 * Top level class for images.
 */
public abstract class Images {
	
	private final double[] modelDimensions;
	
	public Images(double[] modelDimensions) {
		this.modelDimensions = modelDimensions;
	}
	
	//////////////////////////// Helper Methods /////////////////////////////////
	/**
	 * Helper method for getTransformation
	 * Calculates the corner of a hex tile in order to draw from the corner of a png file.
	 * @param coords - model units
	 * @return - screen coordinations of the corner of a hex image.
	 */
	private double[] findCorner() {
		double[] corner = new double[2];
		double[] coords = getCoords();
		double[] panelDim = getScreenDim();
		double[] imageDimensions = getImageDim();
		double scale = getScale();
		double[] shift = getShift();
		double[] dilation = findDilation();
		
		//Divides panel's dimensions to find screen's center.
		double[] screenCenter = new double[]{panelDim[0] / 2.0, panelDim[1] / 2.0};
		//Divides tile's dimensions to find shift to image's corner.
		double[] tileCorner = new double[] {(imageDimensions[0] * dilation[0]) / 2.0, (imageDimensions[1] * dilation[1]) / 2.0};
		//Shifts the coordinates relative to the player's position and then converts model units to pixels.
		double[] convertedUnits = new double[] {scale * (coords[0] - shift[0]), scale * (coords[1] - shift[1])};
		
		// corner = screen's center + converted units - half of tile dimensions
		corner[0] =  convertedUnits[0] + screenCenter[0] - tileCorner[0];
		corner[1] =  convertedUnits[1] + screenCenter[1] - tileCorner[1];
		return corner;
	}
	
	protected double[] findDilation() {
		double scale = getScale();
		double[] imageDimensions = getImageDim();
		
		return new double[]{(scale * modelDimensions[0]) / imageDimensions[0], (scale * modelDimensions[1]) / imageDimensions[1]};
	}
	
	//////////////////////////// Accessor Methods //////////////////////////////
	
	protected abstract double[] getCoords();
	protected abstract double[] getShift();
	protected abstract double getScale();
	protected abstract double[] getScreenDim();
	protected abstract double[] getImageDim();
	
	
	protected AffineTransform getTransformation() {
		AffineTransform at = new AffineTransform();
		double[] corner = findCorner();
		double[] dilation = findDilation();
		
		at.setToTranslation(corner[0], corner[1]);
		at.scale(dilation[0], dilation[1]);
		
		return at;
	}
	
	///////////////////////////  Mutator Methods ////////////////////////////////
	
	
	public abstract void draw(Graphics g);
	

	

	
}
