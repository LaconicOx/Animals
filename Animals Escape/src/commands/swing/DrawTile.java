package commands.swing;



import units.CellKey;
import view.ViewFacade;

public abstract class DrawTile extends Command{
	
	protected CellKey key;
	protected ViewFacade view;
	
	DrawTile(ViewFacade view, CellKey key){
		this.key = key;
		this.view = view;
	}
	
	
	public abstract void execute();
}
