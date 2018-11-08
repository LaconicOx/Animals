package commands.swing;

import view.ViewFacade;

public class UpdateScale extends Command{
	
	ViewFacade vf;
	double factor;
	
	public UpdateScale(ViewFacade vf, double factor) {
		this.vf = vf;
		this.factor = factor;
	}
	
	public void execute() {
		
		vf.setScale(factor);
		}
}
	
