package commands.swing;

import java.io.File;

import fileLoader.ArtLoader;
import view.ViewFacade;

public abstract class DrawTile extends Command{
	
	protected ArtLoader loader; 
	protected File tile;
	private double[] center;
	private ViewFacade view;
	
	DrawTile(ViewFacade view, double[] center){
		this.center = center;
		this.view = view;
		loader = ArtLoader.getInstance();
		this.tile = init();
	}
	
	protected abstract File init();
	
	public void execute() {
		view.testImages(tile, center);
	}
}
