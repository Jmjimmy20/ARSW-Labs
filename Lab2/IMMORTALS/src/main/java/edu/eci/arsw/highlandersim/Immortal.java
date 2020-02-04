package edu.eci.arsw.highlandersim;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;
    
    private AtomicInteger health;
    
    private int defaultDamageValue;

    private final CopyOnWriteArrayList<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());
    private boolean estaPeleando;
    private Mutex m,mutex,mutexPelea;
    private boolean threadEjecutandose;


    public Immortal(String name, CopyOnWriteArrayList<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb, Mutex m, Mutex otroMutex, Mutex mutexPelea) {
        super(name);
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = new AtomicInteger(health);
        this.defaultDamageValue=defaultDamageValue;
        estaPeleando = true;
        threadEjecutandose = true;
        this.m = m;
        mutex = new Mutex();
        this.mutexPelea = mutexPelea;
    }

    public void run() {
        while (threadEjecutandose) {
            if (immortalsPopulation.size() == 1){
                break;
            }
            while (!estaPeleando){
                synchronized (m) {
                    try {
                        m.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Immortal im;
            int myIndex = immortalsPopulation.indexOf(this);
            int nextFighterIndex = r.nextInt(immortalsPopulation.size());
            //avoid self-fight
            if (nextFighterIndex == myIndex) {
                nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
            }
            im = immortalsPopulation.get(nextFighterIndex);
            this.fight(im);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



    }
    public void detenerThread(){ threadEjecutandose = false;}
    public void paraDePelear(){
        estaPeleando = false;
    }
    public void siguePeleando(){
        estaPeleando = true;
        synchronized (m) {
            m.notify();
        }
    }

    public void fight(Immortal i2) {
        synchronized (i2) {
            if (i2.getHealth() > 0 && this.getHealth() > 0) {
                i2.cambiarVida(-defaultDamageValue);
                cambiarVida(defaultDamageValue);
                synchronized (updateCallback) {
                    updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");
                }
            }
        }
    }

    public void cambiarVida(int vida){
        health.addAndGet(vida);
        if (health.get() <= 0){
            paraDePelear();
            detenerThread();
            immortalsPopulation.remove(this);
        }
    }


    public AtomicInteger getAtomicHealth(){
        return health;
    }

    public int getHealth() {
        return health.get();
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

}
