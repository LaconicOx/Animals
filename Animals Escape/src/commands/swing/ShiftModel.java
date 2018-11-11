package commands.swing;

import model.ModelFacade;

class ShiftModel extends Command{
	
	private double angle;
	private ModelFacade model;
	
	ShiftModel(ModelFacade mf, double angle) {
		super();
		model = mf;
		this.angle = angle;
	}
	
	public void execute() {
		model.movePlayer(angle);
	}
}
