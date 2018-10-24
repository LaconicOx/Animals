package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.ModelUtility.Direction;
import model.cell.BorderCell;
import model.cell.Cell;
import model.cell.ConcreteCell;
import model.node.Node;
import model.node.TotalBoard;

public class Screen {
	ModelParameters parameters;
	Set<Cell> screen;
	Set<Cell> border;
	Cell center;
	TotalBoard board;
	
	////////////////////////// Constructors and Initializers //////////////////////////
	Screen(){
		screen = new HashSet<>();
		border = new HashSet<>();
		parameters = ModelParameters.getInstance();
		board = TotalBoard.getInstance();
		init();
	}
	
	private void init() {
		/*
		 * boundaries[0] represents the left boundary.
		 * boundaries[1] represents the right boundary.
		 * boundaries[2] represents the top boundary.
		 * boundaries[3] represents the bottom boundary.
		 */
		double[] boundaries = {0 - parameters.getExtension(), 
				parameters.getPanelWidth() + parameters.getExtension(),
				0 - parameters.getExtension(), 
				parameters.getPanelHeight() + parameters.getExtension()};
		
		center = new ConcreteCell(board.getNodeInstance(0, 0));
		screen.add(center);
		recurInit(center.getNeighborNode(Direction.N), boundaries);
		
	}
	
	/**
	 * A helper method for init. It builds the screen recursively by traversing the 
	 * node trees.
	 * @param n - node for the new cell to be created and added to screen.
	 * @param boundaries - inclusive boundaries for the screen.
	 */
	private void recurInit(Node n, double[] boundaries) {
		Cell newCell = new ConcreteCell(n);
		screen.add(newCell);
		
		for (Direction dir : Direction.values()) {
			if(newCell.getNeighborCell(dir, true) == null) {
				Node newNode = newCell.getNeighborNode(dir);
				double x = parameters.getNodeCellX(newNode);
				double y = parameters.getNodeCellY(newNode);
				//Calls itself to add a new concrete cell to screen if within boundaries;
				//otherwise, adds a border cell to border.
				if ( x >= boundaries[0] && x <= boundaries[1] && y >= boundaries[2] && y <= boundaries[3])
					recurInit(newNode, boundaries);
				else
					border.add(new BorderCell(newNode));
			}
		}
	}
	
	////////////////////////// Accessor Methods /////////////////////////////
	
	void getTileCommands(){
		screen.forEach(cell -> cell.getCommand());
	}
	
	////////////////////////// Mutator Methods /////////////////////////////////
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true when player leaves center cell, otherwise false.
	 */
	boolean setPlayer(double x, double y) {
		//TODO clean up this method and shiftCoords.
		parameters.shift(x, y);
		if (checkCenter()) {
			return false;
		}
		else {
			Direction dir = Cell.getCellDirection(center, parameters.getScreenCenter());
			boolean noNullReturn = true;
			center = center.getNeighborCell(dir, noNullReturn);
			shiftScreen(dir);
			
			return true;
		}
	}
	
	void shiftScreen(Direction dir) {
		
	}
	/////////////////////////// Checker Methods ////////////////////////////////
	
	private boolean checkCenter() {
		/*
		 * Returns true if the center cell contains the player's coordinates.
		 */
		boolean test = center.isContained(parameters.getScreenX(), parameters.getScreenY());
		return test;
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
	
}
