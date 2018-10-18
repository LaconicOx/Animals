package model.cell;

import java.util.HashSet;

import model.ModelUtility.Direction;
import model.ModelUtility.Edge;

final class Left extends BorderCell{
	Left(Cell cc){super(cc, new HashSet<Direction>(Edge.LEFT.getOpposites()));}

	
	protected Cell wrapCell(Cell c) {
		return stored.wrapCell(new Left(c));
	}
	
	@Override
	public String toString() {
		return "Left(" + stored.toString() + ")";
	}
}
