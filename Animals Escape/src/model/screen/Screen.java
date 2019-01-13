package model.screen;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import game.Directions.Direction;
import model.board.Node;
import view.ViewInterface;

public class Screen {

	//State fields
	private final ScreenDilate dilate;
	private final ScreenShift shift;
	private final ScreenNormal normal;
	private final ScreenStart start;
	
	//Instance Fields
	private TreeSet<Node> screenNodes;
	private ScreenState state;
		
	////////////////////////// Constructors and Initializers //////////////////////////
	public Screen(ViewInterface view){
		screenNodes = new TreeSet<>();
		
		//Initializes states
		dilate = new ScreenDilate(this, view);
		shift = new ScreenShift(this);
		normal = new ScreenNormal(this);
		start = new ScreenStart(this, view);
		state = start;
		
	}
		
	////////////////////////// Accessor Methods /////////////////////////////
		
	final Iterator<Node> getBorderIterator(){
		return new BorderIterator(screenNodes);
	}
	
	final Iterator<Node> getScreenIterator(){
		return new ScreenIterator(screenNodes);
	}
	
	final ScreenState getStateDilate() {
		return dilate;
	}
	
	final ScreenState getStateNormal() {
		return normal;
	}
	
	final ScreenState getStateShift() {
		return shift;
	}
		
	////////////////////////// Mutator Methods /////////////////////////////////
	
	final void add(Node node) {
		screenNodes.add(node);
	}
	
	final void add(Set<Node> nodes) {
		screenNodes.addAll(nodes);
	}
	
	public void dilate(double factor) {
		state.dilate(factor);
	}
	
	final void setState(ScreenState state) {
		this.state = state;
	}
	
	public void shift(Direction toward) {
		state.shift(toward);
	}
	
	/**
	 * This method repositions nodes with the screen by adding them to a new treeset.
	 * TreeSet entries are ordered when objects are added, so repositioning the nodes requires
	 * adding them to a new TreeSet. Also, creating a new Treeset avoid concurrent modification issues.
	 */
	final void reposition() {
		TreeSet<Node> newScreenNodes = new TreeSet<>();
		Iterator<Node> oldIt = screenNodes.iterator();
		Node current;
		while(oldIt.hasNext()) {
			current = oldIt.next();
			current.update();
			//Nodes in Off states should be removed.
			if(!current.checkOff()) {
				newScreenNodes.add(current);
			}
		}
		screenNodes = newScreenNodes;
	}
	
	public void update() {
		state.update();
	}
		
		
		
		
	////////////////////////// Checker Methods ////////////////////////////////
	
	public boolean checkNode(Node node) {
		if(screenNodes.contains(node))
			return true;
		else
			return false;
	}
	
	
	///////////////////////// Inner Classes ///////////////////////////////
	public class BorderIterator implements Iterator<Node>{
		//TODO: Temporary fix. Rewrite this class
		private final Iterator<Node> storedIt;
		private Set<Node> stored;
		
		public BorderIterator(TreeSet<Node> nodes) {
			Iterator<Node> nodeIt = nodes.iterator();
			stored = new HashSet<>();
			Node n;
			while(nodeIt.hasNext()) {
				n = nodeIt.next();
				if(n.checkBorder())
					stored.add(n);
				else
					break;
			}
			storedIt = stored.iterator();
		}

		@Override
		public boolean hasNext() {
			return storedIt.hasNext();
		}

		@Override
		public Node next() {
			return storedIt.next();
		}
	}//End of BorderIterator
	
	/**
	 * Iterates through every nodes stored in Screen.
	 * @author dvdco
	 *
	 */
	public class ScreenIterator implements Iterator<Node>{
		private final Iterator<Node> screenIt;
		
		public ScreenIterator(TreeSet<Node> nodes) {
			screenIt = nodes.iterator();
		}

		@Override
		public boolean hasNext() {
			return screenIt.hasNext();
		}

		@Override
		public Node next() {
			return screenIt.next();
		}
	}
	
	//////////////////////////// Debugging ////////////////////////////
	public void display() {
		Iterator<Node> screenIt = screenNodes.iterator();
		System.out.println("***************************************");
		while(screenIt.hasNext())
			System.out.println(screenIt.next());
		System.out.println("***************************************");
	}
		
}//End of Screen
