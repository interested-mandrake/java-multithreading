package com.company;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
// every object in java has a wait and a notify. so in reentrant lock class, the methods are called await, signal,
// and signalAll, instead of wait, notify, and notifyAll

// re-entrant lock - a sort of alternative to the synchronized keyword
class Runner {
    private int count = 0;
    private Lock lock = new ReentrantLock(); // this class (ReentrantLock) keeps track of how many times it's been locked, and then you'll have to unlock it the same number of times
    // there are situations in which re-entrant lock is better to use than synchronized
    private Condition cond = lock.newCondition(); // each ReentrantLock has a Condition property. methods await and signal can only be called once the lock has been locked. await and signal are methods of the Condition object

    private void increment() {
        for(int i =0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock();

        System.out.println("Waiting....");
        cond.await();

        System.out.println("Woken up!");
        try {
            increment(); // but what if this method throws an exception? your code would never unlock. so you should ALWAYS call method in try/catch then in finally block, call unlock
        } finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");

        cond.signal(); // wakes up waiting thread

        try {
            increment(); // but what if this method throws an exception? your code would never unlock. so you should ALWAYS call method in try/catch then in finally block, call unlock
        } finally {
            lock.unlock(); // remember to unlock after you call cond.signal()
        }
    }

    public void finished() {
        System.out.println("Count is: " + count);
    }
}

public class Main {

    public static void main(String[] args) {
        final Runner runner = new Runner();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.secondThread();
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

        runner.finished();
    }
}
