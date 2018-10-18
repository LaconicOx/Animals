package view;

import java.io.File;

public class Test {
	
public static void main(String[] args) {
	
	File testHex = new File("Base Hex.png");
	
	ViewFacade vf = new ViewFacade();
	
	double[] center = {250.0, 250.0};
	double[] north = {250.0, 250.0 - 150.0 * Math.sqrt(3.0)};
	double[] south = {250.0, 250.0 + 150.0 * Math.sqrt(3.0)};
	double[] northeast = {250.0 + 225.0, 250.0 - 75.0 * Math.sqrt(3.0)};
	double[] northwest = {250.0 - 225.0, 250.0 - 75.0 * Math.sqrt(3.0)};
	double[] southeast = {250.0 + 225.0, 250.0 + 75.0 * Math.sqrt(3.0)};
	double[] southwest = {250.0 - 225.0, 250.0 + 75.0 * Math.sqrt(3.0)};
	
	vf.testImages(testHex, center);
	vf.testImages(testHex, north);
	vf.testImages(testHex, south);
	vf.testImages(testHex, northeast);
	vf.testImages(testHex, northwest);
	vf.testImages(testHex, southeast);
	vf.testImages(testHex, southwest);
	
	vf.update();
	
	}
	
}
