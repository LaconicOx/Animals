package model.screen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.ModelParameters;
import model.ModelParameters.Direction;
import model.board.Node;
import model.board.TotalBoard;
import model.characters.Characters;
import model.characters.Player;

public class Screen {
	
	private Set<Characters> characters;
	private ModelParameters parameters;
	private Set<Cell> screen;
	private Set<BorderCell> border;
	//private Cell center;
	private TotalBoard board;
	private static Screen unique = null;
	private Player player;
	
	////////////////////////// Constructors and Initializers //////////////////////////
	private Screen(){
		screen = new HashSet<>();
		border = new HashSet<>();
		parameters = ModelParameters.getInstance();
		board = TotalBoard.getInstance();
		characters = new HashSet<>();
	}
	
	public static Screen getInstance() {
		if (unique == null)
			unique = new Screen();
		return unique;
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
		int[] centerCoords = {0,0};
		double[] boundaries = parameters.getUnitBoundaries(centerCoords);
		
		Cell center = new ScreenCell(board.getNodeInstance(centerCoords));//TODO Eliminate static method.
		player = new Player(center);
		screen.add(center);
		recurInit(center.getNeighborNode(Direction.N), boundaries);
		
		//Creates player
		player = new Player(center);
	}
	
	/**
	 * A helper method for init. It builds the screen recursively by traversing the 
	 * node trees.
	 * @param n - node for the new cell to be created and added to screen.
	 * @param boundaries - inclusive boundaries for the screen.
	 */
	private void recurInit(Node n, double[] boundaries) {
		Cell newCell = new ScreenCell(n);
		screen.add(newCell);
		
		for (Direction dir : Direction.values()) {
			if(newCell.getNeighborCell(dir, true) == null) {
				Node newNode = newCell.getNeighborNode(dir);
				int[] cen = newNode.getCenter();
				//System.err.println(cen[0] + ", " + cen[1]);
				//Calls itself to add a new concrete cell to screen if within boundaries;
				//otherwise, adds a border cell to border.
				if ( cen[0] >= boundaries[0] && cen[0] <= boundaries[1] && cen[1] >= boundaries[2] && cen[1] <= boundaries[3])
					recurInit(newNode, boundaries);
				else {
					//System.err.println("borderCell created");
					border.add(new BorderCell(newNode));
				}
					
			}
		}
	}
	
	////////////////////////// Accessor Methods /////////////////////////////
	
	public void getTileCommands(){
		screen.forEach(cell -> cell.getCommand());
	}
	
	////////////////////////// Mutator Methods /////////////////////////////////
	
	/**
	 * @param angle - the direction the the player's movement in radians.
	 */
	public void movePlayer(double angle) {
		//Shifts the screen if the player moves to a new cell.
		double[] start = player.getCenter();
		if(player.move(angle)) {
			Direction dir = Direction.getDirection(start, player.getCenter());
			shiftCells(dir);
		}
			
	}
	
	void updateCharacters() {
		//TODO
	}
	
	private void shiftCells(Direction dir) {
		ArrayList<Instruction> instructions = new ArrayList<>();
		border.forEach(cell -> instructions.add(cell.getShiftInstruction(dir)));
		instructions.forEach(instruct -> instruct.execute());
		getTileCommands();//Sends commands to view to generate a new set of tile images.
	}
	
	public void addCharacters(Characters ch) { characters.add(ch); }
	
	void addScreenCell(ScreenCell cell) { screen.add(cell); }
	
	void removeScreenCell(ScreenCell cell) {
		
		cell.clearNode();
		screen.remove(cell);
	}
	
	void addBorderCell(BorderCell cell) { border.add(cell); }
	
	void removeBorderCell(BorderCell cell) {
		cell.clearNode();
		border.remove(cell);
	}
	
	/////////////////////////// Checker Methods ////////////////////////////////
	
	
	public void removeCharacters(Cell test) {
		Iterator<Characters> charIt = characters.iterator();
		while(charIt.hasNext()) {
			Characters ch = charIt.next();
			if(ch.offScreen(test))
				characters.remove(ch);
		}
	}
	
	////////////////////////// Overrides ////////////////////////////////
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Cell> screenIt = screen.iterator();
		while(screenIt.hasNext())
			sb.append(screenIt.next().toString() + "\n");
		return sb.toString();		
	} 
	
	//////////////////////// Debugging Methods //////////////////////////////
	
	public void displayScreen() {
		StringBuilder sb = new StringBuilder();
		Iterator<Cell> screenIt = screen.iterator();
		while(screenIt.hasNext())
			sb.append(screenIt.next().toString() + ", ");
		System.out.println(sb.toString());
	}
	
	public void displayBorder() {
		StringBuilder sb = new StringBuilder();
		Iterator<BorderCell> borderIt = border.iterator();
		while(borderIt.hasNext())
			sb.append(borderIt.next().toString() + ", ");
		System.out.println(sb.toString());
	}
	
}//End of Screen
