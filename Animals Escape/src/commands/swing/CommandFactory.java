package commands.swing;


import game.Game;
import model.ModelFacade;
import units.CellKey;
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
	
	public static void getDrawGrass(CellKey key){
		game.enqueCommand(new DrawGrass(view, key));
	}
	
	public static void getDrawBush(CellKey key){
		game.enqueCommand(new DrawBush(view, key));
	}
	
	public static void getDrawRock(CellKey key){
		game.enqueCommand(new DrawRock(view, key));
	}
	
	public static void getDrawTree(CellKey key){
		game.enqueCommand(new DrawTree(view, key));
	}
	
	public static void getUpdatePlayer(double angle) {
		game.enqueCommand(new ShiftModel(model, angle));
	}
	
	public static void getPingModel() {
		game.enqueCommand(new PingModel(model));
	}
	
	public static void getPingView() {
		game.enqueCommand(new PingView(view));
	}
	
	public static void getUpdateScale(double factor) {
		game.enqueCommand(new UpdateScale(view, factor));
	}
	
	public static void getMovePlayer(double[] coords) {
		game.enqueCommand(new MovePlayer(view, coords));
	}
	
	public static void getClearImages() {
		game.enqueCommand(new ClearImages(view));
	}
}
