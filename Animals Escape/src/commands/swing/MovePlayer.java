package commands.swing;

import view.ViewFacade;

public class MovePlayer extends Command{
	
	ViewFacade vf;
	double[] coords;
	
	public MovePlayer(ViewFacade vf, double[] coords) {
		this.vf = vf;
		this.coords = coords;
	}
	
	@Override
	public void execute() {
		vf.shift(coords);
		
	}

}
