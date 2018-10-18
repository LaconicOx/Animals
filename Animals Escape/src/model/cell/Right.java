package model.cell;

import java.util.HashSet;

import model.ModelUtility.Direction;
import model.ModelUtility.Edge;

final class Right extends BorderCell{
	Right(Cell cc){super(cc, new HashSet<Direction>(Edge.RIGHT.getOpposites()));}
	
	protected Cell wrapCell(Cell c) {
		return stored.wrapCell(new Right(c));
	}
	
	@Override
	public String toString() {
		return "Right(" + stored.toString() + ")";
	}
}
