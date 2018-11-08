package model.screen;

import java.util.HashMap;
import java.util.Objects;

import model.ModelParameters.Direction;
import model.board.Node;

public class BorderCell extends Cell{
	
	private enum State{NULL, SCREEN, BORDER};
	HashMap<Direction, State> orientation = null;
	
	public BorderCell(Node node){
		super(node);
	}
	
	///////////////////////////// Mutator Methods //////////////////////////
	
	BorderCell initBorderCell(Direction toward) {
		Node n = getNeighborNode(toward);
		 return new BorderCell(n);
	}
	
	ScreenCell initScreenCell(Direction toward) {
		return new ScreenCell(getNeighborNode(toward));
	}
	
	///////////////////////////// Accessor Methods ///////////////////////////
	
	Instruction getShiftInstruction(Direction dir) {
		Instruction output = new Instruction(this, dir);
		
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
		
		if(away == State.NULL && toward == State.NULL) {
			Node n = getNeighborNode(Direction.getOpposite(dir));
			System.out.println("Node: " + n + " Reference: " + n.getCell() );
		}
			
		//System.out.println(this + " Toward: " + toward + " Away: " + away);
		
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
	public boolean isContained(double[] coords) {
		System.err.println("Error: checked border for containing player");
		return false;
	}
	
	@Override
	public boolean isPassable() { return true; }

	@Override
	public String toString() {
		double[] cen = getCenter();
		return "BorderCell (" + cen[0] + "," + cen[1] + ") maps to " + node.toString();
	}
	
	@Override
	public boolean equals(Object ob) {
		double[] curCenter = getCenter();
		if (ob.getClass() != this.getClass())
			return false;
		BorderCell obCell = (BorderCell)ob;
		double[] othCenter = obCell.getCenter();
		if((curCenter[0] == othCenter[0] && (curCenter[1] == othCenter[1])))
			return true;
		else return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(node);
	}
	
	/////////////////////////////// Debugging ///////////////////////////////////////////////////
	
	@Override
	public void display() {
		double[] cen = getCenter();
		System.out.println("BorderCell (" + cen[0] + "," + cen[1] + ") maps to " + node.toString());
		
	}

}//End of BorderCell
