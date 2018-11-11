package model.screen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import model.board.*;
import units.CellKey;
import units.NodeKey;

public class Test {

	public static void main(String[] args) {
		TotalBoard board = TotalBoard.getInstance();
		NodeKey n = new NodeKey(new int[] {0,0});
		NodeKey m = new NodeKey(new int[] {0,0});
		Node node = new RockNode(n);
		CellKey testN = new CellKey(node);
		CellKey testM = new CellKey(node);
		HashMap<CellKey, String> test = new HashMap<>();
		test.put(testN, "Found");
		System.out.println(testN.equals(testM));
		System.out.println(test.get(testM));
	}

}
