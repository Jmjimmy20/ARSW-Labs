package edu.eci.arsw.primefinder;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;

public class ExecutionThread extends Thread {
    PrimeFinderThread pf;
    PrimeFinderThread pf2;
    PrimeFinderThread pf3;
    public ExecutionThread(PrimeFinderThread a,PrimeFinderThread b,PrimeFinderThread c){
        pf = a;
        pf2 = b;
        pf3 = c;
    }
    public void run() {
        Date startDate = new Date();
        Date endDate;
        int numSeconds = 0;
        while (numSeconds < 5) {
            endDate = new Date();
            numSeconds = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
        }
        pf.suspend();
        pf2.suspend();
        pf3.suspend();
        for(int i: pf.getPrimes()){
            System.out.println(i);
        }
        for(int i: pf2.getPrimes()){
            System.out.println(i);
        }
        for(int i: pf3.getPrimes()){
            System.out.println(i);
        }
        pf.cleanPrimes();
        pf2.cleanPrimes();
        pf3.cleanPrimes();
        Scanner scanner = new Scanner(System.in);
        String entrada  ="";
        do{
            System.out.println("Hemos pausado el programa hasta que undas ENTER");
            entrada  = scanner.nextLine();
        }
        while(!entrada.equals(""));
        pf.resume();
        pf2.resume();
        pf3.resume();
        try {
            pf.join();
            pf2.join();
            pf3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i: pf.getPrimes()){
            System.out.println(i);
        }
        for(int i: pf2.getPrimes()){
            System.out.println(i);
        }
        for(int i: pf3.getPrimes()){
            System.out.println(i);
        }
    }
}
