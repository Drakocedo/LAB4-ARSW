/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters.impl;

import java.util.HashSet;
import java.util.Set;

import edu.eci.arsw.blueprints.filters.filter;
import edu.eci.arsw.blueprints.model.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author david.caycedo
 */
@Component("subsampling")
public class Subsampling implements filter{

   

    @Override
    public Blueprint filtrate(Blueprint bp) {
       
        
            List<Point> lista = new ArrayList<>();
            for (int i=0;i<bp.getPoints().size();i++){
                if (i%2==0){
                    lista.add(bp.getPoints().get(i));
                }
                      
            }    
        bp.setPoints(lista);
        
        return bp;
    
    }  
}
