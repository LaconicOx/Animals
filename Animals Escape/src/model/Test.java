package model;

public class Test {
	
	public static void main(String[] args) {
		ModelKey test = new ModelKey(new int[]{0,0});
		double[] point = {0.0, 1.01};
		System.out.println(test.checkPoint(point));
	}
	
	
}
