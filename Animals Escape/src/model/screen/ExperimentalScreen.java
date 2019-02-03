package model.screen;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import game.Directions.Direction;
import model.nodes.Node;
import view.ViewInterface;

public class ExperimentalScreen implements Screen{
	//State fields
		private final ScreenDilate dilate;
		private final ScreenShift shift;
		private final ScreenNormal normal;
		private final ScreenStart start;
		
		//Instance Fields
		private final Set<Node> screen;
		private final ViewInterface view;
		private ScreenState state;
			
		////////////////////////// Constructors and Initializers //////////////////////////
		public ExperimentalScreen(ViewInterface view){
			screen = new HashSet<>();
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
			return screen.iterator();
		}
		
		final double[] getShift() {
			return view.getShift();
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
			screen.add(node);
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
		
		class BorderQueue implements Queue<Node>{
			
			private final Comparator<Node> order;
			private int size;//Current number of entries
			private final int maxSize;//Max size of the array
			private Node[] sorted;//Sorted array used for queue
			Deque<Node> stack;//Stack for shifting array.
			//////////////////// Constructor and initializer //////////////////////////
			
			BorderQueue(Comparator<Node> order){
				this.order = order;
				size = 0;
				maxSize = screen.size();
				sorted = new Node[maxSize];
				stack = new ArrayDeque<>();
				
				Iterator<Node> screenIt = screen.iterator();
				Node candidate;
				while(screenIt.hasNext()) {
					candidate = screenIt.next();
					
					//Adds border nodes
					if(candidate.checkBorder()) {
						if(size == 0){
							sorted[0] = candidate;
						}
						shift(candidate, binaryIndex(candidate, 0, size - 1));
					}
						
					
				}
			}
			
			private final int binaryIndex(Node node, int low, int high) {
				int lesser = -1;
				int greater = 1;
				int median = (low + high) / 2;
				int output = -1;
				
				int medVal = order.compare(node, sorted[median]);
				if(medVal == lesser) {
					int left = median - 1;
					if(left <=  low)
						output = low;
					else
						output = binaryIndex(node, low, left);
				}
				else if(medVal == greater) {
					int right = median + 1;
					if(right >= size) {
						output = size;
					}
					if(right >= high)
						output = high;
					else
						output = binaryIndex(node, right, high);
				}
				else {
					output = median;
				}
				return output;
				
			}
			
			private final void shift(Node node, int position) {
				
				//Starts at the tail and stores all nodes in an stack, including the node at the index of position. 
				int cur = size - 1;
				while(cur >= position) {
					stack.push(sorted[cur]);
					cur--;
				}
				
				sorted[position] = node;
				cur++;//Increment in order to equal index.
				
				while(!stack.isEmpty()) {
					cur++;
					sorted[cur] = stack.pop();
				}
				size++;
			}
			
			//////////////////////////////// Overrides ////////////////////////////////

			@Override
			public int size() {
				return size;
			}

			@Override
			public boolean isEmpty() {
				if(size == 0)
					return true;
				else
					return false;
			}

			@Override
			public boolean contains(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Iterator<Node> iterator() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object[] toArray() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T[] toArray(T[] a) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean remove(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean containsAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean addAll(Collection<? extends Node> c) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean removeAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void clear() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean add(Node e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean offer(Node e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Node remove() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Node poll() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Node element() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Node peek() {
				// TODO Auto-generated method stub
				return null;
			}
		}
		
		class BorderIterator implements Iterator<Node>{
			
			Comparator<Node> order;
			Node[] sorted;
			int end;
			
			BorderIterator(Comparator<Node> order){
				this.order = order;
				
				Iterator<Node> screenIt = screen.iterator();
				sorted = new Node[screen.size()];
				
				Node candidate;
				while(screenIt.hasNext()) {
					candidate = screenIt.next();
					
					//Adds border nodes
					if(candidate.checkBorder())
						sorter.add(candidate);
					}
				}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Node next() {
				// TODO Auto-generated method stub
				return null;
			}
						
			}//End of constructor
		
		
		//////////////////////////// Debugging ////////////////////////////
		
		
		void display() {
			Iterator<Node> screenIt = getScreenIterator();
			System.out.println("***************************************");
			while(screenIt.hasNext())
				System.out.println(screenIt.next());
			System.out.println("***************************************");
		}
}
