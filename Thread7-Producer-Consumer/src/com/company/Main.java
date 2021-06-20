package com.company;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue(10); // a fifo data structure. it is thread-safe, so can be accessed from multiple threads

    public static void main(String[] args) {
	    Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                producer();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void producer() {
        Random random = new Random();

        while(true) {
            try {
                queue.put(random.nextInt(100)); // if the queue is full, will wait until there is space in the queue, and then will execute
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consumer() throws InterruptedException {
        Random random = new Random();

        while(true) {
            Thread.sleep(100);

            if(random.nextInt(10) == 0) {
                Integer value = queue.take(); // if there's nothing in the queue, take waits, utnil something coems into the queue to take it. doesn't consume many resources while taking

                System.out.println("Taken value: " + value + "; Queue size is: " + queue.size());
            }
        }
    }
}
