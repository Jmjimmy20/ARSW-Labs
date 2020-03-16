package edu.eci.arsw.collabpaint;

import edu.eci.arsw.collabpaint.model.Point;
import org.assertj.core.internal.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;
    private ConcurrentHashMap<String, ArrayList<Point>> polygon = new ConcurrentHashMap<String, ArrayList<Point>>();
    @MessageMapping("/newpoint.{numdibujo}")
    public void handlePointEvent(Point pt, @DestinationVariable String numdibujo) throws Exception {
        System.out.println("Nuevo punto recibido en el servidor!:"+pt);

        msgt.convertAndSend("/topic/newpoint."+numdibujo, pt);

        ArrayList<Point> ptsTemp = polygon.get(numdibujo);
        if (ptsTemp==null){
            ptsTemp = new ArrayList<Point>();
            polygon.put(numdibujo,ptsTemp);
        }
        ptsTemp.add(pt);
        System.out.println(ptsTemp.size());
        //Dividir en List de 4 y mandar cada uno a /topic/newpolygon.

    }
}
