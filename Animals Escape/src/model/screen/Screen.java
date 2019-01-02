package model.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Directions.Direction;
import model.ModelKey;
import model.board.Node;
import model.board.TotalBoard;
import view.ViewInterface;

public class Screen {
	
	//Class fields
	private static final int EXTENSION = 1;//Extends cell beyond edge of screen to prevent flickering
	private static final Direction[] CELL_DIRECTIONS = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
	
	
	//Instance Fields
	private final ViewInterface view;
	private Map<ModelKey, ScreenCell> screen;
	private Map<ModelKey, BorderCell> border;
	
	
	////////////////////////// Constructors and Initializers //////////////////////////
	public Screen(ViewInterface view){
		this.view = view;
		screen = new HashMap<>();
		border = new HashMap<>();
		
		//Initializes screen and border.
		double[] boundaries = getModelBoundaries(new double[] {0.0, 0.0});
		ModelKey cenKey = new ModelKey(new int[] {0,0});
		Node node = TotalBoard.getNode(cenKey, false);
		ScreenCell center = new ScreenCell(node);
		screen.put(cenKey, center);
		recurInit(center.getNeighborKey(Direction.N), boundaries);
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
		double[] vector = ModelKey.getDimensions();//Vector representing dimensions of a hexagon.
		
		double[] boundaries = new double[4];
		
		//Distance from the center to the top or bottom boundaries of the panel.
		double vertical = dim[0] / 2.0 + vector[0] * EXTENSION;
		//Distance from the center to the left of right boundaries of the panel.
		double horizontal = dim[1] / 2.0 + vector[1] * EXTENSION;
		
		boundaries[0] = center[0] - horizontal;
		boundaries[1] = center[0] + horizontal;
		boundaries[2] = center[1] - vertical;
		boundaries[3] = center[1] + vertical;
		
		return boundaries;	
	}
	
	/**
	 * A helper method for init. It builds the screen recursively by traversing the 
	 * node trees.
	 * @param n - node for the new cell to be created and added to screen.
	 * @param boundaries - inclusive boundaries for the screen.
	 */
	private void recurInit(ModelKey key, double[] boundaries) {
		Node node = TotalBoard.getNode(key, false);
		ScreenCell newCell = new ScreenCell(node);
		screen.put(key, newCell);
		
		for (Direction dir : CELL_DIRECTIONS) {
			ModelKey dirKey = newCell.getNeighborKey(dir);
			if(!screen.containsKey(dirKey) && !border.containsKey(dirKey)) {
				double[] cen = dirKey.getCenter();
				//Calls itself to add a new concrete cell to screen if within boundaries;
				//otherwise, adds a border cell to border.
				if ( cen[0] >= boundaries[0] && cen[0] <= boundaries[1] && cen[1] >= boundaries[2] && cen[1] <= boundaries[3])
					recurInit(dirKey, boundaries);
				else {
					Node borderNode = TotalBoard.getNode(dirKey, false);
					border.put(dirKey, new BorderCell(borderNode));
				}
					
			}
		}
	}
	
	////////////////////////// Accessor Methods /////////////////////////////
	
	public Cell getCell(ModelKey key) {
		Cell cell = screen.get(key);
		if (cell == null) {
			System.err.println("Error in getCell(): cell not found");
			System.exit(0);
		}
		return cell;
	}
	
	public Cell getNeighborCell(ModelKey key) {
		if (screen.containsKey(key))
			return screen.get(key);
		else if(border.containsKey(key))
			return border.get(key);
		else
			return null;
	}
	
	////////////////////////// Mutator Methods /////////////////////////////////
	
	public void updateCells() {
		border.forEach((key,cell) -> cell.update());
		screen.forEach((key, cell) -> cell.update());
	}
	
	public void shiftCells(Direction dir) {
		ArrayList<Instruction> instructions = new ArrayList<>();
		border.forEach((key,cell) -> instructions.add(new Instruction(cell, dir)));
		instructions.forEach(instruct -> instruct.execute());
	}
	
	void addScreenCell(ModelKey key) { 
		Node node = TotalBoard.getNode(key, false);
		screen.put(key, new ScreenCell(node)); 
		}
	
	void removeScreenCell(ModelKey key) {
		screen.remove(key);
	}
	
	void addBorderCell(ModelKey key) { 
		Node node = TotalBoard.getNode(key, false);
		border.put(key, new BorderCell(node)); }
	
	void removeBorderCell(ModelKey key) {
		border.remove(key);
	}
	
	////////////////////////// Checker Methods ////////////////////////////////
	
	public boolean checkScreen(ModelKey key) {
		return screen.containsKey(key);
	}
	
	public boolean checkBorder(ModelKey key) {
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
			Direction[] directions = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
			for (Direction dir : directions) {
				ModelKey test = cell.getNeighborKey(dir);

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
				ModelKey key = cell.getNeighborKey(toward);
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
				ModelKey key = cell.getNeighborKey(toward);
				removeScreenCell(key);//remove screen cell.
			}
		}
		
		class Transform extends Action{
			void execute() {
				ModelKey key = cell.getKey();
				removeBorderCell(key);
				addScreenCell(key);
				}
		}
	}//End of Instruction
}//End of Screen
