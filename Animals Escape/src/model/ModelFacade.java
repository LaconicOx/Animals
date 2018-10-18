package model;




import commands.swing.CommandFactory;
import game.Game;
import model.node.BoardFactory;
import model.node.TotalBoard;

public class ModelFacade{
	
	//Game Components
	TotalBoard total;
	Screen active;
	Game game;
	ModelParameters parameters;
	
	////////////////////////////// Constructor //////////////////////////////////
	
	public ModelFacade(Game game){
		this.game = game;
		parameters = ModelParameters.getInstance();
		total = TotalBoard.getInstance();
		BoardFactory.setTotal(parameters.getHorzSetps(), parameters.getVerSteps());
		active = new Screen();
	}
	
	public ModelFacade() {
		/*
		 * This constructor is only for testing ModelFacade in isolation. Any methods
		 * calling game or factory will throw nullpointer exceptions.
		 */
		parameters = ModelParameters.getInstance();
		total = TotalBoard.getInstance();
		BoardFactory.setTotal(parameters.getHorzSetps(), parameters.getVerSteps());
		active = new Screen();
	}
	
	///////////////////////// Accessor Methods ///////////////////////////
	
	public void getDrawCommands() {
		active.getTileCommands();
		CommandFactory.getPingView();
	}
	/////////////////////////// Mutator Methods //////////////////////////////////
	
	public void setPlayer(double x, double y){
		if(active.setPlayer(x, y))
			getDrawCommands();
		else
			getDrawCommands();//TODO: This must be replaced when I implement the viewSurface's ability to shift the image.
	}
	
	public void setScale(double factor) {
		parameters.setScale(factor);
		getDrawCommands();
	}
	
	/////////////////////// Display Methods ////////////////////////////////////////
	public void displayTotal() {System.out.println(total);}
	
	public void displayActive() {System.out.println(active);}
	
}
