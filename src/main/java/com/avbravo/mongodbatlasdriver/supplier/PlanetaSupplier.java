/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Planeta;
import java.util.List;
import java.util.function.Supplier;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class PlanetaSupplier {
    // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.ZERO;
// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="graphics">
    /**
             * Planeta{      
             *        }
             */ 
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Planeta get(Supplier<? extends Planeta> s, Document document)">

    /**
     * Como es una clase que no tiene padres se puede implmentar JSON-B para
     * convertirlo directamente a Objeto.
     *
     * @param s
     * @param document
     * @return
     */
    public static Planeta get(Supplier<? extends Planeta> s, Document document) {
        Planeta planeta = s.get();
        try {
            /**
             * Entidad: Planeta
             * Planeta{
             *    // No tiene embedded ni @Referenced
             * }
             * 
             * Nivel de Trabajo : 0
             * 
             * Esquema de Niveles:
             * | Nivel 0|
             *   Planeta
             * 
             */ 
            
            Jsonb jsonb = JsonbBuilder.create();
            planeta = jsonb.fromJson(document.toJson(), Planeta.class);
        } catch (Exception e) {
           Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }
        return planeta;

    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Planeta get(Supplier<? extends Planeta> s,List<Document> documentList)">

    /**
     * Como es una clase que no tiene padres se puede implmentar JSON-B para
     * convertirlo directamente a Objeto.
     *Se invoca desde otro Supplier de nivel superior
     *
     *
     * @param s
     * @param documentList
     * @return
     */
    public static Planeta get(Supplier<? extends Planeta> s, List<Document> documentList) {
        Planeta planeta = s.get();
        try {
            Document document = documentList.get(0);
             /**
             * Entidad: Planeta
             * Planeta{
             *    // No tiene embedded ni @Referenced
             * }
             * 
             * Nivel de Trabajo : 0
             * 
             * Esquema de Niveles:
             * | Nivel 0|
             *   Planeta
             * 
             */ 
            Jsonb jsonb = JsonbBuilder.create();
            planeta = jsonb.fromJson(document.toJson(), Planeta.class);
        } catch (Exception e) {
          Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }
        return planeta;

    }
// </editor-fold>
}
