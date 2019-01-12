/*
 * David Cox
 * 08/17/18
 * Parts of the implementation for Surface are from "Killer Game Programming in Java" by ???
 * Particularly double buffering in render.
 * All of paintScreen.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;

import image_library.PlayerImage;
import image_library.Tile;


class ViewSurface extends JPanel{
	private static final long serialVersionUID = 1321457304844233883L;
	private Dimension dim;
	
	//Fields for double buffering.
	private Image dbImage;
	private Graphics read;
	private ArrayList<Tile> tiles;
	private PlayerImage player;
	
	//////////////////////////// Constructor ////////////////////////////////////////
	ViewSurface(Dimension dim){
		this.dim = dim;
		setSize(this.dim);
		setBackground(Color.black);//JComponent method for setting background color.
		setOpaque(true);
		tiles = new ArrayList<>();
	}
	
	public void init() {
		dbImage = createImage(dim.width, dim.height);
		read = dbImage.getGraphics();
		read.setColor(Color.black);
		read.fillRect(0,0, dim.width, dim.height);
	}
	
	///////////////////////////// Animation Methods ///////////////////////////////////
	
	void recieve(Tile tile) {
		tiles.add(tile);
	}
	
	void recieve(PlayerImage player) {
		this.player = player;
	}
	
	void render() {
		//Clear previous images by drawing a black background.
		read.setColor(Color.black);
		read.fillRect(0,0, dim.width, dim.height);
		
		tiles.forEach(tile -> tile.draw(read));
		player.draw(read);
		
		repaint();// should be deleted once paintscreen is debugged.
		tiles.clear();//Clears tiles
		
	}
	
	//Should be deleted once paintscreen is debugged.
	public void paintComponent(Graphics g) {
		 // This is a JComponent method that calls GUI paint methods. This method gets called
		 // by repaint() in the animation loop contained in run();
		super.paintComponent(g);
		if (dbImage != null)
			g.drawImage(dbImage,0,0,null);
	}
	
	//TODO paintscreen must be debugged
	void paintScreen() {
		/*
		 * The method implements active rendering. It replaces repaint() and replaces
		 * overriding the paintComponent.
		 */
		System.out.println("paintScreen called");
		Graphics g;
		try {
			g = this.getGraphics();//Inherited from JComponent. Gets Graphics context.
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch(Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	}
}
