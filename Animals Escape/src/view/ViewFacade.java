package view;

import java.awt.Container;
import java.awt.geom.Point2D;

import javax.swing.JLayeredPane;

import game.Game;
import view.images.TreeTile;
import view.images.BushTile;
import view.images.GameImage;
import view.images.GrassTile;
import view.images.Player;
import view.images.RockTile;


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
	
	public void addBush(double[] center) { sur.addImage(new BushTile(center));}
	public void addGrass(double[] center) { sur.addImage(new GrassTile(center));}
	public void addRock(double[] center) { sur.addImage(new RockTile(center));}
	public void addTree(double[] center) { sur.addImage(new TreeTile(center));}
	
	
	public void update() {
		
		sur.addImage(new Player(new Point2D.Double(parameters.getPanelWidth() / 2, parameters.getPanelHeight() / 2)));
		sur.render();
	}
	
	
	///////////////////////// Debugging Methods ////////////////////////
	
	public void degbugImage(GameImage gi) {
		sur.addImage(gi);
		sur.render();
	}
}
