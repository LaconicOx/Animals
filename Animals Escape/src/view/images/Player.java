package view.images;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Player extends GameImage{
	double radius = 10;
	Ellipse2D im;
	
	public Player(Point2D p){
		im = new Ellipse2D.Double(p.getX() - radius, p.getY() - radius, 2*radius, 2*radius);
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.BLUE);
		g2.fill(im);
	}
}
