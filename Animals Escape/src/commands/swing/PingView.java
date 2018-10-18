package commands.swing;

import view.ViewFacade;

public class PingView extends Command{
	ViewFacade vf;
	
	public PingView(ViewFacade vf) {
		this.vf = vf;
	}
	
	public void execute() {
		vf.update();
	}
}
