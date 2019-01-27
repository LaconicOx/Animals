package model.screen;

import java.util.Comparator;
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

		class BorderIterator implements Iterator<Node>{
			
			NavigableSet<Node> sorter;
			
			BorderIterator(Comparator<Node> order){
				
				Iterator<Node> screenIt = screen.iterator();
				sorter = new TreeSet<>(order);
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
