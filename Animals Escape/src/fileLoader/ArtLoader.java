package fileLoader;

import java.io.File;

public class ArtLoader {
	private static File baseHex;
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
		baseHex = new File("Base Hex.png");
	}
	
	/////////////////////// Accessor Methods ///////////////////////
	
	public File getBaseHex() {return baseHex;}
	
}
