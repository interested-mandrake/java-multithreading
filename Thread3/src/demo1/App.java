package demo1;

// atomic operation - an operation that happens in "one step"
// the problem of two threads reading/writing from/to a value can
// be resolved using an AtomicInteger or synchronization
public class App {
	private int count = 0;
	
	public synchronized void increment() {
		count++;
	}
	
	public static void main(String [] args) {
		App app = new App();
		app.doWork();
	}
	
	public void doWork() {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i = 0; i < 10000; i++) {
					increment();
					// 3 steps: 1. get value of count, 2. add one to it, 3. store it back in count
				}
				
			}
			
		});
		
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i = 0; i < 10000; i++) {
					increment();
				}
				
			}
			
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // join pauses execution of the main thread (the thread that calls it) until the thread on which join has been called has completed
		
		System.out.println("count is: " + count);
	}
}
