package units;

public class TileKey extends ImageKey{
	
	private static final double[] TILE_DIMENSIONS = {300.0, 261.0};
	
	public TileKey(CellKey key) {
		super(key.getCenter(), TILE_DIMENSIONS, key.getDimension());
	}
	
	
	
	
}
