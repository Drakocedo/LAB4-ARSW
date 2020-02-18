/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 *
 * @author hcadavid
 */
@RestController

public class BlueprintAPIController {

    @Autowired
    @Qualifier("BlueprintsServices")
    BlueprintsServices bps;  
       
    @RequestMapping(value = "/blueprints", method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGETRecursosBlueprints() throws BlueprintNotFoundException {
    	try {
    		 Set<Blueprint> data = bps.getAllBlueprints();
    	     return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    	        
        } catch (Exception  ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }

    }    
    
    @RequestMapping(value = "/blueprints/{author}",method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGETRecursosBlueprintsAutor(@PathVariable("author") String author) throws BlueprintNotFoundException {
    	try {
    		Set<Blueprint> data = bps.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
        } catch (Exception  ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }    
    
	@RequestMapping(value = "/blueprints/{author}/{bpname}",method = RequestMethod.GET)    
    public ResponseEntity<?> manejadorGETRecursosBlueprintsAutorName(@PathVariable("author") String author,@PathVariable("bpname") String name) throws BlueprintNotFoundException {
        try {
            Blueprint data = bps.getBlueprint(author, name);
            return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
        } catch (Exception  ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(method = RequestMethod.POST)	
    public ResponseEntity<?> manejadorPOSTRecursosBlueprints(@RequestBody Blueprint blueprint){
        try {
            bps.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);            
        }
    }
    /* curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://localhost:8080/blueprints -d "{"""author""":"""Caro""","""points""":[{"""x""":140,"""y""":140},{"""x""":115,"""y""":115}],"""name""":"""Ping"""}" */

    @RequestMapping(method = RequestMethod.PUT, path = "/blueprints/{author}/{bpname}")	
    public ResponseEntity<?> manejadorPUTRecursoActualizarPlano(@RequestBody Blueprint blueprint,@PathVariable("author") String author,@PathVariable("bpname") String plano){    
        try {
            bps.upDateBlueprint(blueprint, author, plano);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);            
        }
    }
    /* curl -i -X PUT -HContent-Type:application/json -HAccept:application/json http://localhost:8080/blueprints/Drako/doggy -d "{"""author""":"""Caro""","""points""":[{"""x""":140,"""y""":140},{"""x""":115,"""y""":115}],"""name""":"""Ping"""}" */
    
}