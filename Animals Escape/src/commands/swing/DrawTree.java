package commands.swing;

import java.io.File;

import view.ViewFacade;

public class DrawTree extends DrawTile{
	DrawTree(ViewFacade vf, double[] center){
		super(vf, center);
	}
	
	protected File init() {
		
		return loader.getTreeHex();
	}
}
