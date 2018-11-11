package commands.swing;


import units.CellKey;
import view.ViewFacade;

public class DrawRock extends DrawTile{
	DrawRock(ViewFacade vf, CellKey key){
		super(vf, key);
	}
	
	public void execute() {
		view.addRock(key);
	}
}
