package model;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


public final class ModelUtility {
	
	private ModelUtility() { throw new AssertionError();}
	
	public enum Direction{
		NE(1,1),
		N(0,2),
		NW(-1,1),
		SW(-1,-1),
		S(0,-2),
		SE(1,-1);
		
		private int x;
		private int y;
		
		Direction(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		
		public int[] getNeighborCoord(int x, int y) {
			int[] coord = {x + this.x, y + this.y};
			return coord;
		}
		
		
		
		static Direction getOpposite(Direction dir) {
			Direction output = null;
			switch(dir) {
				
				case NE: output = Direction.SW;
					break;
				case N: output = Direction.S;
					break;
				case NW: output = Direction.SE;
					break;
				case SW: output = Direction.NE;
					break;
				case S: output = Direction.N;
					break;
				case SE: output = Direction.NW;
					break;
			}
			return output;
		}
	}//End of Direction
	
	public enum Edge{
		TOP((new HashSet<Direction>(List.of(Direction.NE, Direction.SE))), new HashSet<Direction>(List.of(Direction.S, Direction.SW, Direction.SE))),
			BOTTOM((new HashSet<Direction>(List.of(Direction.NW, Direction.SW))), new HashSet<Direction>(List.of(Direction.N, Direction.NE, Direction.NW))),
			RIGHT((new HashSet<Direction>(List.of(Direction.S))), new HashSet<Direction>(List.of(Direction.NW, Direction.SW))),
			LEFT((new HashSet<Direction>(List.of(Direction.N))), new HashSet<Direction>(List.of(Direction.NE, Direction.SE)));
		
		private Set<Direction> moves;
		private Set<Direction> opposites;
		Edge(Set<Direction> moves, Set<Direction> opposites) {
			this.moves = moves;
			this.opposites = opposites;
			}
		
		public Set<Direction> getMoves() {return moves;}
		
		public Set<Direction> getOpposites(){return opposites;}
		
		
	};//End of Edge
}

































