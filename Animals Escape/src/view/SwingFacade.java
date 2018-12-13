package view;

import java.awt.Container;


import javax.swing.JLayeredPane;

import game.Game;
import units.BobKey;
import units.CellKey;
import units.GameKey;
import units.ImageKey;
import units.PlayerKey;
import units.TileKey;
import units.ViewKey;
import view.images.TreeTile;
import view.images.BushTile;
import view.images.GrassTile;
import view.images.Bob;
import view.images.RockTile;


public class ViewFacade{
	
	//Components
	ViewFrame vf;
	ViewSurface sur;
	ControlSurface con;
	Game game;
	ViewKey key;
	
	///////////////////////// Constructor //////////////////////////////
	
	public ViewFacade(Game game) {
		key = new ViewKey();
		this.game = game;
		vf = new ViewFrame(key.getPanel());
		
		//Initializes a layered content pane
		Container layered = new JLayeredPane();
		sur = new ViewSurface(key.getPanel());
		layered.add(sur, 1);
		con = new ControlSurface(key.getPanel());
		layered.add(con, 0);
		vf.setContentPane(layered);
		vf.pack();
		vf.setVisible(true);
	}
	
	ViewFacade() {
		/*
		 * This constructor is only for testing.
		 */
		key = new ViewKey();
		vf = new ViewFrame(key.getPanel());
		
		//Initializes a layered content pane
		Container layered = new JLayeredPane();
		sur = new ViewSurface(key.getPanel());
		layered.add(sur, 1);
		con = new ControlSurface(key.getPanel());
		layered.add(con, 0);
		vf.setContentPane(layered);
		vf.pack();
		vf.setVisible(true);
	}
	
	//////////////////////////// Mutator Methods /////////////////////////
	
	public void addBush(CellKey key) { sur.addImage(new BushTile(new TileKey(key)));}
	public void addGrass(CellKey key) { sur.addImage(new GrassTile(new TileKey(key)));}
	public void addRock(CellKey key) { sur.addImage(new RockTile(new TileKey(key)));}
	public void addTree(CellKey key) { sur.addImage(new TreeTile(new TileKey(key)));}
	public void addBob(PlayerKey key) {sur.addCharacter(new Bob(new BobKey(key)));}
	
	public void setScale(double factor) {
		GameKey.updateRatio(factor);
		//TODO write code to rescale images.
		update();
	}
	
	public void shift(double[] coords) {
		ImageKey.updateShift(coords);
		update();
	}
	
	public void clearImages() {
		sur.clearImages();
	}
	
	public void update() {
		sur.render();
	}
	
	
	
}
