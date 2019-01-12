package image_library;

import java.awt.Graphics;

import game.Directions.Direction;

public interface Tile {
	
	public void advance();//Advances animation by a frame
	public boolean checkResting();
	public void draw(Graphics g);//Draws the image.
	public void send();//Sends image to view to be drawn to components.
	public void setFacing(Direction facing);//Sets what direction the tile animation is facing.
	
	
}
