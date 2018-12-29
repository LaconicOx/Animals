package image_library;

import java.awt.Graphics;

public interface Tile {
	
	public void advance();//Advances animation by a frame
	public void send();//Sends image to view to be drawn to components.
	public void draw(Graphics g);//Draws the image.
	public boolean checkResting();
}
