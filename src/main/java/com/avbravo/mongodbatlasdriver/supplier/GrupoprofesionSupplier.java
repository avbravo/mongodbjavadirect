/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Grupoprofesion;
import java.util.List;
import java.util.function.Supplier;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class GrupoprofesionSupplier {
      // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.ZERO;
// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="graphics">

    /**
     * Grupopresion{ }
     */
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Grupoprofesion get(Supplier<? extends Grupoprofesion> s, Document document)">

    /**
     * Como es una clase que no tiene padres se puede implmentar JSON-B para
     * convertirlo directamente a Objeto.
     *
     * @param s
     * @param document
     * @return
     */
    public static Grupoprofesion get(Supplier<? extends Grupoprofesion> s, Document document) {
        Grupoprofesion grupoprofesion = s.get();
        try {
            /**
             * Entidad: Grupoprofesion
             * Grupoprofesion{
             *    // No tiene embedded ni @Referenced
             * }
             * 
             * Nivel de Trabajo : 0
             * 
             * Esquema de Niveles:
             * | Nivel 0|
             *   Grupoprofesion
             * 
             */ 
            
            Jsonb jsonb = JsonbBuilder.create();
            grupoprofesion = jsonb.fromJson(document.toJson(), Grupoprofesion.class);
        } catch (Exception e) {
           Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }
        return grupoprofesion;

    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Grupoprofesion get(Supplier<? extends Grupoprofesion> s,List<Document> documentList)">

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
    public static Grupoprofesion get(Supplier<? extends Grupoprofesion> s, List<Document> documentList) {
        Grupoprofesion grupoprofesion = s.get();
        try {
            Document document = documentList.get(0);
             /**
             * Entidad: Grupoprofesion
             * Grupoprofesion{
             *    // No tiene embedded ni @Referenced
             * }
             * 
             * Nivel de Trabajo : 0
             * 
             * Esquema de Niveles:
             * | Nivel 0|
             *   Grupoprofesion
             * 
             */ 
            Jsonb jsonb = JsonbBuilder.create();
            grupoprofesion = jsonb.fromJson(document.toJson(), Grupoprofesion.class);
        } catch (Exception e) {
          Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }
        return grupoprofesion;

    }
// </editor-fold>
}
