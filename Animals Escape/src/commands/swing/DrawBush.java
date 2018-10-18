package commands.swing;

import java.awt.Color;

import view.ViewFacade;

public class DrawBush extends DrawTile{
	DrawBush(ViewFacade vf, double[][] vertices){
		super(vf, vertices, new Color(22,72,34));
	}
}
