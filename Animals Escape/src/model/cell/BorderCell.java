package model.cell;

import java.util.HashMap;

import model.ModelUtility.Direction;
import model.node.Node;

public class BorderCell extends Cell{
	
	private enum State{EMPTY, SCREEN, BORDER};
	HashMap<Direction, State> orientation = null;
	
	public BorderCell(Node node){
		super(node);
	}
	
	public BorderCell(Node node, HashMap<Direction, State> orientation) {
		super(node);
		this.orientation = orientation;
		
	}
	
	///////////////////////////// Accessor Methods //////////////////////////
	
	public BorderCell initCopy() {
		return new BorderCell(node, orientation);
	}
	
	public BorderCell initBorder(Direction dir) {
		return  new BorderCell(getNeighborNode(dir), orientation);
	}
	
	public ScreenCell initScreen(Direction dir) {
		return new ScreenCell(getNeighborNode(dir));
	}
	
	///////////////////////////// Mutator Methods ///////////////////////////
	
	private void updateOrientation() {
		orientation = new HashMap<>();
		for (Direction dir : Direction.values()) {
			Cell test = getNeighborCell(dir, true);
			if (test == null)
				orientation.put(dir, State.EMPTY);
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
}
