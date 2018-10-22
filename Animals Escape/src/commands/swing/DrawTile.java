package commands.swing;

import java.io.File;

import view.ViewFacade;

public abstract class DrawTile extends Command{
	
	protected File tile;
	protected double[] center;
	protected ViewFacade view;
	
	DrawTile(ViewFacade view, double[] center){
		this.center = center;
		this.view = view;
	}
	
	
	public abstract void execute();
}
