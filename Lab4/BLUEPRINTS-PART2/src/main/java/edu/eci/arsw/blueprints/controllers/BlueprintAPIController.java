/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value="/blueprints")
public class BlueprintAPIController {

    @Autowired
    @Qualifier("BluePrintService")
    BlueprintsServices bps;

    @RequestMapping(method = RequestMethod.GET,value="/Allblueprints")
    public ResponseEntity<?> manejadorGetAllBluePrints(){
        try {
            //obtener datos que se enviarán a través del API
            Set<Blueprint> todosLosBluePrints = bps.getAllBlueprints();
            return new ResponseEntity<>(todosLosBluePrints, HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("El recurso no se ha encontrado",HttpStatus.NOT_FOUND);
        }
    }



    @RequestMapping(method = RequestMethod.GET,value="/Onlyblueprints/{author}/{name}")
    public ResponseEntity<?> manejadorGetBluepirnts(@PathVariable String author, @PathVariable String name){
        try {
            //obtener datos que se enviarán a través del API
            Blueprint todosLosBluePrints = bps.getBlueprint(author,name);
            return new ResponseEntity<>(todosLosBluePrints, HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("El recurso no se ha encontrado",HttpStatus.NOT_FOUND);
        }
    }
    
}

