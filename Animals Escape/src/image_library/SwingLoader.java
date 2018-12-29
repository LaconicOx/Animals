package image_library;

import java.util.HashMap;
import java.util.Set;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SwingLoader {
	
	//Path Names
	private static final String GRASS_NORTH_PATH = "Grass/Grass_North/Grass_North";
	private static final String ROCK_PATH = "Rock/Rock";
	private static final String TREE_PATH = "Tree/Tree_North/Tree_North";
	private static final String PLAYER_EAST_PATH = "Player_East/Player_East";
	
	//Frame Counts
	private static final int BUSH_NORTH_FRAMES = 62;
	
	private static final int ROCK_FRAMES = 1;
	private static final int TREE_FRAMES = 62;
	private static final int PLAYER_EAST_FRAMES = 25;
	
	//Stores file information to initialize IMAGES.
	private static HashMap<String, Integer> INFO;
	static {
		INFO = new HashMap<>();
		INFO.put(BUSH_NORTH_PATH, BUSH_NORTH_FRAMES);
		INFO.put(GRASS_NORTH_PATH, GRASS_FRAMES);
		INFO.put(ROCK_PATH, ROCK_FRAMES);
		INFO.put(TREE_PATH, TREE_FRAMES);
		INFO.put(PLAYER_EAST_PATH, PLAYER_EAST_FRAMES);
	}
	
	//Image Dimensions in Pixels
		private static final double[] BUSH_DIM = {311.0, 270.0};
		
		private static final double[] TREE_NORTH_DIM = {311.0, 270.0};
		private static final double[] ROCK_DIM = {311.0, 270.0};
		private static final double[] PLAYER_EAST = {435.0, 762.0};
		
	//Stores image dimensions.
	private static HashMap<String, double[]> DIMENSIONS;
	static {
		DIMENSIONS = new HashMap<>();
		DIMENSIONS.put(BUSH_NORTH_PATH, BUSH_DIM);
		DIMENSIONS.put(GRASS_NORTH_PATH, GRASS_NORTH_DIM);
		DIMENSIONS.put(ROCK_PATH, ROCK_DIM);
		DIMENSIONS.put(TREE_PATH, TREE_NORTH_DIM);
		DIMENSIONS.put(PLAYER_EAST_PATH, PLAYER_EAST);
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
	
	public static SwingAnimation getAnimation() {
		
	}
}
