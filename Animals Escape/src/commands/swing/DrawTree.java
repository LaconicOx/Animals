package commands.swing;

import units.CellKey;
import view.ViewFacade;

public class DrawTree extends DrawTile{
	DrawTree(ViewFacade vf, CellKey key){
		super(vf, key);
	}
	
	public void execute() {
		view.addTree(key);
	}
}
