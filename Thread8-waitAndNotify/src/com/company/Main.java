package com.company;

import java.util.Scanner;

class Processor {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("producing...");
            wait(); // waits in such a way as to not consume lots of system resources. can only be called within synchronized code blocks. the rhread will now now not resume until the thread regains control
            System.out.println("Resumed.");
        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);

        synchronized (this) {
            System.out.println("Waiting for return key.");
            scanner.nextLine();
            System.out.println("Return key pressed");
            notify(); // notify, like wait, can only be called within synchronized code block. notifies other thread to wake up, but does not hand over object lock.
            Thread.sleep(5000);
        }
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final Processor processor = new Processor();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }



}
