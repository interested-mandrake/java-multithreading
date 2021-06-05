import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// when two threads are updating the same list, we can get not enough
// items updated or ArrayIndexOutOfBounds exceptions

// the synchronized keyword will acquire the intrinsic lock, or object-level
// lock, on its parent object (in this case the Worker object)

// only one synchronized method can run on a class at once since the 
// synchronized keyowrd is acquiring an object-level lock

// what we want here is to prevent two threads from running stageOne simultaneously
// or running stageTwo simultaneously. stageOne and stageTwo, however, can process concurrently
public class Worker {
	
	private Random random = new Random();
	
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();
	
	public void stageOne() {
		
		synchronized (lock1) { // using a synchronized block here rather than synchronized at the method level, we resolve our issue
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			list1.add(random.nextInt(100));
		}
	}
	
	public void stageTwo() {
		synchronized (lock2) { // using a synchronized block here rather than synchronized at the method level, we resolve our issue
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
		
		
	}
	
	public void process() {
		for(int i = 0; i < 1000; i++) {
			stageOne();
			stageTwo();
		}
	}
	
	public void main() {
		System.out.println("Starting...");
		
		long start = System.currentTimeMillis();
		
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();
			}
			
		});
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();
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
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time elapsed: " + (end - start));
		System.out.println("List1 size: " + list1.size());
		System.out.println("List2 size: " + list2.size());
	}
}
