package units;

import java.awt.Dimension;

public class ViewKey extends GameKey{
	
	public ViewKey() {
		
	}
	
	public Dimension getPanel() {
		double[] dim = getScreenDim();
		return new Dimension((int)dim[0], (int)dim[1]);
	}
	
}
