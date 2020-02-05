package edu.eci.arsw;

import org.junit.Before;
import org.junit.Test;

import edu.eci.arsw.highlandersim.ControlFrame;
import edu.eci.arsw.highlandersim.Immortal;

import static org.junit.Assert.*;

import java.util.concurrent.CopyOnWriteArrayList;

public class InmmortalTest{


    private Immortal immortal;
    private CopyOnWriteArrayList<Immortal> immortals;
    ControlFrame frame;
    boolean isPause;

    @Before
    public void setUp() {
        frame = new ControlFrame();
    }


    @Test
    public  void testPauseButton(){
        immortals = frame.setupInmortals();
        if (immortals != null) {
            for (Immortal im : immortals) {
                im.start();
            }

        }
        for (Immortal im : immortals) {
            im.paraDePelear();
            isPause =  im.estaPeleando; 
        }
        
        assertFalse(isPause);
    }


    /*
    @Test
    public void testContinueButton(){
        immortals = frame.setupInmortals();
        int vida = 10000;
        boolean isChange = false;
        if (immortals != null) {
            for (Immortal im : immortals) {
                im.start();
            }

        }
        for (Immortal im : immortals) {
            im.cambiarVida(vida);
            if(im.getHealth() == vida) isChange = true;
            System.out.println(im.getHealth() + " bla" );
        }
        System.out.println(isChange);
        assertTrue(isChange);
    }*/

    @Test
    public void testStopButton(){
        immortals = frame.setupInmortals();
        if (immortals != null) {
            for (Immortal im : immortals) {
                im.start();
            }

        }
        for (Immortal im : immortals) {
            im.paraDePelear();
        }

        for (Immortal im : immortals) {
            im.detenerThread();
            isPause =  im.estaPeleando; 
        }
        assertFalse(isPause);
    }

    @Test
    public  void testContinueButton(){
        immortals = frame.setupInmortals();
        if (immortals != null) {
            for (Immortal im : immortals) {
                im.start();
            }

        }
        for (Immortal im : immortals) {
            im.paraDePelear();
            isPause =  im.estaPeleando; 
        }

        for (Immortal im : immortals) {
            im.siguePeleando();
            isPause =  im.estaPeleando; 
        }
        
        assertTrue(isPause);
    }


}
