package model.nodes;

class On extends BaseWrapper{
	
	private final Idle base;
	private OnState internal;
	private OnState updateInternal;
	
	
	On(NodeState ns) {
		super(ns);
		base = new Idle(this);
		internal = base;
	}
	
	/////////////////////// Accessors ///////////////////////////////
	
	@Override
	final double eat(double amount) {
		return internal.eat(amount);
	}
	
	@Override
	final double getScent() {
		return internal.getScent();
	}
	
	//////////////////////////// Mutators ///////////////////////////////
	
	double wrapFood(double amount) {
		if(updateInternal == null) {
			updateInternal = new FoodWrapper(internal, state.getFoodFactor());
		}
		else {
			updateInternal = new FoodWrapper(updateInternal, state.getFoodFactor());
		}
		
		return updateInternal.eat(amount);
	}
	
	void wrapScent(double scent) {
		if(updateInternal == null) {
			updateInternal = new WindWrapper(internal, state.getWindFactor());
		}
		else {
			updateInternal = new WindWrapper(updateInternal, state.getWindFactor());
		}
		
		updateInternal.setScent(scent);
	}
	
	void wrapWind(double[] wind) {
		if(updateInternal == null) {
			updateInternal = new WindWrapper(internal, state.getWindFactor());
		}
		else {
			updateInternal = new WindWrapper(updateInternal, state.getWindFactor());
		}
		
		updateInternal.receiveWind(wind);
	}

	@Override
	void setScent(double scent) {
		internal.setScent(scent);
		
	}

	@Override
	void setWind(double[] wind) {
		internal.receiveWind(wind);
		
	}
	
	final void reset() {
		internal = base;
	}
	
	final void send() {
		state.sendTile();
	}
	
	@Override
	final boolean update() {
		//TODO
		return false;
		
	}
	
	/////////////////////////// Checkers ////////////////////////////////
	
	@Override
	final boolean checkInterior() {
		return state.checkInterior();
	}
	
	

	//////////////////////////// Object Overrides ///////////////////////////
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	



	

	

	

	

	
}
