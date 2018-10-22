package commands.swing;


import view.ViewFacade;

public class DrawRock extends DrawTile{
	DrawRock(ViewFacade vf, double[] center){
		super(vf, center);
	}
	
	public void execute() {
		view.addRock(center);
	}
}
