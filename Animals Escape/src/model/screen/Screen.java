package model.screen;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import game.Directions.Direction;
import model.nodes.Node;
import view.ViewInterface;


public class Screen {

	//State fields
	private final ScreenDilate dilate;
	private final ScreenShift shift;
	private final ScreenNormal normal;
	private final ScreenStart start;
	
	//Instance Fields
	private final TreeMap<Double, Row> screenRows;
	private ScreenState state;
		
	////////////////////////// Constructors and Initializers //////////////////////////
	public Screen(ViewInterface view){
		screenRows = new TreeMap<>();
		
		//Initializes states
		dilate = new ScreenDilate(this, view);
		shift = new ScreenShift(this);
		normal = new ScreenNormal(this);
		start = new ScreenStart(this, view);
		state = start;
		
	}
		
	////////////////////////// Accessor Methods /////////////////////////////
		
	final Iterator<Node> getShiftIterator(){
		return new ShiftIterator(screenRows);
	}
	
	final Iterator<Node> getScreenIterator(){
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
	
	final void add(Node node) {
		
		double key = node.getCenter()[1];
		if(screenRows.containsKey(key)) {
			screenRows.get(key).add(node);
		}
		else {
			screenRows.put(key, new Row(node));
		}
	}
	
	public final void dilate(double factor) {
		state.dilate(factor);
	}
	
	final void remove(Node node) {
		Row row = screenRows.get(node.getCenter()[1]);
		if(row != null) {
			row.remove(node);
		}
		else {
			//TODO error code
		}
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
	abstract class BorderIterator implements Iterator<Node>{
		
		protected NavigableSet<Node> collection;
		protected Node next;
		
		protected abstract NavigableSet<Node> initSet();
		
		protected NavigableSet<Node> initCollection(TreeMap<Double, Row> collection) {
			NavigableSet<Node> output = initSet();
			
			NavigableSet<Double> keys = collection.navigableKeySet();
			int size = keys.size();
			double key;
			for(int i = 0; i < size; i++) {
				key = keys.pollFirst();
				//The top border spans the topmost two rows.
				if(i < 2) {
					output.addAll(collection.get(key).getAll());
				}
				//For the middle rows, only the first and last nodes are on the border.
				else if(i < size - 2) {
					Row row = collection.get(key);
					output.add(row.getFirst());
					output.add(row.getLast());
				}
				//the bottom border spans the bottom-most two rows.
				else {
					output.addAll(collection.get(key).getAll());
				}
			}
			return output;
		}
		
		////////////////////////// Overrides /////////////////////////////
		
		@Override
		public boolean hasNext() {
			if(next != null) {
				return true;
			}
			else
				return false;
		}

		@Override
		public Node next() {
			Node output = next;
			next = collection.higher(output);
			return output;
		}
		
	}//End of BorderIterator
	
	class ShiftIterator extends BorderIterator{
		//Must add code for comparators based on shift direction.
		ShiftIterator(TreeMap<Double, Row> collection){
			this.collection = initCollection(collection);
			next = this.collection.first();
		}
		
		@Override
		protected final NavigableSet<Node> initSet() {
			return new TreeSet<Node>();
		}
	}//End of ShiftIterator
	
	/**
	 * Iterates through every nodes stored in Screen.
	 * @author dvdco
	 *
	 */
	class ScreenIterator implements Iterator<Node>{
		
		private final TreeMap<Double, Row> collection;
		private double key;
		private Node next;
		private Iterator<Double> keyIt;
		private Iterator<Node> rowIt;
		
		ScreenIterator(TreeMap<Double, Row> collection) {
			this.collection = collection;
			keyIt = collection.navigableKeySet().iterator();
			if(keyIt.hasNext()) {
				key = keyIt.next();
				rowIt = collection.get(key).getIterator();
			}
			next = getNext();
		}
		
		private Node getNext(){
			if(rowIt.hasNext()) {
				return rowIt.next();
			}
			
			//Loop protects against empty rows.
			Node output = null;
			while(keyIt.hasNext()) {
				key = keyIt.next();
				rowIt = collection.get(key).getIterator();
				if(rowIt.hasNext()) {
					output = rowIt.next();
					break;
				}
			}
			return output;
		}

		@Override
		public boolean hasNext() {
			if(next != null)
				return true;
			else
				return false;
		}

		@Override
		public Node next() {
			if(hasNext()) {
				Node output = next;
				next = getNext();
				return output;
			}
			else {
				//TODO error code
				return null;
			}
		}
	}
	
	private class Row implements Comparable<Row>{
		
		private final TreeSet<Node> row;
		private final double yComponent;
		
		Row(Node first){
			row = new TreeSet<>();
			yComponent = first.getCenter()[1];
			row.add(first);
		}
		
		///////////////////// Accessors ////////////////////
		
		final NavigableSet<Node> getAll(){
			return row;
		}
		
		final Node getFirst() {
			return row.first();
		}
		
		final Node getLast() {
			return row.last();
		}
		
		final double getYComp() {
			return yComponent;
		}
		
		final Iterator<Node> getIterator(){
			return row.iterator();
		}
		
		///////////////////// Mutator ///////////////////////
		
		final void add(Node node) {
			if(node.getCenter()[1] == yComponent) {
				row.add(node);
			}
			else {
				System.err.println("Error in Screen.Row.add");
				System.exit(0);
			}
		}
		
		final void remove(Node node) {
			if(!row.remove(node)) {
				System.err.println("Error in Screen.Row.remove");
				System.exit(0);
			}
		}
		
		/////////////////// Checkers /////////////////////////
		
		@SuppressWarnings("unused")
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
		Iterator<Node> screenIt = getScreenIterator();
		System.out.println("***************************************");
		while(screenIt.hasNext())
			System.out.println(screenIt.next());
		System.out.println("***************************************");
	}
	
}//End of Screen
