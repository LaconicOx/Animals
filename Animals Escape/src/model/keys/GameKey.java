package units;

/**
 * 
 * @author dvdco
 *Top level class for handling game parameters. It contains the ratio between model units and pixels and the screen's dimensions.
 *Its children are ModelKey and ImageKey.
 *
 */
public class GameKey {
	
	private static double[] panelDim = {300.0, 300.0};
	private static double pixelsToUnit = 30.0; //Unit ratio of pixels to one model unit.
	
	public GameKey() {}
	
	///////////////////////// Accessor Methods //////////////////////////
	
	protected double[] getScreenDim() {
		return panelDim.clone();
	}
	
	protected double getRatio() {
		return pixelsToUnit;
	}
	
	/////////////////////// Static Methods //////////////////////////////////
	
	public static void updateRatio(double factor) {
		pixelsToUnit *= factor;
	}
}
