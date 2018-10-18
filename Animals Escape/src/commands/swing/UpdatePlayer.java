package commands.swing;

import model.ModelFacade;

class UpdatePlayer extends Command{
	
	private double x;
	private double y;
	private ModelFacade model;
	
	UpdatePlayer(ModelFacade mf, double x, double y) {
		super();
		model = mf;
		this.x = x;
		this.y = y;
	}
	
	public void execute() {
		model.setPlayer(x, y);
	}
}
