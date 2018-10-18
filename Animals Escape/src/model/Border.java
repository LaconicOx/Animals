package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.ModelUtility.*;
import model.cell.Cell;
import model.cell.CellFactory;

class Border {
	
	Screen screen;
	Set<Cell> border;
	CellFactory cFactory;
	
	/////////////////////////////// Constructor and intializers ///////////////////////////////
	Border(Screen screen){
		this.screen = screen;
		border = new HashSet<>();
		cFactory = new CellFactory();
		borderInit();
		
	}
	
	private void borderInit() {
		Cell first = screen.getTopLeft();
		//Guides the for-loop around the boundary.
		Edge[] steps = {Edge.TOP, Edge.RIGHT, Edge.BOTTOM, Edge.LEFT}; 
		
		Cell previousBorder = cFactory.getBorderInstance(first, Edge.LEFT);//Wraps the top-left cell in order to be double wrapped into corner.
		Cell currentCell = first;
		ArrayList<Cell> neighbors = new ArrayList<>();
		
		//For-loop steps around the boundary.
		for(Edge step : steps) {
			//Double wraps bordercells to represent corners.
			border.add(cFactory.getBorderInstance(previousBorder, step));
			
			//While-loop keeps retrieving neighbors until it hits the edge of the screen.
			while(true) {
				Iterator<Direction> moveIt = step.getMoves().iterator();
				Cell previousCell = previousBorder.getCell();
				//While-loop for instances of multiple neighbors.
				while(moveIt.hasNext()) {
					boolean nullReturn = true;
					neighbors.add(previousCell.getNeighborCell(moveIt.next(), nullReturn));
				}
				//Applies rules contained in Edges to select appropriate cell.
				currentCell = Cell.select(neighbors, step);
				
				//Gate for adding BorderCells and breaking loop.
				//The currentCell will be null when the perviousBorder is the corner.
				if (currentCell == null) {
					border.remove(previousBorder);//Must remove BorderCell to turn into corner in next step of for loop.
					neighbors.clear();
					break;
				}
				else {
					previousBorder = cFactory.getBorderInstance(currentCell, step);
					border.add(previousBorder);
					neighbors.clear();
				}
			}
		}
	}
		
	/////////////////////////////// Mutator Methods ////////////////////////////////
	
	void shiftScreen(Direction dir) {
		
		HashSet<Cell> newBorder = new HashSet<>();
		
		Iterator<Cell> borderIt = border.iterator();
		while(borderIt.hasNext()) {
			Cell bc = borderIt.next();
			Cell newCell = bc.getNewBorder(dir);
			newBorder.add(newCell);//Wraps appropriate neighboring cell; adds it to the new border.
			screen.putCell(newCell.getCell());//Adds new cell to the screen.
			
			//Gates for removing off-screen cells.
			if(bc.isOffScreen(dir))
				screen.removeCell(bc.getCell());
			
		}
		border = newBorder;
		
	}
	
	
	////////////////////////////// DeBugging /////////////////////////////////////
	
	void display() {
		Iterator<Cell> borderIt = border.iterator();
		System.out.println("******************* New Border *****************************");
		while(borderIt.hasNext()) {
			System.out.println(borderIt.next());
		}
	}
	
}

























