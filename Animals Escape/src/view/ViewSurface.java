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

import view.images.Bob;
import view.images.GameImage;
import view.images.Tile;


class ViewSurface extends JPanel{
	private static final long serialVersionUID = 1321457304844233883L;
	private Dimension dim;
	
	//Fields for double buffering.
	private Image dbImage = null;
	private Graphics dbg;
	
	ArrayList<Tile> images;
	ArrayList<GameImage> characters;
	
	//////////////////////////// Constructor ////////////////////////////////////////
	ViewSurface(Dimension dim){
		this.dim = dim;
		setSize(this.dim);
		setBackground(Color.white);//JComponent method for setting background color.
		setOpaque(true);
		images = new ArrayList<>();
		characters = new ArrayList<>();
	}
	
	///////////////////////////// Animation Methods ///////////////////////////////////
	
	void addImage(Tile tile) {
		images.add(tile);
	}
	
	void addCharacter(Bob bob) {
		System.out.println(bob);
		characters.add(bob);
	}
	
	
	void clearImages() {
		images.clear();
	}
	
	void render() {
		//Creates double-buffer.
		if (dbImage == null) {
			dbImage = createImage(dim.width, dim.height);
			if (dbImage == null)
				System.out.println("dbImage is null.");
			else
				dbg = dbImage.getGraphics();
		}
		
		//Clear previous images by drawing a white background.
		dbg.setColor(Color.white);
		dbg.fillRect(0,0, dim.width, dim.height);
		
		//Draws tiles
		for (GameImage image : images)
			image.draw(dbg);
		
		for(GameImage chr : characters)
			chr.draw(dbg);
		
		repaint();// should be deleted once paintscreen is debugged.
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
