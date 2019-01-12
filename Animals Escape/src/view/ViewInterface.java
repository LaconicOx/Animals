package view;

import image_library.PlayerImage;
import image_library.Tile;

public interface ViewInterface {
	
	///////////////////// Mutators ///////////////////////
	public void render();
	public void recieve(Tile tile);
	public void recieve(PlayerImage player);
	public void updateShift(double[] coords);
	public void updateScale(double factor);
	
	///////////////////// Accessors /////////////////////////
	
	public double[] getShift();
	public double[] getScreenDim();
	public double getScale();
	
	public Tile getBush(double[] coords, double[] dimensions);
	public Tile getGrass(double[] coords, double[] dimensions);
	public Tile getRock(double[] coords, double[] dimensions);
	public Tile getTree(double[] coords, double[] dimensions);
	public Tile getBorder(double[] coords, double[] dimensions);
	
	public PlayerImage getPlayer(double[] coords, double[] dimensions);
}
