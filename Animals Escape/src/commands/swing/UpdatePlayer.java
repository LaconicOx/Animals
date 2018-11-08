package commands.swing;

import model.ModelFacade;

class UpdatePlayer extends Command{
	
	private double angle;
	private ModelFacade model;
	
	UpdatePlayer(ModelFacade mf, double angle) {
		super();
		model = mf;
		this.angle = angle;
	}
	
	public void execute() {
		model.movePlayer(angle);
	}
}
