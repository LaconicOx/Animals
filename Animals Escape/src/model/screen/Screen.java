package model.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.Directions.Direction;
import model.board.TotalBoard;
import model.characters.Characters;
import model.keys.CellKey;
import view.ViewInterface;

public class Screen {
	
	//Class fields
	private static final int EXTENSION = 1;//Extends cell beyond edge of screen to prevent flickering
	private static final double UNIT_APOTHEM = 1.0;
	private static final double UNIT_RADIUS = (2 * Math.sqrt(3)) / 3.0;
	private static final double[] UNIT_VECTOR = {1.5 * UNIT_RADIUS, UNIT_APOTHEM};//Vector for converting node units to model units.
	
	
	//Instance Fields
	private final ViewInterface view;
	private Set<Characters> characters;
	private Map<CellKey, Cell> screen;
	private Map<CellKey, BorderCell> border;
	private boolean playerMoved;
	
	
	////////////////////////// Constructors and Initializers //////////////////////////
	public Screen(ViewInterface view){
		this.view = view;
		screen = new HashMap<>();
		border = new HashMap<>();
		characters = new HashSet<>();
		playerMoved = false;
	}
	
	private double[] getModelBoundaries(double[] center) {
		/*
		 * boundaries[0] represents the left boundary.
		 * boundaries[1] represents the right boundary.
		 * boundaries[2] represents the top boundary.
		 * boundaries[3] represents the bottom boundary.
		 */
		
		//Converts screendimensions form pixels to model units.
		double[] screenDim = view.getScreenDim();//Screen dimensions in pixels
		double scale = view.getScale();//number of pixels per model unit
		double[] dim = new double[] {screenDim[0] / scale, screenDim[1] / scale};//Dimensions in model units
		
		double[] boundaries = new double[4];
		
		//Distance from the center to the top or bottom boundaries of the panel.
		double vertical = dim[0] / 2.0 + UNIT_VECTOR[0] * EXTENSION;
		//Distance from the center to the left of right boundaries of the panel.
		double horizontal = dim[1] / 2.0 + UNIT_VECTOR[1] * EXTENSION;
		
		boundaries[0] = center[0] - horizontal;
		boundaries[1] = center[0] + horizontal;
		boundaries[2] = center[1] - vertical;
		boundaries[3] = center[1] + vertical;
		
		return boundaries;	
	}
	
	
	//Including init() in the constructor led to circular calls. 
	//Making it public is a temporary fix
	//TODO: Fix circular calls.
	public void init() {
		/*
		 * boundaries[0] represents the left boundary.
		 * boundaries[1] represents the right boundary.
		 * boundaries[2] represents the top boundary.
		 * boundaries[3] represents the bottom boundary.
		 */
		double[] boundaries = getModelBoundaries(new double[] {0.0, 0.0});
		CellKey cenKey = new CellKey(TotalBoard.getNodeInstance(new int[] {0,0}));
		Cell center = new ScreenCell(cenKey);
		screen.put(cenKey, center);
		recurInit(center.getNeighborKey(Direction.N), boundaries);
		
	}
	
	/**
	 * A helper method for init. It builds the screen recursively by traversing the 
	 * node trees.
	 * @param n - node for the new cell to be created and added to screen.
	 * @param boundaries - inclusive boundaries for the screen.
	 */
	private void recurInit(CellKey key, double[] boundaries) {
		Cell newCell = new ScreenCell(key);
		screen.put(key, newCell);
		
		for (Direction dir : Direction.values()) {
			CellKey dirKey = newCell.getNeighborKey(dir);
			if(!screen.containsKey(dirKey) && !border.containsKey(dirKey)) {
				double[] cen = dirKey.getCenter();
				//Calls itself to add a new concrete cell to screen if within boundaries;
				//otherwise, adds a border cell to border.
				if ( cen[0] >= boundaries[0] && cen[0] <= boundaries[1] && cen[1] >= boundaries[2] && cen[1] <= boundaries[3])
					recurInit(dirKey, boundaries);
				else {
					border.put(dirKey, new BorderCell(dirKey));
				}
					
			}
		}
	}
	
	////////////////////////// Accessor Methods /////////////////////////////
	
	
	public Cell getNeighborCell(CellKey key) {
		if (screen.containsKey(key))
			return screen.get(key);
		else if(border.containsKey(key))
			return border.get(key);
		else
			return null;
	}
	
	public static double getApothem() {
		return UNIT_APOTHEM;
	}
	
	public static double getRadius() {
		return UNIT_RADIUS;
	}
	
	public static double[] getUnit() {
		return UNIT_VECTOR;
	}
	
	////////////////////////// Mutator Methods /////////////////////////////////
	
	public void pumpAnimation() {
		screen.forEach((key, cell) -> cell.draw());
	}
	
	/**
	 * Forwarding Method.
	 * @param angle - the direction the the player's movement in radians.
	 */
	public void movePlayer(double angle) {
		//player.move(angle);
		playerMoved = true;
	}
	
	public void updateCharacters() {
		System.out.println(playerMoved);
		if (!playerMoved)
			//player.update();
		playerMoved = false;
	}
	
	public void shiftCells(Direction dir) {
		ArrayList<Instruction> instructions = new ArrayList<>();
		border.forEach((key,cell) -> instructions.add(new Instruction(cell, dir)));
		instructions.forEach(instruct -> instruct.execute());
	}
	
	public void addCharacters(Characters ch) { characters.add(ch); }
	
	void addScreenCell(CellKey key) { screen.put(key, new ScreenCell(key)); }
	
	void removeScreenCell(CellKey key) {
		
		screen.remove(key);
	}
	
	void addBorderCell(CellKey key) { border.put(key, new BorderCell(key)); }
	
	void removeBorderCell(CellKey key) {
		border.remove(key);
	}
	
	public void removeCharacters(CellKey key) {
		//TODO
	}
	
	////////////////////////// Checker Methods ////////////////////////////////
	
	public boolean checkScreen(CellKey key) {
		return screen.containsKey(key);
	}
	
	public boolean checkBorder(CellKey key) {
		return border.containsKey(key);
	}
	
	///////////////////////// Inner Class /////////////////////////////////////
	private enum State{NULL, SCREEN, BORDER};
	
	class Instruction {
		
		private BorderCell cell;
		private List<Action> actions;
		private Direction toward;
		private HashMap<Direction, State> orientation = null;
		
		//////////////// Constructor and initializers ///////////////////////
		
		Instruction(BorderCell cell, Direction toward){
			this.cell = cell;
			this.toward = toward;
			actions = new ArrayList<>();
			initOrientation();
			getShiftInstruction(toward);
		}
		
		private void getShiftInstruction(Direction dir) {
			
			//Builds instruction for creating a cell at the node in the direction of dir.
			State toward = orientation.get(dir);
			if (toward == State.NULL)
				nullToBorder();
			else if (toward == State.SCREEN)
				screenToBorder();
			else if (toward == State.BORDER)
				keep();
			else
				System.err.println("Error in getInstruction(): unrecognized choice.");
			
			//Builds instruction for creating a cell at the node for this cell.
			State away = orientation.get(Direction.getOpposite(dir));
			if (away == State.NULL)
				selfToNull();
			else if (away == State.SCREEN)
				selfToScreen();
			else if (away == State.BORDER)
				keep();
			else
				System.err.println("Error in getInstruction(): unrecognized choice.");
			
			if(away == State.NULL && toward == State.NULL) {
				System.err.println("Error in getShiftInstructions(): Null, null orientation" );
			}
		}
		
		/**
		 * Determines where the cell is relative to other screen objects.
		 */
		private void initOrientation() {
			orientation = new HashMap<>();
			for (Direction dir : Direction.values()) {
				CellKey test = cell.getNeighborKey(dir);

				if (checkScreen(test))
					orientation.put(dir, State.SCREEN);
				else if (checkBorder(test))
					orientation.put(dir, State.BORDER);
				else
					orientation.put(dir, State.NULL);
			}
		}
		
		///////////////////// Accessor Method ////////////////////
		
		void execute() {
			actions.forEach(action -> action.execute());
		}
		
		///////////////////// Builder Methods ////////////////////
		void screenToBorder() {
			actions.add(new DeleteScreen());
			actions.add(new CreateBorder());
		}
		
		void nullToBorder() {
			actions.add(new CreateBorder());
		}
		
		void selfToNull(){
			actions.add(new DeleteSelf());
		}
		
		void selfToScreen() {
			actions.add(new Transform());
		}
		
		void keep() {};//The method is here to represent the choice of keeping the cell
		
		/////////////////////// Inner Classes /////////////////////
		abstract class Action { abstract void execute(); }
		
		class CreateBorder extends Action{
			void execute() { 
				CellKey key = cell.getNeighborKey(toward);
				addBorderCell(key);; 
			}
		}
		
		class DeleteSelf extends Action{
			void execute() { 
				removeBorderCell(cell.getKey());	
			}
		}
		
		class DeleteScreen extends Action{
			void execute() { 
				CellKey key = cell.getNeighborKey(toward);
				removeCharacters(key);//tests characters for removal
				removeScreenCell(key);//remove screen cell.
			}
		}
		
		class Transform extends Action{
			void execute() {
				CellKey key = cell.getKey();
				removeBorderCell(key);
				addScreenCell(key);
				}
		}
	}//End of Instruction
}//End of Screen
