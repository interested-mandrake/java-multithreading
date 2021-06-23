package com.company;

import java.util.LinkedList;
import java.util.Random;

// a worked example of wait and notify
class Processor {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT = 10;
    private Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;

        while(true) {
            // modify shared data within synchronized block
            synchronized (lock) {
                while(list.size() == LIMIT) {
                    lock.wait(); // the point here is you need to call wait on the object that you are locking on
                    // you may often want to surround a call to wait in a loop so you can check that the condition we want to wait on has finally evaluated to true
                }
                list.add(value++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();

        while(true) {
            synchronized (lock) {
                while(list.size() == 0) {
                    lock.wait();
                }
                System.out.print("List size is: " + list.size());
                int value = list.removeFirst();
                System.out.println("; value is: " + value);
                lock.notify(); // if you had more than one thread that could be waiting, you'd want to say notify all
            }

            Thread.sleep(random.nextInt(1000));
        }
    }
}

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}
