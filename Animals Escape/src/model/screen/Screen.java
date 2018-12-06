package model.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import commands.swing.CommandFactory;
import model.Directions.Direction;
import model.board.TotalBoard;
import model.characters.Characters;
import model.characters.Player;
import units.CellKey;
import units.ModelKey;

public class Screen {
	
	private Set<Characters> characters;
	private ModelKey key;
	private Map<CellKey, Cell> screen;
	private Map<CellKey, BorderCell> border;
	private TotalBoard board;
	private static Screen unique = null;
	private Player player;
	private boolean playerMoved;
	
	////////////////////////// Constructors and Initializers //////////////////////////
	private Screen(){
		screen = new HashMap<>();
		border = new HashMap<>();
		key = new ModelKey();
		board = TotalBoard.getInstance();
		characters = new HashSet<>();
		playerMoved = false;
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
		double[] boundaries = key.getModelBoundaries(new double[] {0.0, 0.0});
		CellKey cenKey = new CellKey(board.getNodeInstance(new int[] {0,0}));
		Cell center = new ScreenCell(cenKey);
		screen.put(cenKey, center);
		recurInit(center.getNeighborKey(Direction.N), boundaries);
		
		//Creates player
		player = new Player(center);
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
	
	public void getTileCommands(){
		CommandFactory.getClearImages();
		screen.forEach((key, cell) -> cell.getCommand());
	}
	
	public Cell getNeighborCell(CellKey key) {
		if (screen.containsKey(key))
			return screen.get(key);
		else if(border.containsKey(key))
			return border.get(key);
		else
			return null;
	}
	
	////////////////////////// Mutator Methods /////////////////////////////////
	
	/**
	 * Forwarding Method.
	 * @param angle - the direction the the player's movement in radians.
	 */
	public void movePlayer(double angle) {
		player.move(angle);
		playerMoved = true;
	}
	
	public void updateCharacters() {
		System.out.println(playerMoved);
		if (!playerMoved)
			player.update();
		playerMoved = false;
	}
	
	public void shiftCells(Direction dir) {
		ArrayList<Instruction> instructions = new ArrayList<>();
		border.forEach((key,cell) -> instructions.add(cell.getShiftInstruction(dir)));
		instructions.forEach(instruct -> instruct.execute());
		getTileCommands();//Sends commands to view to generate a new set of tile images.
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
	
	
}//End of Screen
