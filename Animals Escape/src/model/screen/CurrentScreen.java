package model.screen;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;
import game.Directions.Direction;
import model.nodes.Node;
import view.ViewInterface;


public class CurrentScreen implements Screen{

	//State fields
	private final ScreenDilate dilate;
	private final ScreenShift shift;
	private final ScreenNormal normal;
	private final ScreenStart start;
	
	//Instance Fields
	private final TreeMap<Double, NavigableSet<Node>> screenRows;
	private final ViewInterface view;
	private ScreenState state;
		
	////////////////////////// Constructors and Initializers //////////////////////////
	public CurrentScreen(ViewInterface view){
		screenRows = new TreeMap<>();
		this.view = view;
		
		//Initializes states
		dilate = new ScreenDilate(this, view);
		shift = new ScreenShift(this);
		normal = new ScreenNormal(this);
		start = new ScreenStart(this, view);
		state = start;
		
	}
		
	////////////////////////// Accessor Methods /////////////////////////////
		
	public final Iterator<Node> getBorderIterator(Comparator<Node> order){
		return new BorderIterator(order);
	}
	
	public final Iterator<Node> getScreenIterator(){
		return new ScreenIterator();
	}
	
	public final ScreenState getStateDilate() {
		return dilate;
	}
	
	public final ScreenState getStateNormal() {
		return normal;
	}
	
	public final ScreenState getStateShift() {
		return shift;
	}
		
	////////////////////////// Mutator Methods /////////////////////////////////
	
	public final void add(Node node) {
		
		double key = node.getCenter()[1];
		if(screenRows.containsKey(key)) {
			screenRows.get(key).add(node);
		}
		else {
			screenRows.put(key, new TreeSet<Node>(new RowOrder()));
			screenRows.get(key).add(node);
		}
	}
	
	public final void dilate(double factor) {
		state.dilate(factor);
	}
	
	
	public final void setState(ScreenState state) {
		this.state = state;
	}
	
	public void shift(Direction toward) {
		state.shift(toward);
	}
	
	public void update() {
		state.update();
	}
		
	
	///////////////////////// Inner Classes ///////////////////////////////

	class BorderIterator implements Iterator<Node>{
		private final NavigableSet<Node> collection;
		private Node next;
		
		BorderIterator(Comparator<Node> order){
			collection = new TreeSet<>(order);
			
			NavigableSet<Double> keys = screenRows.navigableKeySet();
			int size = keys.size();
			double key = 0;
			Node left = null;
			Node right = null;
			
			for(int i = 0; i < size; i++) {
				//Gets key by referencing by links.
				if(i == 0) {
					key = keys.first();
				}
				else {
					key = keys.higher(key);
				}
				
				NavigableSet<Node> row = screenRows.get(key);
				
				//Gates for empty rows. Removes row and decrements size if true.
				if(row.isEmpty()) {
					screenRows.remove(key);//Removes row
					size--;//Decrements size
					i--;//Decrements index
				}
				else {
					//The top border spans the topmost two rows.
					if(i < 2) {
						collection.addAll(row);					}
					//For the middle rows, only the first and last nodes are on the border.
					else if(i < size - 2) {
						left = row.first();
						if(left.checkBorder())
							collection.add(left);
						
						right = row.last();
						if(right.checkBorder())
							collection.add(right);
						
					}
					//the bottom border spans the bottom-most two rows.
					else {
						collection.addAll(row);
					}
				}
			}
			
			next = collection.first();
			System.out.println(collection.size());
		}//End of constructor
		
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
	
	
	/**
	 * Iterates through every nodes stored in Screen.
	 * @author dvdco
	 *
	 */
	private class ScreenIterator implements Iterator<Node>{
		
		private final NavigableSet<Double> keys; 
		private double curKey;
		private Iterator<Node> rowIt;
		private Iterator<Node> prevIt;
		private boolean retFlag = false;
		private boolean swapFlag = false;
		
		ScreenIterator(){
			keys = screenRows.navigableKeySet();
			boolean error = true;
			if(!keys.isEmpty()) {
				curKey = keys.first();
				double lastKey = keys.last();
				NavigableSet<Node> row = null;
				while(true) {
					row = screenRows.get(curKey);
					//Breaks the loop for non empty rows.
					if(!row.isEmpty()) {
						rowIt = row.iterator();
						error = false;
						break;
					}
					else {
						screenRows.remove(curKey);//Removes empty row
					}
					
					
					
					if(curKey < lastKey) {
						curKey = keys.higher(curKey);
					}
					else {
						System.out.println("Cur" + curKey);
						System.out.println("Last" + lastKey);
						break;//breaks loop after processing last key
						
					}
				}
				
			}
			if(error) {
				//TODO Error code
				System.err.println("error in ScreenIt.init");
				System.exit(0);
			}
			
			
		}//End of Constructor
		
		private void advance() {
			if(!rowIt.hasNext()) {
				prevIt = rowIt;
				NavigableSet<Node> row = null;
				double lastKey = keys.last();
				while(curKey < lastKey) {
					curKey = keys.higher(curKey);
					row = screenRows.get(curKey);
					if(!row.isEmpty()) {
						rowIt = row.iterator();
						swapFlag = true;
						break;
					}
					else {
						screenRows.remove(curKey);
					}
				}
			}
		}

		@Override
		public boolean hasNext() {
			if(rowIt.hasNext()) {
				return true;
			}
			else {
				advance();
				return rowIt.hasNext();
			}
		}

		@Override
		public Node next() {
			
			if(rowIt.hasNext()) {
				
				retFlag = true;
				swapFlag = false;
				return rowIt.next(); 
			}
			else {
				advance();
				if(rowIt.hasNext()) {
					retFlag = true;
					swapFlag = false;
					return rowIt.next();
				}
				else {
					//TODO error code
					System.err.println("error in ScreenIt.next");
					return null;
				}
			}
			
		}
		
		@Override
		public void remove() {
			if(swapFlag && retFlag) {
				prevIt.remove();
				retFlag = false;
			}
			else if(retFlag) {
				rowIt.remove();
				retFlag = false;
			}
			else {
				//TODO error code
			}
		}
	}//End of ScreenIterator

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
	
	//////////////////////////// Debugging ////////////////////////////
	
	void displayRows() {
		NavigableSet<Double> keys = screenRows.navigableKeySet();
		for(double key : keys) {
			System.out.println("*************** Row at " + key + " ********************");
			Iterator<Node> rowIt = screenRows.get(key).iterator();
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
