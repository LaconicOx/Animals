package game;


import java.util.PriorityQueue;

import commands.swing.Command;

public class Game {
	
	//Game Fields
	
	
	//Game Components
	PriorityQueue<Command> queue;
	GameLoop loop;
	
	/////////////////////////////// Constructor ///////////////////////////////////
	public Game() {
		queue = new PriorityQueue<>();
		loop = new GameLoop(this);
	}
	
	
	/////////////////////////// Public Methods ///////////////////////////////////////
	
	public void enqueCommand(Command cmd) {
		queue.add(cmd);
	}
	
	/////////////////////////// Getter Methods /////////////////////////////////////
	public PriorityQueue<Command> getQueue() {
		return queue;
	}
	
	////////////////////////////// Main Function //////////////////////////////////
	public static void main(String[] args) {
		new Game();
	}
}
