package view.images;


import units.TileKey;

public class RockTile extends Tile{
	
	private static final String path = "RockHex";
	private static final int frames = 1;
	private static final Animation rock = new Animation(path, frames);
	
	public RockTile(TileKey key) {
		super(key, rock);
	}
	
	
}
