import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// thread pools are a way of managing lots of threads at the same time

class Processor implements Runnable {
	private int id;
	
	public Processor(int id) {
		this.id = id;
	}
	
	public void run() {
		System.out.println("Starting: " + id);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Completed: " + id);
	}
}

public class App {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2); // creates threadpool of size 2
		
		// we need to submit tasks to the ExecutorService instance
		for(int i = 0; i < 5; i++) {
			executor.submit(new Processor(i));
		}
		
		executor.shutdown(); // waits for all currently submitted threads to complete their task, then shuts down
	
		System.out.println("all tasks submitted.");
		
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("All tasks completed.");
	}
}
