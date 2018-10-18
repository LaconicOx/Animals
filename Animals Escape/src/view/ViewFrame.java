package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

class ViewFrame extends JFrame{

	private static final long serialVersionUID = 4797069952403888542L;
	
	ViewSurface sur;
	ControlSurface con;
	JMenuBar menuBar;
	
	ViewFrame(Dimension dim){
		super("Animals Escape");
		setSize(dim);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Initializes the menu bar.
		menuBar = new JMenuBar();
		JMenu game = new JMenu("Game");
		menuBar.add(game);
		setJMenuBar(menuBar);
		
		//pack();
		//setVisible(true);
	}
	
	
}
