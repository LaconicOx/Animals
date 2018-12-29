package image_library;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class SwingAnimation {
	private final BufferedImage[] images;
	private final double[] dimensions;
	
	SwingAnimation(String path, int frames, double[] dimensions){
		this.dimensions = dimensions;
		images = new BufferedImage[frames];
		
		
		//Loads Images
		String index = null;
		for(int i = 0; i < frames; i++) {
			if (i < 10) {
				index = "_00" + i;
			}
			else if (i <100) {
				index = "_0" + i;
			}
			else if (i < 1000) {
				index = "_" + i;
			}
			else {
				System.err.println("Error in Animation: " + path + " has index larger than three digits.");
				System.exit(0);
			}
			
			File file = new File(path + index +".png");
			try {
				images[i] = ImageIO.read(file);
			}catch(IOException e) {
				System.err.println("Could not read " + path + index);
				System.exit(0);
			}
		}
	}
	
	
	
	/////////////////////// Accessors //////////////////////////////////
	
	final BufferedImage getImage(int index){
		return images[index];
	}
	
	final double[] getDimensions() {
		return dimensions.clone();
	}
	
	final int getTotal() {
		return images.length;
	}
}
