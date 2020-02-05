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


}
