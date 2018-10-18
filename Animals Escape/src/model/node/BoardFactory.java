package model.node;


import java.util.HashSet;

public class BoardFactory {

	public static void setTotal(int horzSteps, int verSteps) {
		//Gets keys
		HashSet<NodeKey> keys = genKeys(new NodeKey(0,0), horzSteps, verSteps);
		
		for(NodeKey key : keys)
			NodeFactory.getNodeInstance(key);
	}
	
	static HashSet<NodeKey> genKeys(NodeKey center, int horzSteps, int verSteps){
		HashSet<NodeKey> keys = new HashSet<NodeKey>();
		int x = center.getX();
		int y = center.getY();
		
		//Initializes keys for the center column.
		keys.add(center);
		for (int i = 1; i <= verSteps; i++) {
			keys.add(new NodeKey(x, y + i * 2));
			keys.add(new NodeKey(x, y + -i * 2));
		}
		
		//Initializes remaining columns of keys.
		for (int i = 1; i <= horzSteps; i++) {
			int right = x + i;
			int left = x - i;
			if (i % 2 == 0) {
				keys.add(new NodeKey(right, y));
				keys.add(new NodeKey(left, y));
				for(int j = 1; j <= verSteps; j++) {
					keys.add(new NodeKey(right, y + j * 2));
					keys.add(new NodeKey(right, y + -j * 2));
					keys.add(new NodeKey(left, y + j * 2));
					keys.add(new NodeKey(left, y + -j * 2));
				}
			}
			else {
				for(int j = 1; j <= verSteps; j++) {
					keys.add(new NodeKey(right, y + j * 2 - 1));
					keys.add(new NodeKey(right, y + j * -2 + 1));
					keys.add(new NodeKey(left, y + j * 2 - 1));
					keys.add(new NodeKey(left, y + j * -2 + 1));
				}
			}	
		}
		
		return keys;
	}
	
}
