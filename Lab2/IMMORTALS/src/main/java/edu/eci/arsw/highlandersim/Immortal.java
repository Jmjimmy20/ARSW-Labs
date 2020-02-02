package edu.eci.arsw.highlandersim;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;
    
    private AtomicInteger health;
    
    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());
    private boolean estaPeleando;
    private Mutex m;


    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb, Mutex m) {
        super(name);
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = new AtomicInteger(health);
        this.defaultDamageValue=defaultDamageValue;
        estaPeleando = true;
        this.m = m;
    }

    public void run() {

        while (true) {
            while (!estaPeleando){
                try {
                    synchronized (m) {
                        m.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void paraDePelear(){
        estaPeleando = false;
    }
    public void siguePeleando(){
        estaPeleando = true;
    }

    public void fight(Immortal i2) {

        if (i2.getHealth() > 0) {
            i2.getAtomicHealth().addAndGet(defaultDamageValue*-1);
            health.addAndGet(defaultDamageValue);
            updateCallback.processReport("Fight: " + this + " vs " + i2+"\n");
        } else {
            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
        }

    }

    public void changeHealth(int v) {
        health.set(v);
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
