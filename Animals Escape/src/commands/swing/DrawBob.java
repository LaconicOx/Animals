package commands.swing;


import units.PlayerKey;
import view.ViewFacade;

public class DrawBob extends Command{
	
	protected PlayerKey key;
	protected ViewFacade view;
	
	public DrawBob(ViewFacade view, PlayerKey key){
		this.key = key;
		this.view = view;
		System.out.println("Drawbob created");
	}
	
	
	public void execute() {
		view.addBob(key);
	}
}
