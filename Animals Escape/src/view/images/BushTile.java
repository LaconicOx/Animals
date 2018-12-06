package view.images;


import units.TileKey;

public class BushTile extends Tile{
	
	private static final String path = "BushHex";
	private static final int frames = 1;
	private static final Animation bush = new Animation(path, frames);
	
	
	public BushTile(TileKey key) {
		super(key, bush);
	}
	
	
}
