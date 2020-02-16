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
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
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

    @RequestMapping(method = RequestMethod.GET,value="")
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

    @RequestMapping(method = RequestMethod.GET,value="/{author}")
    public ResponseEntity<?> manejadorGetBluepirntsByAuthor(@PathVariable String author){
        try {
            //obtener datos que se enviarán a través del API
            Set<Blueprint> todosLosBluePrints = bps.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(todosLosBluePrints, HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("El recurso no se ha encontrado",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET,value="/{author}/{name}")
    public ResponseEntity<?> manejadorGetBluepirntsByAuthorAndName(@PathVariable String author, @PathVariable String name){
        try {
            //obtener datos que se enviarán a través del API
            Blueprint todosLosBluePrints = bps.getBlueprint(author,name);
            return new ResponseEntity<>(todosLosBluePrints, HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("El recurso no se ha encontrado",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="/crearBlueprint")
    @ResponseBody
    public ResponseEntity<?> insertarUnBlueprint(@RequestBody Blueprint blueprint){
        try {
            bps.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(Exception.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al crear un plano",HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(method = RequestMethod.PUT,value="/{author}/{name}")
    @ResponseBody
    public ResponseEntity<?> editarUnBlueprint(@RequestBody Blueprint bp, @PathVariable String author, @PathVariable String name){
        try {
            Blueprint editar = bps.getBlueprint(author,name);
            editar.setPoints(bp.getPoints());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(Exception.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al cambiar un plano",HttpStatus.FORBIDDEN);
        }

    }
    
}

