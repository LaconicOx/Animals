package game;

import model.ModelFacade;
import view.ViewInterface;

public class GameLoop implements Runnable{
	
	private static final long SLEEP_TIME= 2L;
	
	private ViewInterface view;
	private ModelFacade model;
	private Thread loop;
	
	//Statistics
	//private long startTime = 0L;
	//private double updates = 0;
	
	//The volatile prefix prevents stops running and gameOver from being copied into the local 
		//memory of the GUI thread or animation thread.
		private volatile boolean running = false;
		
	GameLoop(Game game, ViewInterface view, ModelFacade model){
		this.view = view;
		this.model = model;
		startLoop();
	}
	
	
	private void startLoop() {
		loop = new Thread(this);
		loop.start();
	}
	
	public void run() {
		//startTime = System.nanoTime();
		running = true;
		 
		model.primeAnimation();
		view.render();
		while(running) {
			//model.primeAnimation();
			//model.update();
			//view.render();
			//updates++;
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("GameLoop running");
		}
		//System.out.println(updates);
	}
	
	
}
