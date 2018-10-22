package commands.swing;


import view.ViewFacade;

public class DrawGrass extends DrawTile{
	
	DrawGrass(ViewFacade vf, double[] center){
		super(vf, center);
	}
	
	public void execute() {
		view.addGrass(center);
	}
}
