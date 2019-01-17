package model.screen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import game.Directions.Direction;
import model.nodes.ConcreteNode;
import view.ViewInterface;

public class Screen {

	//State fields
	private final ScreenDilate dilate;
	private final ScreenShift shift;
	private final ScreenNormal normal;
	private final ScreenStart start;
	
	//Instance Fields
	private final HashMap<Double, Row> screenRows;
	private ScreenState state;
		
	////////////////////////// Constructors and Initializers //////////////////////////
	public Screen(ViewInterface view){
		screenRows = new HashMap<>();
		
		//Initializes states
		dilate = new ScreenDilate(this, view);
		shift = new ScreenShift(this);
		normal = new ScreenNormal(this);
		start = new ScreenStart(this, view);
		state = start;
		
	}
		
	////////////////////////// Accessor Methods /////////////////////////////
		
	final Iterator<ConcreteNode> getBorderIterator(){
		return new BorderIterator(screenRows);
	}
	
	final Iterator<ConcreteNode> getScreenIterator(){
		return new ScreenIterator(screenRows);
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
	
	final void add(ConcreteNode node) {
		
		double key = node.getCenter()[1];
		if(screenRows.containsKey(key)) {
			screenRows.get(key).add(node);
		}
		else {
			screenRows.put(key, new Row(node));
		}
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
	
	public void update() {
		state.update();
	}
		
		
		
		
	////////////////////////// Checker Methods ////////////////////////////////
	
	
	
	///////////////////////// Inner Classes ///////////////////////////////
	public class BorderIterator implements Iterator<ConcreteNode>{
		//TODO: Temporary fix. Rewrite this class
		private final Iterator<ConcreteNode> storedIt;
		private Set<ConcreteNode> stored;
		
		public BorderIterator(TreeSet<ConcreteNode> nodes) {
			Iterator<ConcreteNode> nodeIt = nodes.iterator();
			stored = new HashSet<>();
			ConcreteNode n;
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
		public ConcreteNode next() {
			return storedIt.next();
		}
	}//End of BorderIterator
	
	/**
	 * Iterates through every nodes stored in Screen.
	 * @author dvdco
	 *
	 */
	public class ScreenIterator implements Iterator<ConcreteNode>{
		private final Iterator<ConcreteNode> screenIt;
		
		public ScreenIterator(TreeSet<ConcreteNode> nodes) {
			screenIt = nodes.iterator();
		}

		@Override
		public boolean hasNext() {
			return screenIt.hasNext();
		}

		@Override
		public ConcreteNode next() {
			return screenIt.next();
		}
	}
	
	//////////////////////////// Inner Class////////////////////////
	
	private class Row implements Comparable<Row>{
		
		private final TreeSet<ConcreteNode> row;
		private final double yComponent;
		
		Row(ConcreteNode first){
			row = new TreeSet<>();
			yComponent = first.getCenter()[1];
			row.add(first);
		}
		
		///////////////////// Accessors ////////////////////
		
		final ConcreteNode getFirst() {
			return row.first();
		}
		
		final ConcreteNode getLast() {
			return row.last();
		}
		
		final double getYComp() {
			return yComponent;
		}
		
		final Iterator<ConcreteNode> getIterator(){
			return row.iterator();
		}
		
		///////////////////// Mutator ///////////////////////
		
		final void add(ConcreteNode node) {
			if(node.getCenter()[1] == yComponent) {
				row.add(node);
			}
			else {
				System.err.println("Error in Screen.Row.add");
				System.exit(0);
			}
		}
		
		final void remove(ConcreteNode node) {
			if(!row.remove(node)) {
				System.err.println("Error in Screen.Row.remove");
				System.exit(0);
			}
		}
		
		/////////////////// Checkers /////////////////////////
		
		final boolean checkY(double yComp) {
			if(yComp == yComponent) {
				return true;
			}
			else {
				return false;
			}
		}
		
		/////////////////// Object Overrides /////////////////////
		
		@Override
		public int compareTo(Row other) {
			
			int lesser = -1;
			int equal = 0;
			int greater = 1;
			
			double otherY = other.getYComp();
			
			if(yComponent < otherY)
				return lesser;
			else if (yComponent > otherY)
				return greater;
			else
				return equal;
		}
		
		@Override
		public boolean equals(Object other) {
			if(other.getClass() != Row.class)
				return false;
			
			Row otherRow = (Row)other;
			if(yComponent == otherRow.getYComp())
				return true;
			else
				return false;
		}
		
		@Override
		public int hashCode() {
			
			return Objects.hash(yComponent);
		}
		
		
		
		
		
	}
	
	//////////////////////////// Debugging ////////////////////////////
	public void display() {
		Iterator<ConcreteNode> screenIt = getScreenIterator();
		System.out.println("***************************************");
		while(screenIt.hasNext())
			System.out.println(screenIt.next());
		System.out.println("***************************************");
	}
	
}//End of Screen
