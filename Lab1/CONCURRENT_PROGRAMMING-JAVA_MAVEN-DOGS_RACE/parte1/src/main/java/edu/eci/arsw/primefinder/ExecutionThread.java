package edu.eci.arsw.primefinder;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;

public class ExecutionThread extends Thread {
    PrimeFinderThread pf;
    PrimeFinderThread pf2;
    PrimeFinderThread pf3;
    private static final class Lock { }
    private final Object lock;
    public ExecutionThread(PrimeFinderThread a,PrimeFinderThread b,PrimeFinderThread c){
        pf = a;
        pf2 = b;
        pf3 = c;
        lock = new Lock();
    }
    public void run() {
        Date startDate = new Date();
        Date endDate;
        int numSeconds = 0;
        while (numSeconds < 5) {
            endDate = new Date();
            numSeconds = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
        }
        try {
            pf.wait();
            pf2.wait();
            pf3.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (lock) {
            try {

                pf.wait();
                pf2.wait();
                pf3.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        Scanner scanner = new Scanner(System.in);
        String entrada  ="";
        do{
            System.out.println("Hemos pausado el programa hasta que undas ENTER");
            entrada  = scanner.nextLine();
        }
        while(!entrada.equals(""));
        pf.notify();
        pf2.notify();
        pf3.notify();
    }
}
