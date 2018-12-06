package view.images;


import java.awt.Graphics;
import java.awt.Graphics2D;

import units.BobKey;

public class Bob extends GameImage{
	
	private static final String PATH = "Player_East\\Player_East";
	private static final int FRAMES = 25;
	private static final Animation ANIM = new Animation(PATH, FRAMES);
	private BobKey key;
	
	public Bob(BobKey key){
		this.key = key;
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(ANIM.getFrame(key.getCount() % FRAMES), key.getTransformation(), null);		
	}
}
