package view.images;

import java.awt.Graphics;
import java.awt.Graphics2D;

import units.TileKey;

public class GrassTile extends Tile{
	public GrassTile(TileKey key) {
		super(key);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(bf.getGrass(), getAffine(), null);		
	}
	
}
