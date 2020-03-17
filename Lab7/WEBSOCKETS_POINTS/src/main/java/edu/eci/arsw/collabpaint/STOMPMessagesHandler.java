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
        List<Point> temporal = new ArrayList<>();
        if(ptsTemp.size()%4==0){
            for(int i = 0; i<ptsTemp.size();i++){
                if (i%4==0){
                    if(i!=0){
                        msgt.convertAndSend("/topic/newpolygon."+numdibujo, temporal);
                    }
                    temporal = new ArrayList<>();
                }
                temporal.add(ptsTemp.get(i));
            }
            msgt.convertAndSend("/topic/newpolygon."+numdibujo, temporal);
        } else if ((ptsTemp.size()-1)%4==0){
            for(int i = 0; i<ptsTemp.size()-1;i++){
                if (i%4==0){
                    if(i!=0){
                        msgt.convertAndSend("/topic/newpolygon."+numdibujo, temporal);
                    }
                    temporal = new ArrayList<>();
                }
                temporal.add(ptsTemp.get(i));
            }
            msgt.convertAndSend("/topic/newpolygon."+numdibujo, temporal);

        } else if ((ptsTemp.size()-2)%4==0){
            for(int i = 0; i<ptsTemp.size()-2;i++){
                if (i%4==0){
                    if(i!=0){
                        msgt.convertAndSend("/topic/newpolygon."+numdibujo, temporal);
                    }
                    temporal = new ArrayList<>();
                }
                temporal.add(ptsTemp.get(i));
            }
            msgt.convertAndSend("/topic/newpolygon."+numdibujo, temporal);
        } else if ((ptsTemp.size()-3)%4==0){
            for(int i = 0; i<ptsTemp.size()-3;i++){
                if (i%4==0){
                    if(i!=0){
                        msgt.convertAndSend("/topic/newpolygon."+numdibujo, temporal);
                    }
                    temporal = new ArrayList<>();
                }
                temporal.add(ptsTemp.get(i));
            }
            msgt.convertAndSend("/topic/newpolygon."+numdibujo, temporal);
        }

    }
}
