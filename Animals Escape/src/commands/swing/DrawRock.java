package commands.swing;

import java.io.File;

import view.ViewFacade;

public class DrawRock extends DrawTile{
	DrawRock(ViewFacade vf, double[] center){
		super(vf, center);
	}
	
	protected File init() {
		return loader.getRockHex();
	}
}
