package model.board.node_states;

import game.Directions.Direction;
import model.board.Node;

public interface Active {
	
	
	public double eat(double amount);
	public Node getNeighbor(Direction dir);
	public double getScent();
	public Active getWrapped();
	
	public void advance();
	public void receiveScent(double scent);
	public void receiveWind(double[] wind);
	public void sendBorder();
	public void setTileFacing(Direction facing);
	public Node shift(Direction toward);
	public void turnOff();
	public boolean update();
	
	public boolean checkDuplicates(boolean perivous);
	public boolean checkBorder();
	public boolean checkInterior();
	public boolean checkOff();
	public boolean checkResting();
}
