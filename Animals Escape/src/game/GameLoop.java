package game;

import model.ModelFacade;
import view.ViewInterface;

public class GameLoop implements Runnable{
	
	private static final long IDEAL= 16666667;
	
	private ViewInterface view;
	private ModelFacade model;
	private Thread loop;
	
	//Statistics
	private long startTime = 0L;
	private double updates = 0;
	
	//The volatile prefix prevents stops running and gameOver from being copied into the local 
	//memory of the GUI thread or animation thread.
	private volatile boolean running = false;
		
	GameLoop(Game game, ViewInterface view, ModelFacade model){
		this.view = view;
		this.model = model;
		model.update();//initializing the model before starting the loop provides more accurate FPS readings
		startLoop();
		//test();
	}
	
	private void test() {
		model.update();
	}
	
	private void startLoop() {
		loop = new Thread(this);
		loop.start();
	}
	
	public void run() {
		startTime = System.nanoTime();
		running = true;
		
		long beforeTime = 0;
		long sleepTime = 0;
		
		while(running) {
			beforeTime = System.nanoTime();
			model.update();
			view.render();
			updates++;
			
			//Takes the difference between the actual time interval and the ideal interval.
			//Then converts to milliseconds
			sleepTime = (IDEAL - (System.nanoTime() - beforeTime))/1000000;
			if(sleepTime < 0) {
				sleepTime = 0;
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long elapsed = System.nanoTime() - startTime;
			double ups = 1000000000 * updates/elapsed;
			//System.out.println(ups);
		}//End of run()
	}
	
	
}
