package model.screen;

import java.util.Iterator;
import java.util.Set;

import game.Directions.Direction;
import model.nodes.Node;
import view.ViewInterface;

public class ScreenDilate extends ScreenState{
	
	//Instance Fields
	private final ViewInterface view;
	private double factor;
	
	public ScreenDilate(Screen screen, ViewInterface view) {
		super(screen);
		this.view = view;
	}
	
	private final void decrease(double factor) {
		view.updateScale(factor);
		double[] boundaries = getModelBoundaries(view.getShift(), view.getScreenDim(), view.getScale());
		
	
		
	}
	
	@Override
	public final void dilate(double factor) {
		this.factor = factor;
	}
	
	private final void increase(double factor) {
		//TODO
	}
	
	
	@Override
	public final void update() {
		if(factor > 1.0) {
			increase(factor);
		}
		else if(factor < 1.0) {
			decrease(factor);
		}
		else {
			//Do nothing.
		}
		
		//Returns state to normal
		screen.setState(screen.getStateNormal());
	}
}
