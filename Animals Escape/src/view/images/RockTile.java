package view.images;
import java.awt.Graphics;
import java.awt.Graphics2D;

import units.TileKey;

public class RockTile extends Tile{
	public RockTile(TileKey key) {
		super(key);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(bf.getRock(), getAffine(), null);		
	}
}
