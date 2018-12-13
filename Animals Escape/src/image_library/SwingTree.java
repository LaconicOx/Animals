package image_library;

import java.awt.image.BufferedImage;

import view.SwingFacade;

public class SwingTree extends SwingTile{
	private static final SwingAnimation ANIMATION = SwingLoader.getTree();
	private static final int TOTAL_FRAMES = ANIMATION.getTotal();
	private static final double[] DIMENSIONS = ANIMATION.getDimensions();
	
	public SwingTree(SwingFacade sf, double[] coords, double[] modelDimensions){
		super(sf, coords, modelDimensions, DIMENSIONS);
	}
	
	////////////////////////// Acessors //////////////////////////////
	
	@Override
	protected final int getTotalFrames() {
		return TOTAL_FRAMES;
	}
	
	@Override
	protected final BufferedImage getFrame(int index) {
		return ANIMATION.getImage(index);
	}

}
