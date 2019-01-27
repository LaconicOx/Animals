package model.nodes;



import game.Directions.Direction;

public interface Node {
	
	////////////////////// Accessors ///////////////////////////
	public double[] getCenter();
	public Node getNeighbor(Direction dir);
	public double getScent();
	
	//////////////////// Mutators ////////////////////////////////
	public double eat(double rate);
	public void setBorder();
	public void setInterior();
	public void setOff();
	public void setScent(double scent);
	public void transfer(Node node);
	public boolean update();
	
	///////////////////// Checkers //////////////////////////////
	public boolean checkBorder();
	public boolean checkBorder(Direction dir);
	public boolean checkInterior();
	public boolean checkInterior(Direction dir);
	public boolean checkOff();
	public boolean checkOff(Direction dir);
	public boolean checkPassable();
	public boolean checkPoint(double[] point);
	
}
