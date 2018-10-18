package model.cell;

import java.util.HashSet;

import model.ModelUtility.Direction;
import model.ModelUtility.Edge;

final class Bottom extends BorderCell{
	Bottom(Cell cc){super(cc, new HashSet<Direction>(Edge.BOTTOM.getOpposites()));}

	protected Cell wrapCell(Cell c) {
		return stored.wrapCell(new Bottom(c));
	}

	@Override
	public String toString() {
		return "Bottom(" + stored.toString() + ")";
	}
}
