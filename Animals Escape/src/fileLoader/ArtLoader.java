package fileLoader;

import java.io.File;

public class ArtLoader {
	
	private static File bushHex;
	private static File grassHex;
	private static File rockHex;
	private static File treeHex;
	
	static ArtLoader unique = null;
	
	private ArtLoader() {
		init();
	}
	
	public static ArtLoader getInstance() {
		if (unique == null)
			unique = new ArtLoader();
		return unique;
	}
	
	private void init() {
		bushHex = new File("BushHex.png");
		grassHex = new File("GrassHex.png");
		rockHex = new File("RockHex.png");
		treeHex = new File("TreeHex.png");
	}
	
	/////////////////////// Accessor Methods ///////////////////////
	
	public File getBushHex() {return bushHex;}
	public File getGrassHex() {return grassHex;}
	public File getRockHex() {return rockHex;}
	public File getTreeHex() {return treeHex;}
	
}
