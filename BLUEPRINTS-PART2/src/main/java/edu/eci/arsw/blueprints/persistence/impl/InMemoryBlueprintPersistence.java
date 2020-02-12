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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */

@Service("InMemoryBlueprintPersistence")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    public InMemoryBlueprintPersistence() {
        // load stub data
        Point[] pts = new Point[] { new Point(140, 140), new Point(115, 115),new Point(115, 115) };
        Blueprint bp1 = new Blueprint("David", "Pro", pts);
        Blueprint bp2 = new Blueprint("David", "Red", pts);
        Blueprint bp3 = new Blueprint("Jonatan", "Blue", pts);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
         
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
    	//System.out.println(blueprints.get(new Tuple<>(author, bprintname)));
    	Blueprint result=blueprints.get(new Tuple<>(author, bprintname));
    	if (result==(null)) {
    		throw new BlueprintNotFoundException("No existen blueprints con el autor: " + author +" y de nombre: "+bprintname);
    	}
    	else {
    		return result;
    	}
        
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> lista = new HashSet<>();
        blueprints.forEach((key, value) -> {
            if (key.o1.equals(author)){
                lista.add(value);
            }
        });
        if (lista.isEmpty()) {
            throw new BlueprintNotFoundException("No existen blueprints con el autor seleccionado: "+author);
        } else {
            return lista;
        }
    }
    
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException{
        Set<Blueprint> lista = new HashSet<>();
        blueprints.forEach((key, value) -> {
                lista.add(value);
        });
  
        if (lista.isEmpty()) {
            throw new BlueprintNotFoundException("No hay blueprints almacenados.");
        } else {
            return lista;
        }
        
    }
}
