/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import java.util.Queue;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author hcadavid
 */
public class Consumer extends Thread{
    
    private Queue<Integer> queue;
    private Mutex m;
    
    
    public Consumer(Queue<Integer> queue, Mutex m){
        this.queue=queue;
        this.m = m;
    }
    
    @Override
    public void run() {
        int elem;
        while (true) {
            synchronized (m) {
                try {
                    m.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                elem = queue.poll();
                System.out.println("Consumer consumes " + elem);
            }



        }
            

    }
}
