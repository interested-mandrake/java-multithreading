package demo3;

// a third way to create a thread is using an anoymous class to give implementation
// for the run method of the Runnable interface
public class App {
	public static void main(String [] args) {
		Thread t1 = new Thread(new Runnable() {

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
			
		});
		
		t1.start();
	}
}
