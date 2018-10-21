package commands.swing;

import java.io.File;

import view.ViewFacade;

public class DrawGrass extends DrawTile{
	
	DrawGrass(ViewFacade vf, double[] center){
		super(vf, center);
	}
	
	protected File init() {
		return loader.getGrassHex();
	}
}
