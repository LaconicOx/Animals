package image_library;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import view.SwingFacade;

public class SwingPlayer extends Images implements PlayerImage{
	
	private static final SwingAnimation ANIMATION = SwingLoader.getPlayer();
	private static final int TOTAL_FRAMES = ANIMATION.getTotal();
	private static final double[] DIMENSIONS = ANIMATION.getDimensions();
	
	private final SwingFacade view;
	private double[] coords;
	
	public SwingPlayer(SwingFacade view, double[] coords, double[] modelDimensions) {
		super(DIMENSIONS, modelDimensions);
		this.coords = coords;
		this.view = view;
	}
	
	////////////////////// Accessors //////////////////////////////////
	
	protected final BufferedImage getFrame(int index) {
		return ANIMATION.getImage(index);
	}
	
	@Override
	protected final double[] getCoords() {
		return coords.clone();
	}
	
	@Override
	protected final double[] getShift() {
		return view.getShift();
	}
	
	protected final double getScale() {
		return view.getScale();
	}
	
	protected final double[] getScreenDim() {
		return view.getScreenDim();
	}
	
	@Override
	protected int getTotalFrames() {
		return TOTAL_FRAMES;
	}
	
	//////////////////////// Mutators //////////////////////////////
	
	
	@Override
	public final void send(double[] coords) {
		this.coords = coords;
		view.recieve(this);
	}
	
	@Override
	public final void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(getFrame(getFrameIndex()), getTransformation(), null);		
	}
	
}
