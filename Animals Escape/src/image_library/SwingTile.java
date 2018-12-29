package image_library;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import game.Directions.Direction;
import view.SwingFacade;

public abstract class SwingTile extends Images implements Tile{
	
	private final SwingFacade view;
	private final double[] coords;
	private final HashMap<Direction, SwingAnimation> animations;
	private int advance;
	private Direction facing;
	
	protected SwingTile(SwingFacade view, double[] coords, double[] modelDimensions, HashMap<Direction, SwingAnimation> animations) {
		super(modelDimensions);
		this.animations = animations;
		this.coords = coords;
		this.view = view;
		advance = 0;
		facing = Direction.NE;
	}
	
	////////////////////// Accessors //////////////////////////////////
	
	protected abstract BufferedImage getFrame(Direction dir, int frame);
	
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
	

	
	//////////////////////// Mutators //////////////////////////////
	
	@Override
	public final void send() {
		view.recieve(this);
	}
	
	@Override
	public final void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(getFrame(getFrameIndex()), getTransformation(), null);		
	}
}
