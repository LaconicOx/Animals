package model.cell;

import model.node.Node;

public class BorderCell extends Cell{
	
	public BorderCell(Node node){
		super(node);
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
