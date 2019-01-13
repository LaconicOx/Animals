package model.board.node_states;

import java.util.concurrent.ThreadLocalRandom;

import game.Directions.Direction;
import model.Pattern;
import model.board.Node;


public class BorderWrapper extends OnStates{
	
	//Class Fields
	private static final double WIND_FORCE = 10.0;
	private static final double WIND_PROB = 0.001;
	
	//Instance Fields
	private Pattern pattern;
	private Pattern updatePattern;
	private boolean stripFlag;
	
	//////////////////////////Constructor and Initializers ////////////////////////////
	
	public BorderWrapper(Active active) {
		super(active);
		pattern = null;
		updatePattern = null;
		stripFlag = false;
	}
	
	public void initPattern() {
		if(pattern == null) {
			Direction[] directions = Direction.getNodeDirections();
			Pattern.Builder patBuild = Pattern.Builder.getInstance();
			
			System.out.println("******************* " + active + "*************************");
			
			Node neighbor;
			for(int i = 0; i < 6; i++) {
				neighbor = active.getNeighbor(directions[i]);
				
				System.out.println(neighbor);
				System.out.println("Acitve: " + neighbor.checkActive() + ", Border: " + neighbor.checkBorder() + ", Off: " + neighbor.checkOff());
				
				if(neighbor.checkActive()) {
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
			
			System.out.println("******************* " + pattern + "*************************");
			
			
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
			active.receiveWind(dir.scaledVector(WIND_FORCE));
		}
	}
	
	@Override
	public final Node shift(Direction toward) {
		
		//Transfer border wrapper to the toward node.
		Node neighNode = active.getNeighbor(toward);
		On neighOn = neighNode.getOn();
		neighOn.transfer(this);
		neighNode.setState(neighOn);
		
		//Changes the current node's internal state based on the state of the node
		//in the direction opposite of the toward node.
		Direction opposite = Direction.getOpposite(toward);
		
		//If the opposite node is INTERIOR, this border wrapper should be stripped off.
		if(pattern.checkInterior(opposite)) {
			
		}
		//If the opposite node is OFF, this node should be turned off.
		else if(pattern.checkOff(opposite)) {
			active.turnOff();
		}
		//If the opposite node is BORDER, the node in that direction will transfer its state to this node,
		//this wrapper must be stripped off.
		else if(pattern.checkBorder(opposite)) {
		}
		else {
			System.err.println("Error in shift: unhandled state");
			System.exit(0);
		}
		
		stripFlag = true;
		return neighNode;
	}//End of Shift
	
	final void transfer(BorderWrapper wrap) {
		updatePattern = wrap.getPattern();
	}
	
	@Override
	public final boolean update() {
		
		if(stripFlag) {
			return true;
		}
		else {
			//Changes pattern if transfered.
			if(updatePattern != null) {
				pattern = updatePattern;
				updatePattern = null;
			}
			initPattern();
			genWind();
			
		if(active.update())
			active = active.getWrapped();
		//active.sendBorder();
			return false;
		}
		
	}
	
	///////////////////////////////// Checkers /////////////////////////////////////
	
	public boolean checkDuplicates(boolean previous) {
		//TODO Strip out of final version
		if(previous) {
			return true;
		}
		else {
			return active.checkDuplicates(true);
		}
	}
	
	@Override
	public final boolean checkInterior() {
		return false;
	}
	
	@Override
	public final boolean checkBorder() {
		return true;
	}
	
	//////////////////////////////////Object Overrides ///////////////////////////////
	
	@Override
	public final String toString() {
		return active.toString() + " with a border wrapper";
	}
	
}
