package commands.swing;

import view.ViewFacade;

public class DrawBush extends DrawTile{
	DrawBush(ViewFacade vf, double[] center){
		super(vf, center);
	}
	
	public void execute() {
		view.addBush(center);
	}
}
