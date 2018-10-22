package view.images;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class GrassTile extends Tile{
	public GrassTile(double[] center) {
		super(center);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(bf.getGrass(), at, null);		
	}
	
}
