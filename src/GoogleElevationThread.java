
public class GoogleElevationThread extends Thread{
	
	boolean running;
	int wait;
	public GoogleElevationThread(int waiting){
		running = false;
		wait = waiting;
	}

	public void start(){
		running = true;
		System.out.println("GoogleElevation Thread started (will execute every " + wait + " milliseconds.)"); 
		super.start();
	}
	
	public void run(){
		
	}
}
