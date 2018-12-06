package view.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Animation is implemented to load png images from Spriter.
 * File names are formated as "name_###" to indicate indexes with three places. 
 * @author dvdco
 *
 */
class Animation {
	
	BufferedImage[] anim;
	int frames;
	
	Animation(String path, int frames){
		this.frames = frames;
		this.anim = new BufferedImage[frames];
		
		//Loads Images
		String index = null;
		for(int i = 0; i < frames; i++) {
			if (i < 10) {
				index = "_00" + i;
			}
			else if (i <100) {
				index = "_0" + i;
			}
			else if (i < 1000) {
				index = "_" + i;
			}
			else {
				System.err.println("Error in Animation: " + path + " has index larger than three digits.");
			}
			
			File file = new File(path + index +".png");
			try {
				anim[i] = ImageIO.read(file);
			}catch(IOException e) {
				System.err.println("Could not read " + path + index);
			}
		}
	}
	
	BufferedImage getResting() {
		return anim[0];
	}
	
	BufferedImage getFrame(int index) {
		return anim[index];
	}
	
}
