package units;


public class TileKey extends ImageKey{
	
	private static final double[] TILE_DIMENSIONS = {300.0, 261.0};//Dimensions for image files in pixels.
	private final CellKey key;
	private final double[] coords;
	
	public TileKey(CellKey key) {
		super(TILE_DIMENSIONS, key.getDimension());
		this.key = key;
		this.coords = key.getCenter();
	}
	
	protected double[] getCoords() {
		return coords;
	}
	
}
