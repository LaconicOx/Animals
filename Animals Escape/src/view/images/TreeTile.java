package view.images;



import units.TileKey;

public class TreeTile extends Tile{
	
	private static final String path = "TreeHex";
	private static final int frames = 1;
	private static final Animation tree = new Animation(path, frames);
	
	public TreeTile(TileKey key) {
		super(key, tree);
	}
	
}
