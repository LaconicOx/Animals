package commands.swing;

import model.ModelFacade;

class PingModel extends Command {
	
	ModelFacade model;
	
	PingModel(ModelFacade mf){
		super();
		model = mf;
	}
	
	public void execute() {
		model.getDrawCommands();
	}
}
