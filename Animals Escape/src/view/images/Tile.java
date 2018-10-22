package view.images;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ViewParameters;

public abstract class Tile extends GameImage{
	
	protected static final BufferedHexes bf = BufferedHexes.getInstance();
	
	private ViewParameters parameters;
	protected AffineTransform at;
	
	///////////////////////// Constructor and Initializers /////////////////////////////////////
	public Tile(double[] center) {
		
		parameters = ViewParameters.getInstance();
		at = new AffineTransform();
		at.setToTranslation(parameters.getCornerX(center[0]), parameters.getCornerY(center[1]));
		at.scale(parameters.getScale(), parameters.getScale());
	}
	
	
	public abstract void draw(Graphics g);
		
	//////////////////////////////////////// Inner Class ///////////////////////////////////////////
	
	protected static class BufferedHexes{
		
		File[] files = {new File("BushHex.png"), new File("GrassHex.png"), new File("RockHex.png"), new File("TreeHex.png")};
		BufferedImage[] buffers = new BufferedImage[files.length];
		
		static BufferedHexes unique = null;
		
		private BufferedHexes() {
			for (int i = 0; i < files.length; i++) {
				try {
					buffers[i] = ImageIO.read(files[i]);
				}catch(IOException e) {
					System.err.println("Could not read image file.");
				}
			}
			
		}
		
		static BufferedHexes getInstance() {
			if (unique == null)
				unique = new BufferedHexes();
			return unique;
		}
		
		protected BufferedImage getBush() { return buffers[0]; }
		protected BufferedImage getGrass() { return buffers[1]; }
		protected BufferedImage getRock() { return buffers[2]; }
		protected BufferedImage getTree() { return buffers[3]; }
		
	}
}
