package view.images;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ViewParameters;

public class NewTile extends GameImage{
	
	private ViewParameters parameters;
	private AffineTransform at;
	protected BufferedImage im = null;
	
	///////////////////////// Constructor and Initializers /////////////////////////////////////
	public NewTile(File hex, double[] center) {
		
		try {
			im = ImageIO.read(hex);
		}catch(IOException e) {
			System.err.println("Could not read image file.");
		}
		parameters = ViewParameters.getInstance();
		at = new AffineTransform();
		at.setToTranslation(parameters.getCornerX(center[0]), parameters.getCornerY(center[1]));
		at.scale(parameters.getScale(), parameters.getScale());
	}
	
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(im, at, null);		
	}
		
	
}
