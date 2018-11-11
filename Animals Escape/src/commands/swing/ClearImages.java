package commands.swing;

import view.ViewFacade;

public class ClearImages extends Command{
	
	ViewFacade vf;
	
	public ClearImages(ViewFacade vf) {
		this.vf = vf;
	}
	
	@Override
	public void execute() {
		vf.clearImages();
		
	}

}
