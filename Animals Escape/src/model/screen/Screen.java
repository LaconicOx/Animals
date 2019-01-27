package model.screen;

import java.util.Comparator;
import java.util.Iterator;

import model.nodes.Node;

public interface Screen {
	
	public ScreenState getStateDilate();
	public void setState(ScreenState state);
	public ScreenState getStateShift();
	public Iterator<Node> getBorderIterator(Comparator<Node> comp);
	public void add(Node node);
	public ScreenState getStateNormal();
	public Iterator<Node> getScreenIterator();
}
