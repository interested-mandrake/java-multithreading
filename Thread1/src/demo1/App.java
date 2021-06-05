package demo1;

class Runner extends Thread {

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println("hello " + i);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

public class App {
	public static void main(String args[]) {
		Runner runner1 = new Runner();
		runner1.start(); // calling start starts the code in the run method on a new thread, whereas calling run directly would perform the work on the main thread, like a regular method
	
		Runner runner2 = new Runner();
		runner2.start();
	}
}
