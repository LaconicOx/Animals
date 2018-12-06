package view.images;


import units.TileKey;

public class GrassTile extends Tile{
	
	private static final String path = "GrassHex";
	private static final int frames = 1;
	private static final Animation grass = new Animation(path, frames);
	
	public GrassTile(TileKey key) {
		super(key, grass);
	}
	
}
