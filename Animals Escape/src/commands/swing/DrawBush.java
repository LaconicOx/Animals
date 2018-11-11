package commands.swing;

import units.CellKey;
import view.ViewFacade;

public class DrawBush extends DrawTile{
	DrawBush(ViewFacade vf, CellKey key){
		super(vf, key);
	}
	
	public void execute() {
		view.addBush(key);
	}
}
