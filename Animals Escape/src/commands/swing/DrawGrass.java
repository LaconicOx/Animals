package commands.swing;


import units.CellKey;
import view.ViewFacade;

public class DrawGrass extends DrawTile{
	
	DrawGrass(ViewFacade vf, CellKey key){
		super(vf, key);
	}
	
	public void execute() {
		view.addGrass(key);
	}
}
