package model.screen;

import java.util.HashMap;

import model.ModelUtility.Direction;
import model.board.Node;

public class BorderCell extends Cell{
	
	private enum State{NULL, SCREEN, BORDER};
	private HashMap<Direction, State> orientation = null;
	
	public BorderCell(Node node){
		super(node);
	}
	
	BorderCell(Node node, HashMap<Direction, State> orientation) {
		super(node);
		this.orientation = orientation;
		
	}
	
	///////////////////////////// Mutator Methods //////////////////////////
	
	BorderCell initBorderCell(Direction toward) {
		Node n = getNeighborNode(toward);
		 return new BorderCell(n, orientation);
	}
	
	ScreenCell initScreenCell(Direction toward) {
		return new ScreenCell(getNeighborNode(toward));
	}
	
	///////////////////////////// Accessor Methods ///////////////////////////
	
	Instruction getShiftInstruction(Direction dir) {
		Instruction output = new Instruction(this, dir);
		
		//Gates for orientation's initializaion.
		//if (orientation == null)
		updateOrientation();
		
		//Builds instruction for creating a cell at the node in the direction of dir.
		State toward = orientation.get(dir);
		if (toward == State.NULL)
			output.nullToBorder();
		else if (toward == State.SCREEN)
			output.screenToBorder();
		else if (toward == State.BORDER)
			output.keep();
		else
			System.err.println("Error in getInstruction(): unrecognized choice.");
		
		//Builds instruction for creating a cell at the node for this cell.
		State away = orientation.get(Direction.getOpposite(dir));
		if (away == State.NULL)
			output.selfToNull();
		else if (away == State.SCREEN)
			output.selfToScreen();
		else if (away == State.BORDER)
			output.keep();
		else
			System.err.println("Error in getInstruction(): unrecognized choice.");
		
		return output;
	}
	
	private void updateOrientation() {
		orientation = new HashMap<>();
		for (Direction dir : Direction.values()) {
			Cell test = getNeighborCell(dir, true);
			if (test == null)
				orientation.put(dir, State.NULL);
			else if (test.getClass() == ScreenCell.class)
				orientation.put(dir, State.SCREEN);
			else if (test.getClass() == BorderCell.class)
				orientation.put(dir, State.BORDER);
			else
				System.err.println("Error: Unrecognized type in BorderCell.updateOrientation");
		}
	}
	
	
	
	///////////////////////////////// Overrides ////////////////////////////////
	
	@Override
	public boolean isContained(double x, double y) {
		System.err.println("Error: checked border for containing player");
		return false;
	}

	@Override
	public String toString() {
		return "BorderCell (" + getX() + "," + getY() + ") maps to " + node.toString();
		//return "Cell (" + center[0] + "," + center[1] + ")";
	}

	@Override
	public void display() {
		System.out.println("BorderCell (" + getX() + "," + getY() + ") maps to " + node.toString());
		
	}

}//End of BorderCell
