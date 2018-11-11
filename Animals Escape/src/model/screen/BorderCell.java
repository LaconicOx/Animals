package model.screen;

import java.util.HashMap;

import model.Directions.Direction;
import units.CellKey;

public class BorderCell extends Cell{
	
	private enum State{NULL, SCREEN, BORDER};
	HashMap<Direction, State> orientation = null;
	Screen screen;
	
	public BorderCell(CellKey key){
		super(key);
		screen = Screen.getInstance();
	}
	
	///////////////////////////// Mutator Methods //////////////////////////
	

	
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
			System.err.println("Error in getShiftInstructions(): Null, null orientation" );
		}
			
		//System.out.println(this + " Toward: " + toward + " Away: " + away);
		
		return output;
	}
	
	private void updateOrientation() {
		orientation = new HashMap<>();
		for (Direction dir : Direction.values()) {
			CellKey test = getNeighborKey(dir);

			if (screen.checkScreen(test))
				orientation.put(dir, State.SCREEN);
			else if (screen.checkBorder(test))
				orientation.put(dir, State.BORDER);
			else
				orientation.put(dir, State.NULL);
		}
	}
	
	
	
	///////////////////////////////// Overrides ////////////////////////////////
	
	@Override
	public boolean isContained(double[] coords) {
		System.err.println("Error: checked border for containing player");
		return false;
	}
	
	@Override
	public boolean checkPassable() { return true; }

	@Override
	public String toString() {
		double[] cen = getCenter();
		return "BorderCell (" + cen[0] + "," + cen[1] + ") maps to " + super.toString();
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
	
	
	/////////////////////////////// Debugging ///////////////////////////////////////////////////
	
	@Override
	public void display() {
		double[] cen = getCenter();
		System.out.println("BorderCell (" + cen[0] + "," + cen[1] + ") maps to " + super.toString());
		
	}

}//End of BorderCell
