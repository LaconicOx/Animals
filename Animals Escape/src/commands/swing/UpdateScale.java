package commands.swing;

import model.ModelFacade;

public class UpdateScale extends Command{
	
	ModelFacade mf;
	double factor;
	
	public UpdateScale(ModelFacade mf, double factor) {
		this.mf = mf;
		this.factor = factor;
	}
	
	public void execute() { 
		mf.setScale(factor);
		}
}
	
