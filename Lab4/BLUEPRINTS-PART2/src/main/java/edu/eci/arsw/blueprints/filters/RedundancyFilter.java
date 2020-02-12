package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service("redundancyFilter")
public class RedundancyFilter implements CommonFilter {
    @Override
    public Blueprint filter(Blueprint blueprint) {
        ArrayList<Point> puntos = new ArrayList<Point>(blueprint.getPoints());

        int i = 0;
        while(i<puntos.size()){
            if (i==0){
                if(puntos.get(i).equals(puntos.get(i+1))){
                    puntos.remove(i+1);
                }
            }else if(i==puntos.size()-1){
                if (puntos.get(i).equals(puntos.get(i-1))){
                    puntos.remove(i-1);
                    i--;
                }
            }else{
                boolean flagAnterior=false;
                boolean flagDespues=false;
                if(puntos.get(i).equals(puntos.get(i-1))){
                    flagAnterior = true;
                }
                if(puntos.get(i).equals(puntos.get(i+1))){
                    flagDespues = true;
                }
                if(flagAnterior){
                    puntos.remove(i-1);

                }
            }
            i++;
        }
        Point[] temporal = new Point[puntos.size()];
        for (int j = 0;j<puntos.size();j++){
            temporal[j]=puntos.get(j);
        }

        return new Blueprint(blueprint.getAuthor(),blueprint.getName(),temporal);
    }
}
