package view;


import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import commands.swing.CommandFactory;

class ControlSurface extends JPanel{
	private static final long serialVersionUID = -8270240225014168071L;
	private Dimension dim;
	
	ControlSurface(Dimension dim){
		this.dim = dim;
		setSize(this.dim);
		setOpaque(false);
		setFocusable(true);
		requestFocus();
		addKeyListener(new UserKeys());
	}
	
	private class UserKeys extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch(keyCode) {
			case KeyEvent.VK_W: CommandFactory.getUpdatePlayer(0.0, 1.0);
				break;
			case KeyEvent.VK_A: CommandFactory.getUpdatePlayer(1.0, 0);
				break;
			case KeyEvent.VK_D: CommandFactory.getUpdatePlayer(-1.0, 0.0);
				break;
			case KeyEvent.VK_S: CommandFactory.getUpdatePlayer(0.0, -1.0);
				break;
			case KeyEvent.VK_I: CommandFactory.getUpdateScale(1.2);
				break;
			case KeyEvent.VK_K: CommandFactory.getUpdateScale(0.8);
			}
		}
	}
}
