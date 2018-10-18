package commands.swing;

import java.awt.Color;

import view.ViewFacade;

public class DrawOak extends DrawTile{
	DrawOak(ViewFacade vf, double[][] vertices){
		super(vf, vertices, new Color(69,55,3));
	}
}
