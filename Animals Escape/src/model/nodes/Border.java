package model.nodes;

import java.util.concurrent.ThreadLocalRandom;

import game.Directions.Direction;


class Border extends BaseWrapper{
	
	//Class Fields
	private static final double WIND_FORCE = 10.0;
	private static final double WIND_PROB = 0.001;
	
	//Instance Fields
	private Pattern pattern;
	
	//////////////////////////Constructor and Initializers ////////////////////////////
	
	Border(NodeState state) {
		super(state);
		if(!state.checkInterior()) {
			System.err.println("Error in Border.constructor");
			System.exit(0);
		}
		pattern = null;
	}
	
	void initPattern() {
		if(pattern == null) {
			Direction[] directions = Direction.getNodeDirections();
			Pattern.Builder patBuild = Pattern.Builder.getInstance();
			
			
			Node neighbor;
			for(int i = 0; i < 6; i++) {
				neighbor = state.getNeighbor(directions[i]);
				
				if(neighbor.checkInterior()) {
					patBuild.interior(directions[i]);
				}
				else if(neighbor.checkBorder()) {
					patBuild.border(directions[i]);
				}
				else if(neighbor.checkOff()) {
					patBuild.off(directions[i]);
				}
				else {
					System.err.println("Error in Border.initState: unhandled state.");
					System.exit(0);
				}
			}
			
			pattern = patBuild.build();
			
		}
		
	}//End of initPattern()
	
	/////////////////////////// Accessors //////////////////////////////////////////
	
	final Pattern getPattern(){
		return pattern;
	}
	
	//////////////////////////// Mutators /////////////////////////////////////

	private final void genWind() {
		
		double prob = ThreadLocalRandom.current().nextDouble(1.0);
		if (prob <= WIND_PROB) {
			Direction dir = pattern.getRandomDir();
			state.setWind(dir.scaledVector(WIND_FORCE));
		}
	}
	
	final void transfer(Pattern pat) {
		pattern = pat;
	}
	
	@Override
	final boolean update() {
		if(pattern == null) {
			initPattern();
		}
		genWind();
		//state.update();
		state.sendBorder();
		
		return true;
	}
	
	///////////////////////////////// Checkers /////////////////////////////////////


	
	@Override
	final boolean checkBorder() {
		return true;
	}
	
	//////////////////////////////////Object Overrides ///////////////////////////////
	
	@Override
	public final String toString() {
		return state.toString() + " with a border wrapper";
	}

	

	
	
}
