package view;


import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import game.Game;

class ControlSurface extends JPanel{
	private static final long serialVersionUID = -8270240225014168071L;
	
	private Dimension dim;
	private Game game;
	private SwingFacade sf;
	
	ControlSurface(Game game, SwingFacade sf, Dimension dim){
		this.dim = dim;
		this.game = game;
		this.sf = sf;
		setSize(this.dim);
		setOpaque(false);
		setFocusable(true);
		requestFocus();
		addKeyListener(new UserKeys());
	}
	
	private class UserKeys extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			double angle = Math.PI / 2.0;
			
			switch(keyCode) {
			case KeyEvent.VK_W: game.movePlayer(angle);//Moves up.
				break;
			case KeyEvent.VK_A: game.movePlayer(2 * angle);//Moves left.
				break;
			case KeyEvent.VK_D: game.movePlayer(0.0);//Moves right
				break;
			case KeyEvent.VK_S: game.movePlayer(3 * angle);//Moves down.
				break;
			case KeyEvent.VK_I: game.screenDilate(1.2);//Zooms in.
				break;
			case KeyEvent.VK_K: game.screenDilate(0.8);//Zooms out.
				break;
			}
		}
		
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			switch(keyCode) {
			case KeyEvent.VK_W: game.stopPlayer();
				break;
			case KeyEvent.VK_A: game.stopPlayer();
				break;
			case KeyEvent.VK_D: game.stopPlayer();
				break;
			case KeyEvent.VK_S: game.stopPlayer();
				break;
			}
		}
	}
}
