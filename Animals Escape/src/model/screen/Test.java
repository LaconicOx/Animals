package model.screen;

import java.util.HashSet;
import java.util.Set;

import model.board.NodeKey;
import model.board.RockNode;
import model.board.TotalBoard;

public class Test {

	public static void main(String[] args) {
		TotalBoard board = TotalBoard.getInstance();
		NodeKey center = new NodeKey(0,0);
		RockNode node = new RockNode(center);
		BorderCell test1 = new BorderCell(node);
		BorderCell test2 = test1;
		Set<BorderCell> set = new HashSet<>();
		set.add(test1);
		System.out.println(set.size());
		System.out.println(set.contains(test1));
		
		System.out.println("*************************");
		
		NodeKey center2 = new NodeKey(1,1);
		RockNode node2 = new RockNode(center2);
		ScreenCell test3 = new ScreenCell(node2);
		ScreenCell test4 = test3;
		Set<ScreenCell> set2 = new HashSet<>();
		set2.add(test3);
		System.out.println(set2.size());
		System.out.println(set2.contains(test4));
	}

}
