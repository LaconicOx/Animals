package model.node;


import java.util.HashMap;
import java.util.HashSet;



public class TotalBoard {
	/*
	 * Singleton class for creating and storing the game board.
	 */
	
	private static TotalBoard uniqueInstance;
	private static HashMap<NodeKey, Node> board;
	
	/////////////////// Singleton Constructor ////////////////////////
	private TotalBoard() {
		board = new HashMap<NodeKey, Node>();
	}
	
	public static TotalBoard getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new TotalBoard();
			}
		return uniqueInstance;
	}
	

	///////////////////// public Methods /////////////////////////////
	
	public static Node getNode(NodeKey k) {
		/*
		 * Returns reference to node for key p.
		 * Returns a null object if no node is found.
		 */
		return board.get(k);
	}
	
	public static HashSet<Node> getAllNodes(){
		HashSet<Node> output = new HashSet<>();
		output.addAll(board.values());
		
		return output;
	}
	
	static void putNode(Node n) {
		if(board.containsKey(n.getCenter()))
			System.err.println("Error: model already conatins a node at " + n);
		else {
			board.put(n.getCenter(), n);
		}
	}
	
	public String toString() {
		
		StringBuilder output = new StringBuilder();
		for (NodeKey key : board.keySet()) {
			
			output.append("Key " + key.toString() + " maps to node " + board.get(key).toString() + ".\n");
		}
		
		return output.toString();
	}
}
