package view;



import java.awt.Color;
import java.awt.Container;
import java.awt.geom.Point2D;
import java.io.File;

import javax.swing.JLayeredPane;


import game.Game;
import view.images.Tile;
import view.images.GameImage;
import view.images.NewTile;
import view.images.Player;


public class ViewFacade{
	
	//Components
	ViewFrame vf;
	ViewSurface sur;
	ControlSurface con;
	Game game;
	ViewParameters parameters;
	
	///////////////////////// Constructor //////////////////////////////
	
	public ViewFacade(Game game) {
		parameters = ViewParameters.getInstance();
		this.game = game;
		vf = new ViewFrame(parameters.getPanel());
		
		//Initializes a layered content pane
		Container layered = new JLayeredPane();
		sur = new ViewSurface(parameters.getPanel());
		layered.add(sur, 1);
		con = new ControlSurface(parameters.getPanel());
		layered.add(con, 0);
		vf.setContentPane(layered);
		vf.pack();
		vf.setVisible(true);
	}
	
	ViewFacade() {
		/*
		 * This constructor is only for testing.
		 */
		parameters = ViewParameters.getInstance();
		vf = new ViewFrame(parameters.getPanel());
		
		//Initializes a layered content pane
		Container layered = new JLayeredPane();
		sur = new ViewSurface(parameters.getPanel());
		layered.add(sur, 1);
		con = new ControlSurface(parameters.getPanel());
		layered.add(con, 0);
		vf.setContentPane(layered);
		vf.pack();
		vf.setVisible(true);
	}
	
	//////////////////////////// Public Methods /////////////////////////
	
	public void draw(double[][] vertices, Color fill) {
		GameImage image = new Tile(vertices, fill);
		sur.addImage(image);
		
	};
	
	public void update() {
		
		sur.addImage(new Player(new Point2D.Double(parameters.getPanelWidth() / 2, parameters.getPanelHeight() / 2)));
		sur.render();
	}
	
	//Only for testing. Should be removed at a later date.
	public void testImages(File image, double[] center) {
		sur.addImage(new NewTile(image, center));
	}
	
	///////////////////////// Debugging Methods ////////////////////////
	
	public void degbugImage(GameImage gi) {
		sur.addImage(gi);
		sur.render();
	}
}
