package model.node;


import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import model.ModelUtility.Direction;

public class NodeFactory {
	
	//TODO This version is specifically written for TotalBoard factory
	//It should be deleted when TotalBoard is rewritten.
	public static void getNodeInstance(NodeKey nk) {
		HexType type = initType(nk);
		Node output = null;
		
		switch(type) {
		case GRASS: output = new GrassNode(nk);
			break;
		case BUSH: output = new BushNode(nk);
			break;
		case OAK: output = new OakNode(nk);
			break;
		case ROCK: output = new RockNode(nk);
			break;
		}
		TotalBoard.putNode(output);//Assures all nodes have been entered into the TotalBoard
	}
	
	public static Node getNodeInstance(int x, int y) {
		NodeKey nk = new NodeKey(x, y);
		HexType type = initType(nk);
		Node output = null;
		
		switch(type) {
		case GRASS: output = new GrassNode(nk);
			break;
		case BUSH: output = new BushNode(nk);
			break;
		case OAK: output = new OakNode(nk);
			break;
		case ROCK: output = new RockNode(nk);
			break;
		}
		TotalBoard.putNode(output);//Assures all nodes have been entered into the TotalBoard
		return output;
	}
	
	
	private static HexType initType(NodeKey key) {
		/*
		 * 
		 */
		
		Set<Node> neighbors = new HashSet<>();
		int x = key.getX();
		int y = key.getY();
		
		for (Direction dir : EnumSet.allOf(Direction.class)) {
			neighbors.add(TotalBoard.getNode(new NodeKey(dir.getNeighborCoord(x, y))));
		}
		neighbors.remove(null);//Must remove null because it counts as a set element.
		//System.out.println(neighbors.size());
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
				else if(n.getClass() == OakNode.class)
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
	
	
	private static enum HexType { 
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
