package commands.swing;

import java.io.File;

import view.ViewFacade;

public class DrawBush extends DrawTile{
	DrawBush(ViewFacade vf, double[] center){
		super(vf, center);
	}
	
	protected File init() {
		return loader.getBushHex();
	}
}
