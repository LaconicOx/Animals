package units;

public class BobKey extends ImageKey{
	
	private static final double[] BOB_DIMENSIONS = {435.0, 762.0};
	private final PlayerKey key;
	
	public BobKey(PlayerKey pk) {
		super(BOB_DIMENSIONS, pk.getDimensions());
		key = pk;
	}
	
	protected double[] getCoords() {
		return key.getCenter();
	}
	
	public int getCount() {
		return key.getCount();
	}
}
