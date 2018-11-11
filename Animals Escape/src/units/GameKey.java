package units;

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
