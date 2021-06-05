package demo1;

import java.util.Scanner;

// a technique for stopping execution of a thread
class Processor extends Thread {

	private boolean running = true;
	
	@Override
	public void run() {
		while(running) {
			System.out.println("hi");
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown() {
		running = false;
	}
	
}

public class App {
	public static void main(String args []) {
		Processor proc1 = new Processor();
		proc1.start();
		
		System.out.println("Press enter to stop...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		
		proc1.shutdown();
	}
}
