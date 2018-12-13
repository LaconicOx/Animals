package image_library;

import java.util.HashMap;
import java.util.Set;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SwingLoader {
	
	//Path Names
	private static final String BUSH_PATH = "BushHex";
	private static final String GRASS_PATH = "GrassHex";
	private static final String ROCK_PATH = "RockHex";
	private static final String TREE_PATH = "TreeHex";
	
	//Frame Counts
	private static final int BUSH_FRAMES = 1;
	private static final int GRASS_FRAMES = 1;
	private static final int ROCK_FRAMES = 1;
	private static final int TREE_FRAMES = 1;
	
	//Image Dimensions in Pixels
	private static final double[] TILES = {300.0, 261.0};
	
	//Stores file information to initialize IMAGES.
	private static HashMap<String, Integer> INFO;
	static {
		INFO = new HashMap<>();
		INFO.put(BUSH_PATH, BUSH_FRAMES);
		INFO.put(GRASS_PATH, GRASS_FRAMES);
		INFO.put(ROCK_PATH, ROCK_FRAMES);
		INFO.put(TREE_PATH, TREE_FRAMES);
	}
	
	//Stores image dimensions.
	private static HashMap<String, double[]> DIMENSIONS;
	static {
		DIMENSIONS = new HashMap<>();
		DIMENSIONS.put(BUSH_PATH, TILES);
		DIMENSIONS.put(GRASS_PATH, TILES);
		DIMENSIONS.put(ROCK_PATH, TILES);
		DIMENSIONS.put(TREE_PATH, TILES);
	}
	
	//Stores image files.
	private static HashMap<String, SwingAnimation> ANIMATIONS;
	static {
		ANIMATIONS = new HashMap<>();
		Set<String> keys = INFO.keySet();
		
		for(String key : keys) {
			int frames = INFO.get(key);
			BufferedImage[] animation = new BufferedImage[frames];
			
			
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
					System.err.println("Error in Animation: " + key + " has index larger than three digits.");
					System.exit(0);
				}
				
				File file = new File(key + index +".png");
				try {
					animation[i] = ImageIO.read(file);
				}catch(IOException e) {
					System.err.println("Could not read " + key + index);
					System.exit(0);
				}
			}
			ANIMATIONS.put(key, new SwingAnimation(animation, DIMENSIONS.get(key)));
		}
	}
	
	//////////////////////// Static Methods /////////////////////
	
	public static SwingAnimation getBush() {
		return ANIMATIONS.get(BUSH_PATH);
	}
	
	public static SwingAnimation getGrass() {
		return ANIMATIONS.get(GRASS_PATH);
	}
	
	public static SwingAnimation getRock() {
		return ANIMATIONS.get(ROCK_PATH);
	}
	
	public static SwingAnimation getTree() {
		return ANIMATIONS.get(TREE_PATH);
	}
	
}
