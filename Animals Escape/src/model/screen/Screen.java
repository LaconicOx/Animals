package model.screen;

import java.util.Comparator;
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
		
	final Iterator<Node> getShiftIterator(Direction toward){
		return new ShiftIterator(screenRows, toward);
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

		double key = node.getCenter()[1];
		Row row = screenRows.get(key);
		if(row != null) {
			row.remove(node);
		}
		else {
			//TODO error code
		}

		if(row.getSize() == 0) {
			screenRows.remove(key);
		
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
		
	
	///////////////////////// Inner Classes ///////////////////////////////
	abstract class BorderIterator implements Iterator<Node>{
		
		protected NavigableSet<Node> collection;
		protected Node next;
		
		protected abstract NavigableSet<Node> initSet();
		
		protected NavigableSet<Node> initCollection(TreeMap<Double, Row> collection) {
			NavigableSet<Node> output = initSet();
			System.out.println("**************** initCollection *******************");
			NavigableSet<Double> keys = collection.navigableKeySet();
			int size = keys.size();
			double key = 0;
			Node left = null;
			Node right = null;
			
			for(int i = 0; i < size; i++) {
				if(i == 0) {
					key = keys.first();
				}
				else {
					key = keys.higher(key);
				}
		
				
				//The top border spans the topmost two rows.
				if(i < 2) {
					//System.out.println(key);
					//System.out.println(collection.get(key));
					output.addAll(collection.get(key).getAll());
				}
				//For the middle rows, only the first and last nodes are on the border.
				else if(i < size - 2) {
					Row row = collection.get(key);
					left = row.getFirst();
					if(left.checkBorder())
						output.add(left);
					
					right = row.getLast();
					if(right.checkBorder())
						output.add(right);
					
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
		
		/**
		 * Comparator orders treeset for iterating when the shifting
		 * toward the north. Top is less than bottom and left is less than right.
		 * @author dvdco
		 *
		 */
		private final class North implements Comparator<Node>{

			@Override
			public int compare(Node left, Node right) {
				int lesser = -1;
				int equal = 0;
				int greater = 1;
				
				double[] leftCen = left.getCenter();
				double[] rightCen = right.getCenter();
				
				double xDiff = leftCen[0] - rightCen[0];
				double yDiff = leftCen[1] - rightCen[1];
				//Remember the y-values have been reflected over the x-axis 
				//in order to mirror screen values.
				
				//The topmost nodes should be first.
				if(yDiff < 0)
					return lesser;
				else if(yDiff > 0)
					return greater;
				else {
					if(xDiff < 0)
						return lesser;
					else if(xDiff > 0)
						return greater;
					else
						return equal;
				}
			}
			
		}//End of North
		
		/**
		 * Comparator orders treeset for iterating when the shifting
		 * toward the south. Bottom is less than top and left is less than right.
		 * @author dvdco
		 *
		 */
		private final class South implements Comparator<Node>{

			@Override
			public int compare(Node left, Node right) {
				int lesser = -1;
				int equal = 0;
				int greater = 1;
				
				double[] leftCen = left.getCenter();
				double[] rightCen = right.getCenter();
				
				double xDiff = leftCen[0] - rightCen[0];
				double yDiff = leftCen[1] - rightCen[1];
				//Remember the y-values have been reflected over the x-axis 
				//in order to mirror screen values.
				
				//The bottom-most nodes should be first.
				if(yDiff > 0)
					return lesser;
				else if(yDiff < 0)
					return greater;
				else {
					if(xDiff < 0)
						return lesser;
					else if(xDiff > 0)
						return greater;
					else
						return equal;
				}
			}
			
		}//End of South
		
		private final class NorthEast implements Comparator<Node>{

			public int compare(Node left, Node right) {
				int lesser = -1;
				int equal = 0;
				int greater = 1;
				
				double[] leftCen = left.getCenter();
				double[] rightCen = right.getCenter();
				double xComp = rightCen[0] - leftCen[0];
				//Multiplying by negative one adjusting for the reflection
				double yComp = -1.0 * (rightCen[1] - leftCen[1]);
				double angle = 0.0;
				
				/*
				 * Calculates radians using arctan. The conditionals assure that 
				 * angle will always be positive and that 0 <= angle <= 2PI.
				 */
				if(xComp > 0.0 && yComp >= 0.0) {
					angle = Math.atan(yComp/xComp);
				}
				else if(xComp < 0.0) {
					angle = Math.PI + Math.atan(yComp/xComp);
				}
				else if(xComp > 0.0 && yComp < 0.0) {
					angle = 2.0 * Math.PI + Math.atan(yComp/xComp);
				}
				else if(xComp == 0.0 && yComp > 0.0) {
					angle = Math.PI / 2.0;
				}
				else if(xComp == 0.0 && yComp < 0.0) {
					angle = 3.0 * Math.PI / 2.0;
				}
				//The remaining case is xComp == 0 && yComp == 0.
				else {
					return equal;
				}
				
				/*
				 * ray1 and ray2 represent the two rays that make up 
				 * the diagonal. ray1 should come before ray2 when rotating
				 * counter-clockwise.
				 */
				double ray1 = 5.0 * Math.PI / 6.0;
				double ray2 = ray1 + Math.PI;
				double error = 0.00000000000001;
				
				/*
				 * Error checking is necessary because I found that these calculations
				 * don't always have the same number of significant digits.
				 */
				if(angle <= ray1 || Math.abs(angle - ray1) <= error) {
					return greater;
				}
				else if(angle <= ray2 || Math.abs(angle - ray2) <= error) {
					return lesser;
				}
				else
					return greater;
			}
		}//End of NorthEast
		
		private final class NorthWest implements Comparator<Node>{
			public int compare(Node left, Node right) {
				int lesser = -1;
				int equal = 0;
				int greater = 1;
				
				double[] leftCen = left.getCenter();
				double[] rightCen = right.getCenter();
				double xComp = rightCen[0] - leftCen[0];
				//Multiplying by negative one adjusting for the reflection
				double yComp = -1.0 * (rightCen[1] - leftCen[1]);
				double angle = 0.0;
				
				/*
				 * Calculates radians using arctan. The conditionals assure that 
				 * angle will always be positive and that 0 <= angle <= 2PI.
				 */
				if(xComp > 0.0 && yComp >= 0.0) {
					angle = Math.atan(yComp/xComp);
				}
				else if(xComp < 0.0) {
					angle = Math.PI + Math.atan(yComp/xComp);
				}
				else if(xComp > 0.0 && yComp < 0.0) {
					angle = 2.0 * Math.PI + Math.atan(yComp/xComp);
				}
				else if(xComp == 0.0 && yComp > 0.0) {
					angle = Math.PI / 2.0;
				}
				else if(xComp == 0.0 && yComp < 0.0) {
					angle = 3.0 * Math.PI / 2.0;
				}
				//The remaining case is xComp == 0 && yComp == 0.
				else {
					return equal;
				}
				
				/*
				 * ray1 and ray2 represent the two rays that make up 
				 * the diagonal. ray1 should come before ray2 when rotating
				 * counter-clockwise.
				 */
				double ray1 = Math.PI / 6.0;
				double ray2 = ray1 + Math.PI;
				double error = 0.00000000000001;
				
				/*
				 * Error checking is necessary because I found that these calculations
				 * don't always have the same number of significant digits.
				 */
				if(angle >= ray2 || Math.abs(angle - ray2) <= error) {
					return lesser;
				}
				else if (angle >= ray1 || Math.abs(angle - ray1) <= error) {
					return greater;
				}
				else
					return lesser;
			}
		}//End of NorthWest
		
		private final class SouthWest implements Comparator<Node>{
			public int compare(Node left, Node right) {
				int lesser = -1;
				int equal = 0;
				int greater = 1;
				
				double[] leftCen = left.getCenter();
				double[] rightCen = right.getCenter();
				double xComp = rightCen[0] - leftCen[0];
				//Multiplying by negative one adjusting for the reflection
				double yComp = -1.0 * (rightCen[1] - leftCen[1]);
				double angle = 0.0;
				
				/*
				 * Calculates radians using arctan. The conditionals assure that 
				 * angle will always be positive and that 0 <= angle <= 2PI.
				 */
				if(xComp > 0.0 && yComp >= 0.0) {
					angle = Math.atan(yComp/xComp);
				}
				else if(xComp < 0.0) {
					angle = Math.PI + Math.atan(yComp/xComp);
				}
				else if(xComp > 0.0 && yComp < 0.0) {
					angle = 2.0 * Math.PI + Math.atan(yComp/xComp);
				}
				else if(xComp == 0.0 && yComp > 0.0) {
					angle = Math.PI / 2.0;
				}
				else if(xComp == 0.0 && yComp < 0.0) {
					angle = 3.0 * Math.PI / 2.0;
				}
				//The remaining case is xComp == 0 && yComp == 0.
				else {
					return equal;
				}
				
				/*
				 * ray1 and ray2 represent the two rays that make up 
				 * the diagonal. ray1 should come before ray2 when rotating
				 * counter-clockwise.
				 */
				double ray1 = 5.0 * Math.PI / 6.0;
				double ray2 = ray1 + Math.PI;
				double error = 0.00000000000001;
				
				/*
				 * Error checking is necessary because I found that these calculations
				 * don't always have the same number of significant digits.
				 */
				if(angle <= ray1 || Math.abs(angle - ray1) <= error) {
					return lesser;
				}
				else if(angle <= ray2 || Math.abs(angle - ray2) <= error) {
					return greater;
				}
				else
					return lesser;
			}
		}//End of SouthWest
		
		private final class SouthEast implements Comparator<Node>{
			public int compare(Node left, Node right) {
				int lesser = -1;
				int equal = 0;
				int greater = 1;
				
				double[] leftCen = left.getCenter();
				double[] rightCen = right.getCenter();
				double xComp = rightCen[0] - leftCen[0];
				//Multiplying by negative one adjusting for the reflection
				double yComp = -1.0 * (rightCen[1] - leftCen[1]);
				double angle = 0.0;
				
				/*
				 * Calculates radians using arctan. The conditionals assure that 
				 * angle will always be positive and that 0 <= angle <= 2PI.
				 */
				if(xComp > 0.0 && yComp >= 0.0) {
					angle = Math.atan(yComp/xComp);
				}
				else if(xComp < 0.0) {
					angle = Math.PI + Math.atan(yComp/xComp);
				}
				else if(xComp > 0.0 && yComp < 0.0) {
					angle = 2.0 * Math.PI + Math.atan(yComp/xComp);
				}
				else if(xComp == 0.0 && yComp > 0.0) {
					angle = Math.PI / 2.0;
				}
				else if(xComp == 0.0 && yComp < 0.0) {
					angle = 3.0 * Math.PI / 2.0;
				}
				//The remaining case is xComp == 0 && yComp == 0.
				else {
					return equal;
				}
				
				/*
				 * ray1 and ray2 represent the two rays that make up 
				 * the diagonal. ray1 should come before ray2 when rotating
				 * counter-clockwise.
				 */
				double ray1 = Math.PI / 6.0;
				double ray2 = ray1 + Math.PI;
				double error = 0.00000000000001;
				
				/*
				 * Error checking is necessary because I found that these calculations
				 * don't always have the same number of significant digits.
				 */
				if(angle >= ray2 || Math.abs(angle - ray2) <= error) {
					return greater;
				}
				else if(angle >= ray1 || Math.abs(angle - ray1) <= error) {
					return lesser;
				}
				else
					return greater;
			}
		}//End of SouthEast
		
		//Instance Field
		private final Direction toward;
		
		/////////////////////// Constructor /////////////////////////
		
		ShiftIterator(TreeMap<Double, Row> collection, Direction toward){
			this.toward = toward;
			this.collection = initCollection(collection);
			next = this.collection.first();
		}
		
		@Override
		protected final NavigableSet<Node> initSet() {
			Comparator<Node> comp = null;
			if(toward == Direction.NE)
				comp = new NorthEast();
			else if(toward == Direction.N)
				comp = new North();
			else if(toward == Direction.NW) {
				System.out.println("**************** initSet *******************");
				comp = new NorthWest();
			}
			else if(toward == Direction.SW)
				comp = new SouthWest();
			else if(toward == Direction.S)
				comp = new South();
			else if(toward == Direction.SE)
				comp = new SouthEast();
			else {
				//TODO Error code
			}
			return new TreeSet<Node>(comp);
		}
	}//End of ShiftIterator
	
	/**
	 * Iterates through every nodes stored in Screen.
	 * @author dvdco
	 *
	 */
	class ScreenIterator implements Iterator<Node>{
		
		private final TreeMap<Double, Row> collection;
		private final Iterator<Double> keyIt;
		private Row curRow = null;
		private Iterator<Node> curIt;
		private Row lastRow = null;
		private boolean retFlag = false;
		private boolean swapFlag = false;
		private Node cursor = null;
		
		ScreenIterator(TreeMap<Double, Row> collection) {
			this.collection = collection;
			keyIt = collection.navigableKeySet().iterator();
			if(keyIt.hasNext()) {
				curRow = collection.get(keyIt.next());
				curIt = curRow.getIterator();
			}
		}
		
		private void advance() {
			if(!curIt.hasNext()) {
				lastRow = curRow;
				swapFlag = true;
				while(keyIt.hasNext()) {
					double key = keyIt.next();
					curRow = collection.get(key);
					if(curRow.getSize() > 0) {
						curIt = curRow.getIterator();
						break;
					}
					else {
						collection.remove(key);
					}
				}
			}
		}

		@Override
		public boolean hasNext() {
			if(curIt.hasNext()) {
				return true;
			}
			else {
				advance();
				return curIt.hasNext();
			}
		}

		@Override
		public Node next() {
			if(curIt.hasNext()) {
				retFlag = true;
				swapFlag = false;
				cursor = curIt.next();
				return cursor;
			}
			else {
				advance();
				if(curIt.hasNext()) {
					retFlag = true;
					swapFlag = false;
					cursor = curIt.next();
					return cursor;
				}
				else {
					//TODO: error code.
					return null;
				}
			}
		}
		
		@Override
		public void remove() {
			if(retFlag) {
				if(swapFlag) {
					lastRow.remove(cursor);
					if(lastRow.getSize() == 0) {
						collection.remove(lastRow.getYComp());
					}
					retFlag = false;
				}
				else {
					curIt.remove();
					if(curRow.getSize() == 0) {
						advance();
						collection.remove(lastRow.getYComp());
					}
					retFlag = false;
				}
			}
		}
		
	}//End of ScreenIterator
	
	private class Row implements Comparable<Row>{
		
		private final TreeSet<Node> row;
		private final double yComponent;
		/**
		 * Orders nodes from left to right according to their
		 * x-components. Throws exception if y-components are not equal.
		 * @author dvdco
		 *
		 */
		private final class RowOrder implements Comparator<Node>{
			
			@Override
			public int compare(Node left, Node right) {
				int lesser = -1;
				int equal = 0;
				int greater = 1;
				double[] leftCen = left.getCenter();
				double[] rightCen = right.getCenter();
				double xDiff = leftCen[0] - rightCen[0];
				
				//Row nodes must have the same y-component.
				if(leftCen[1] != rightCen[1]) {
					//TODO error code
					System.err.println("Error in Row.RowOrder.compare");
					System.exit(0);
				}
				
				if (xDiff < 0)
					return lesser;
				else if (xDiff > 0)
					return greater;
				else
					return equal;
				
			}
			
		}
		
		/////////////////// Constructor ///////////////////////
		Row(Node first){
			row = new TreeSet<>(new RowOrder());
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
		
		final int getSize() {
			return row.size();
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
	
	void displayRows() {
		NavigableSet<Double> keys = screenRows.navigableKeySet();
		for(double key : keys) {
			System.out.println("*************** Row at " + key + " ********************");
			Iterator<Node> rowIt = screenRows.get(key).getIterator();
			while(rowIt.hasNext()) {
				System.out.println(rowIt.next());
			}
		}
	}
	
	void display() {
		Iterator<Node> screenIt = getScreenIterator();
		System.out.println("***************************************");
		while(screenIt.hasNext())
			System.out.println(screenIt.next());
		System.out.println("***************************************");
	}
	
}//End of Screen
