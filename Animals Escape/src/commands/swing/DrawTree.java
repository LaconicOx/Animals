package commands.swing;

import view.ViewFacade;

public class DrawTree extends DrawTile{
	DrawTree(ViewFacade vf, double[] center){
		super(vf, center);
	}
	
	public void execute() {
		view.addTree(center);
	}
}
