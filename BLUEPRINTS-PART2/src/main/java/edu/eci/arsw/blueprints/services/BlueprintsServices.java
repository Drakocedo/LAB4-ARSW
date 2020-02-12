/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filters.filtrar;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;


import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service("BlueprintsServices")
public class BlueprintsServices {
   
    @Autowired
    @Qualifier("InMemoryBlueprintPersistence")
    BlueprintsPersistence bpp;
    
    @Autowired
    @Qualifier("redundancy")
    filtrar bpf;
    
    
    public void addNewBlueprint(Blueprint bp){
        try {
            bpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintsServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException{
        Set<Blueprint> nueva = new HashSet<>();
        for (Blueprint bp:bpp.getAllBlueprints()){
            nueva.add(bpf.filtrate(bp));
        }

       return nueva;
       
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException{
       return bpp.getBlueprint(author, name);
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        Set<Blueprint> nueva = new HashSet<>();
        for (Blueprint bp:bpp.getBlueprintsByAuthor(author)){
            bpf.filtrate(bp);
            nueva.add(bp);
        }

       return nueva;
           
    }    
    
}
