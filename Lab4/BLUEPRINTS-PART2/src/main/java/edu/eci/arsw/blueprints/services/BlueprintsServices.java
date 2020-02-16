/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.controllers.ResourceNotFoundException;
import edu.eci.arsw.blueprints.filters.CommonFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import edu.eci.arsw.blueprints.persistence.impl.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service("BluePrintService")
public class BlueprintsServices {

    @Autowired
    @Qualifier("bluePrintInMemory")
    BlueprintsPersistence bpp;

    @Autowired
    @Qualifier("redundancyFilter")
    //@Qualifier("subsamplingFilter")
    CommonFilter filter;

    public Blueprint filter(Blueprint bp){
        return filter.filter(bp);
    }

    public void addNewBlueprint(Blueprint bp){
        try {
            bpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            e.printStackTrace();
        }
    }
    
    public Set<Blueprint> getAllBlueprints() throws ResourceNotFoundException {
        Set<Blueprint> temporal = new HashSet<Blueprint>();
        for(Blueprint i:bpp.getAllBluePrints().values()){
            temporal.add(i);
        }
        if(temporal.size()==0){
            throw new ResourceNotFoundException();
        }
        return temporal;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws ResourceNotFoundException{
        Blueprint temporal = null;
        try {
            temporal = bpp.getBlueprint(author,name);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        if (temporal==null){
            throw new ResourceNotFoundException();

        }
        return temporal;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws ResourceNotFoundException{
        Map<Tuple<String,String>,Blueprint> temporal = bpp.getAllBluePrints();
        Set<Blueprint> conjuntoBluePrints = new HashSet<Blueprint>();

        for(Tuple<String,String> i: temporal.keySet()){
            if (i.getElem1().equals(author)){
                conjuntoBluePrints.add(temporal.get(i));
            }
        }
        if(conjuntoBluePrints.size()==0){
            throw new ResourceNotFoundException();
        }
        return conjuntoBluePrints;
    }
    
}
