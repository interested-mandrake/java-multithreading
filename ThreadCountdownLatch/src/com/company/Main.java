package com.company;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processor implements Runnable {
    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }
    public void run() {
        System.out.println("Thread begins.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch.countDown(); // when countdown is called the value specified when the countdown latch was declared will be counted down to
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
	    CountDownLatch latch = new CountDownLatch(3); // a class that is thread safe. let's you count down from a number you specify and lets one or more threads wait until the latch reaches a count of 0. then one or more threads waiting on the latch can proceed once it reaches 0
        // multiple threadsd can access and modify the same latch instance safely
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for(int i=0; i<3; i++) {
            executor.submit(new Processor(latch));
        }
        latch.await(); // wait until latch reaches zero

        System.out.println("completed.");
    }
}
