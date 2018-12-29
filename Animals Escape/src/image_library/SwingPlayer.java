package image_library;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import game.Directions.Direction;
import view.SwingFacade;

public class SwingPlayer extends Images implements PlayerImage{
	
	//Class Fields
	private static final String PATH = "Player_East/Player_East";
	private static final int FRAMES = 25;
	private static final double[] DIM = {435.0, 762.0};
	private static final HashMap<Direction, SwingAnimation> ANIMATIONS;
	static {
		ANIMATIONS = new HashMap<>();
		Direction[] keys = {Direction.E, Direction.N, Direction.W, Direction.S};
		SwingAnimation anim = new SwingAnimation(PATH, FRAMES, DIM);
		
		for (Direction key: keys) {
			ANIMATIONS.put(key, anim);
		}
	}
	
	//Instance Fields
	private final SwingFacade view;
	private double[] coords;
	private int advance;
	private Direction facing;
	
	///////////////////////// Constructor //////////////////////////////
	
	public SwingPlayer(SwingFacade view, double[] coords, double[] modelDimensions) {
		super(modelDimensions);
		this.coords = coords;
		this.view = view;
		facing = Direction.E;
		advance = 0;
	}
	
	////////////////////// Accessors //////////////////////////////////
	
	protected final BufferedImage getFrame(int index) {
		return ANIMATIONS.get(facing).getImage(index);
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
	
	private final int getTotalFrames() {
		return ANIMATIONS.get(facing).getTotal();
	}
	
	private final int getFrameIndex() {
		return advance % getTotalFrames();
	}
	
	protected double[] getImageDim() {
		return ANIMATIONS.get(facing).getDimensions();
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
	public final void send(double[] coords) {
		this.coords = coords;
		view.recieve(this);
	}
	
	@Override
	public final void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(getFrame(getFrameIndex()), getTransformation(), null);		
	}
	
	///////////////////////// Checkers ///////////////////////////
	
	@Override
	public final boolean checkResting() {
		if(getFrameIndex() == 0)
			return true;
		else
			return false;
	}
}
