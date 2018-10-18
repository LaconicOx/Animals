package model.cell;

import java.util.HashSet;

import model.ModelUtility.Direction;
import model.ModelUtility.Edge;


final class Top extends BorderCell{
	Top(Cell cc){super(cc, new HashSet<Direction>(Edge.TOP.getOpposites()));}
	
	protected Cell wrapCell(Cell c) {
		return stored.wrapCell(new Top(c));
	}
	
	@Override
	public String toString() {
		return "Top(" + stored.toString() + ")";
	}
}
