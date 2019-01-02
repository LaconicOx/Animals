package model.screen;

import model.board.Node;

public class BorderCell extends Cell{
	
	public BorderCell(Node node){
		super(node);
	}
	
	///////////////////////////// Mutator Methods //////////////////////////
	
	@Override
	public void update() {
		node.genWind();
	};
	
	///////////////////////////// Accessor Methods ///////////////////////////
	
	///////////////////////////////// Overrides ////////////////////////////////
	
	@Override
	public boolean checkPoint(double[] coords) {
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
	

}//End of BorderCell
