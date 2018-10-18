package commands.swing;

import java.awt.Color;

import view.ViewFacade;

class DrawTile extends Command{
	ViewFacade view;
	double[][] vertices;
	Color fill;
	
	DrawTile(ViewFacade v, double[][] vertices, Color fill) {
		super();
		view = v;
		this.vertices = vertices;
		this.fill = fill;
	}
	
	public void execute() {
		view.draw(vertices, fill);
	}
}
