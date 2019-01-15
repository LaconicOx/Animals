package model.nodes;

public class FoodWrapper extends OnWrapper{
	
	private final static double FOOD_GROWTH = 0.005;
	
	private double food;
	private final double max;
	
	public FoodWrapper(OnState state, double food) {
		super(state);
		this.food = food;
		max = food;
	}
	
	@Override
	public final double eat(double amount) {
		food -= amount;
		if (food < 0.0)
			return 0.0;
		else
			return amount;
	}
	
	@Override
	public final boolean update() {
		
		food += FOOD_GROWTH;
		
		if(food >= max) {
			if(active.update())
				active = active.getWrapped();
			return true;
		}
		else {
			if(active.update())
				active = active.getWrapped();
			return false;
		}
	}
	
	@Override
	public final String toString() {
		return active.toString() + " with a food Wrapper";
	}
}
