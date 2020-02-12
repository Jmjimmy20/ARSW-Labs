/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
@Service("bluePrintInMemory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        Point[] pts0 = new Point[]{new Point(120, 120),new Point(95, 95)};
        Blueprint bp0=new Blueprint("OrlandoGelves", "PlanosCasa",pts0);

        Point[] pts2 = new Point[]{new Point(130, 130),new Point(105, 105)};
        Blueprint bp1=new Blueprint("JimmyMoya", "PlanosEdificio",pts2);
        Blueprint bp2=new Blueprint("JimmyMoya", "PlanosParque ",pts2);
        blueprints.put(new Tuple<>(bp0.getAuthor(),bp0.getName()),bp0);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()),bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()),bp2);
    }
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Map<Tuple<String, String>, Blueprint> getAllBluePrints() {
        return blueprints;
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bpByAuthor = new HashSet<>();
        for (Tuple<String, String> x : blueprints.keySet()) {
            if (author.equals(x.getElem1())) {
                bpByAuthor.add(blueprints.get(x));
            }
        }
        return bpByAuthor;        
        
    }

}
