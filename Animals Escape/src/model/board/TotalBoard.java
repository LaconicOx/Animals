package model.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.Directions.Direction;
import model.ModelKey;
import view.ViewInterface;


public class TotalBoard {
	/*
	 * Singleton class for creating and storing the game board.
	 */
	
	private static ViewInterface view;
	private static TotalBoard uniqueInstance;
	private static Map<ModelKey, Node> board;
	
	/////////////////// Singleton Constructor ////////////////////////
	private TotalBoard(ViewInterface view) {
		TotalBoard.view = view;
		board = new HashMap<ModelKey, Node>();
	}
	
	public static TotalBoard getInstance(ViewInterface view) {
		if (uniqueInstance == null) {
			uniqueInstance = new TotalBoard(view);
			}
		return uniqueInstance;
	}
	
	

	///////////////////// Accessor Methods /////////////////////////////
	
	/**
	 * Gets node from board. 
	 * @param key
	 * @param nullReturn - true allows for null returns. False will create new node
	 * 	 instead of node.
	 * @return
	 */
	public static Node getNode(ModelKey key, boolean nullReturn) {
		
		//Gate for nullReturn
		if(nullReturn)
			return board.get(key);
		else {
			Node n = board.get(key);
			if(n != null) {
				return n;
			}
			else {
				HexType type = initType(key);
				Node output = null;
				
				switch(type) {
				case GRASS: output = new GrassNode(key, view);
					break;
				case BUSH: output = new BushNode(key, view);
					break;
				case OAK: output = new TreeNode(key, view);
					break;
				case ROCK: output = new RockNode(key, view);
					break;
				}
				putNode(output);//Assures all nodes have been entered into the TotalBoard
				return output;
			}
		}
	}
	
	public static HashSet<Node> getAllNodes(){
		HashSet<Node> output = new HashSet<>();
		output.addAll(board.values());
		
		return output;
	}
	
	////////////////////// Helper Methods //////////////////////////////
	static void putNode(Node n) {
		if(board.containsKey(n.getNodeKey()))
			System.err.println("Error in TotalBoard.putNode(): model already conatins a node at " + n);
		else {
			board.put(n.getNodeKey(), n);
		}
	}
	
	private static HexType initType(ModelKey key) {
		Direction[] directions = {Direction.NE, Direction.N, Direction.NW, Direction.SW, Direction.S, Direction.SE};
		Set<Node> neighbors = new HashSet<>();
		
		for (Direction dir : directions) {
			neighbors.add(getNode(key.getNeighborKey(dir), true));
		}
		neighbors.remove(null);//Must remove null because it counts as a set element.
		
		//Gates for the first node, which should be grass.
		if(neighbors.isEmpty())
			return HexType.GRASS;
		else {
			List<HexType> deck = new ArrayList<>();
			//TODO Do more research on the forEach method and lambda expressions.
			//Is EnumSet a raw type?
			EnumSet.allOf(HexType.class).forEach(hex ->deck.addAll(hex.getCards()));
			
			Iterator<Node>neighIt = neighbors.iterator();
			while(neighIt.hasNext()) {
				Node n = neighIt.next();
				if(n.getClass() == GrassNode.class)
					deck.addAll(HexType.GRASS.getCards());
				else if(n.getClass() == BushNode.class)
					deck.addAll(HexType.BUSH.getCards());
				else if(n.getClass() == TreeNode.class)
					deck.addAll(HexType.OAK.getCards());
				else if(n.getClass() == RockNode.class)
					deck.addAll(HexType.ROCK.getCards());
				else
					System.err.println("Unrecognized type in initType.");
			}
			Collections.shuffle(deck);
			return deck.get(0);
		}
		
	}//End of InitType()
	
	
	////////////////////////// Overrides ///////////////////////////////
	public String toString() {
		
		StringBuilder output = new StringBuilder();
		for (ModelKey key : board.keySet()) {
			
			output.append("Key " + key.toString() + " maps to node " + board.get(key).toString() + ".\n");
		}
		
		return output.toString();
	}
	
	///////////////////////// Inner Class /////////////////////////////
	
	private enum HexType { 
		/*
		 * This enum helps represent node selection on the above methods.
		 * The cards are used to control the frequencies of node types.
		 */
		GRASS(15), OAK(10), ROCK(5), BUSH(10);
		
		private int cards;//number of copies to be returned by getCards. 
		
		HexType(int cards) {
			this.cards = cards;
		}
		
		public List<HexType> getCards(){
			return Collections.nCopies(cards, this);
		}
		
	}
}
