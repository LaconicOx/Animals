package view;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLayeredPane;

import game.Game;
import image_library.PlayerImage;
import image_library.SwingBush;
import image_library.SwingGrass;
import image_library.SwingPlayer;
import image_library.SwingRock;
import image_library.SwingTree;
import image_library.Tile;


public class SwingFacade implements ViewInterface{
	
	//Components
	private final ViewFrame vf;
	private final ViewSurface sur;
	private final ControlSurface con;
	
	//Animation Parameters 
	private double[] dimensions = {300.0, 300.0};//screen dimensions
	private double pixelsToUnit = 30.0; //Unit ratio of pixels to one model unit.
	private double[] shift = {0.0, 0.0};//stored as model units.
	
	///////////////////////// Constructor //////////////////////////////
	
	public SwingFacade(Game game) {
		Dimension dim = new Dimension((int)dimensions[0], (int)dimensions[1]);
		vf = new ViewFrame(dim);
		
		//Initializes a layered content pane
		Container layered = new JLayeredPane();
		sur = new ViewSurface(dim);
		layered.add(sur, 1);
		con = new ControlSurface(game, this, dim);
		layered.add(con, 0);
		vf.setContentPane(layered);
		vf.pack();
		vf.setVisible(true);
		sur.init();
	}
	
	//////////////////////////// Accessor Methods //////////////////////
	
	public double[] getScreenDim() {
		return dimensions.clone();
	}
	
	public double[] getShift() {
		return shift.clone();
	}
	
	public double getScale() {
		return pixelsToUnit;
	}
	
	public Tile getBush(double[] coords, double[] dimensions) {
		return new SwingBush(this, coords, dimensions);
	}
	
	public Tile getGrass(double[] coords, double[] dimensions) {
		return new SwingGrass(this, coords, dimensions);
	}
	
	public Tile getRock(double[] coords, double[] dimensions) {
		return new SwingRock(this, coords, dimensions);
	}
	
	public Tile getTree(double[] coords, double[] dimensions) {
		return new SwingTree(this, coords, dimensions);
	}
	
	public PlayerImage getPlayer(double[] coords, double[] dimensions) {
		return new SwingPlayer(this, coords, dimensions);
	}
	
	//////////////////////////// Mutator Methods /////////////////////////
	
	public void updateScale(double factor) {
		pixelsToUnit *= factor;
	}
	
	public void updateShift(double[] coords) {
		shift[0] = coords[0];
		shift[1] = coords[1];
	}
	
	public void render() {
		sur.render();
	}

	
	@Override
	public void recieve(Tile tile) {
		sur.recieve(tile);
	}
	
	@Override
	public void recieve(PlayerImage player) {
		sur.recieve(player);
	}
	
}
