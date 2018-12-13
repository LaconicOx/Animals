package image_library;

import java.awt.image.BufferedImage;

class SwingAnimation {
	private final BufferedImage[] images;
	private final double[] dimensions;
	
	SwingAnimation(BufferedImage[] images, double[] dimensions){
		this.images = images;
		this.dimensions = dimensions;
	}
	
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
