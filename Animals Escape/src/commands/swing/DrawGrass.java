package commands.swing;

import java.io.File;

import view.ViewFacade;

public class DrawGrass extends NewDrawTile{
	
	DrawGrass(ViewFacade vf, double[] center){
		super(vf, center);
		
	}
	
	protected File init() {
		return loader.getBaseHex();
	}
}
