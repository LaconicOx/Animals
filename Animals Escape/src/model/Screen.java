package model;

import java.util.Iterator;
import java.util.TreeSet;

import model.ModelUtility.Direction;

import model.cell.Cell;
import model.cell.CellFactory;

class Screen {
	
	//Component fields
	TreeSet<Cell> screen;
	Cell center;
	ModelParameters parameters;
	Border border;
	CellFactory cellFactory;
	
	//////////////////////////// Constructor and Initializer ////////////////////////////////
	Screen(){
		parameters = ModelParameters.getInstance();
		this.screen = new TreeSet<>();
		cellFactory = new CellFactory();
		
		//Initializes screen and border
		init();
		border = new Border(this);
	}
	
	private void init() {
		TreeSet<Cell> cells = cellFactory.screenInit();
		while(cells.size() > 1) {
			screen.add(cells.pollFirst());
			screen.add(cells.pollLast());
		}
		center = cells.pollFirst();
		screen.add(center);
	}
	
	///////////////////////// Mutator Methods ////////////////////////////////
	
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
			border.shiftScreen(dir);
			
			return true;
		}
	}
	
	void putCell(Cell c){screen.add(c);}
	
	void removeCell(Cell c) {
		c.clearNode();
		screen.remove(c);
	}
	
	/////////////////////////// Checker Methods ////////////////////////////////
	
	private boolean checkCenter() {
		/*
		 * Returns true if the center cell contains the player's coordinates.
		 */
		boolean test = center.isContained(parameters.getScreenX(), parameters.getScreenY());
		return test;
	}
	
	
	/////////////////////// Accessor Methods /////////////////////////////
	
	void getTileCommands(){
		screen.forEach(cell -> cell.getCommand());
	}
	
	Cell getTopLeft() {return screen.first();}
	
	Cell getBottomRight() {return screen.last();}
	
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
