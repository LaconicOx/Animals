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
	
	private final int getFrameIndex() {
		return advance % animations.get(facing).getTotal();
	}
	
	private final BufferedImage getFrame(int index) {
		return animations.get(facing).getImage(index);
	}
	
	@Override
	protected double[] getImageDim() {
		return animations.get(facing).getDimensions();
	}
	
	//////////////////////// Mutators //////////////////////////////
	
	@Override
	public final void advance(Direction dir) {
		
		
		if (dir == facing) {
			advance++;
		}
		else {
			facing = dir;
			advance = 1;
		}
	}
	
	@Override
	public final void send() {
		view.recieve(this);
	}
	
	@Override
	public final void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(getFrame(getFrameIndex()), getTransformation(), null);		
	}
	
	////////////////////////// Checker ////////////////////////////////
	
	@Override
	public final boolean checkResting() {
		if (getFrameIndex() == 0)
			return true;
		else
			return false;
	} 
}
