package image_library;

import java.awt.image.BufferedImage;

import view.SwingFacade;

public class SwingGrass extends SwingTile{
	private static final SwingAnimation ANIMATION = SwingLoader.getGrass();
	private static final int TOTAL_FRAMES = ANIMATION.getTotal();
	private static final double[] DIMENSIONS = ANIMATION.getDimensions();
	
	public SwingGrass(SwingFacade sf, double[] coords, double[] modelDimensions){
		super(sf, coords, modelDimensions, DIMENSIONS);
	}
	
	////////////////////////// Accessors //////////////////////////////
	
	@Override
	protected final int getTotalFrames() {
		return TOTAL_FRAMES;
	}
	
	@Override
	protected final BufferedImage getFrame(int index) {
		return ANIMATION.getImage(index);
	}

}
