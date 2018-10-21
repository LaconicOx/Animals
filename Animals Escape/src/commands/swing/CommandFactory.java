package commands.swing;


import game.Game;
import model.ModelFacade;
import view.ViewFacade;

public class CommandFactory {
	private static Game game;
	private static ModelFacade model;
	private static ViewFacade view;
	private static boolean initialized = false;
	public static CommandFactory unique;
	
	//////////////////// Constructor ///////////////////////////////
	private CommandFactory(Game gm, ModelFacade mf, ViewFacade vf) {
		game = gm;
		model = mf;
		view = vf;
	}
	
	public static void init(Game gm, ModelFacade mf, ViewFacade vf) {
		if (!initialized) {
			unique = new CommandFactory(gm, mf, vf);
			initialized = true;
		}
	}
	

	///////////////////// Getter Methods /////////////////////
	
	public static void getDrawGrass(double[] center){
		game.enqueCommand(new DrawGrass(view, center));
	}
	
	public static void getDrawBush(double[] vertices){
		game.enqueCommand(new DrawBush(view, vertices));
	}
	
	public static void getDrawRock(double[] vertices){
		game.enqueCommand(new DrawRock(view, vertices));
	}
	
	public static void getDrawTree(double[] vertices){
		game.enqueCommand(new DrawTree(view, vertices));
	}
	
	public static void getUpdatePlayer(double x, double y) {
		game.enqueCommand(new UpdatePlayer(model, x, y));
	}
	
	public static void getPingModel() {
		game.enqueCommand(new PingModel(model));
	}
	
	public static void getPingView() {
		game.enqueCommand(new PingView(view));
	}
	
	public static void getUpdateScale(double factor) {
		game.enqueCommand(new UpdateScale(model, factor));
	}
}
