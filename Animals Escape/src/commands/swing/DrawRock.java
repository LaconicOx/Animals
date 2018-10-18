package commands.swing;

import java.awt.Color;

import view.ViewFacade;

public class DrawRock extends DrawTile{
	DrawRock(ViewFacade vf, double[][] vertices){
		super(vf, vertices,  new Color(159,165,160));
	}
}
