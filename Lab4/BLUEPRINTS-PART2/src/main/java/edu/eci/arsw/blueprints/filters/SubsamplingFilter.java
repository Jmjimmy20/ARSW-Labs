package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service("subsamplingFilter")
public class SubsamplingFilter implements CommonFilter {
    @Override
    public Blueprint filter(Blueprint blueprint) {
        ArrayList<Point> temporal = new ArrayList<Point>(blueprint.getPoints());
        ArrayList<Point> puntosNuevos = new ArrayList<Point>();
        boolean bandera = true;
        for (Point punto:temporal){
            if(bandera){
                puntosNuevos.add(punto);
            }
            bandera=!bandera;
        }
        Point[] puntosQueSon = new Point[puntosNuevos.size()];
        for (int j = 0;j<puntosNuevos.size();j++){
            puntosQueSon[j]=puntosNuevos.get(j);
        }
        return new Blueprint(blueprint.getAuthor(),blueprint.getName(),puntosQueSon);
    }
}
