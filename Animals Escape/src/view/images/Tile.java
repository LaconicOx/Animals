package view.images;

import java.awt.Graphics;
import java.awt.Graphics2D;


import units.TileKey;

public abstract class Tile extends GameImage{
	
	protected Animation anim;
	private TileKey key;
	
	///////////////////////// Constructor and Initializers /////////////////////////////////////
	protected Tile(TileKey key, Animation anim) {
		this.key = key;
		this.anim = anim;
	}
	
	
	////////////////////////// Accessor Methods ////////////////////////////////
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(anim.getResting(), key.getTransformation(), null);		
	}
		
	
}
