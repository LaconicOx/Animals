package game;

import java.util.PriorityQueue;

import commands.swing.Command;
import commands.swing.CommandFactory;
import model.ModelFacade;
import view.ViewFacade;

public class GameLoop implements Runnable{
	
	private PriorityQueue<Command> queue;
	private ViewFacade view;
	private ModelFacade model;
	private Thread loop;
	
	//Statistics
	//private long startTime = 0L;
	//private double updates = 0;
	
	//The volatile prefix prevents stops running and gameOver from being copied into the local 
		//memory of the GUI thread or animation thread.
		private volatile boolean running = false;
		
	GameLoop(Game game){
		model = new ModelFacade(game);
		view = new ViewFacade(game);
		CommandFactory.init(game, model, view);
		queue = game.getQueue();
		

		startLoop();
	}
	
	
	private void startLoop() {
		loop = new Thread(this);
		loop.start();
	}
	
	public void run() {
		//startTime = System.nanoTime();
		running = true;
		model.getDrawCommands();
		while(running) {
			process();
			//updates++;
		}
		//System.out.println(updates);
	}
	
	
	private void process() {
		while(!queue.isEmpty()) {
			queue.poll().execute();
		}
	}
}
