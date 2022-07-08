/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Oceano;
import java.util.List;
import java.util.function.Supplier;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class OceanoSupplier {
        // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.ZERO;
// </editor-fold>
           // <editor-fold defaultstate="collapsed" desc="graphics">

    /**
     * Ocenao{ }
     */
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Oceano get(Supplier<? extends Oceano> s, Document document)">

    /**
     * Como es una clase que no tiene padres se puede implmentar JSON-B para
     * convertirlo directamente a Objeto.
     *
     * @param s
     * @param document
     * @return
     */

    public static Oceano get(Supplier<? extends Oceano> s, Document document) {
        Oceano oceano = s.get();
        try { 
             /**
             * Entidad: Oceano
             * Oceano{
             *    // No tiene embedded ni @Referenced
             * }
             *
             * Nivel de Trabajo : 0
             * 
             * Esquema de Niveles:
             * | Nivel 0|
             *   Oceano
             * 
             */ 
            Jsonb jsonb = JsonbBuilder.create();
            oceano = jsonb.fromJson(document.toJson(), Oceano.class);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
            
        }
        return oceano;

    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Oceano get(Supplier<? extends Oceano> s, List<Document> documentList)">

    /**
     * Como es una clase que no tiene padres se puede implmentar JSON-B para
     * convertirlo directamente a Objeto.
     *  Se invoca desde otro Supplier de nivel superior
     */
    /**
     * 
     * @param s
     * @param documentList
     * @return 
     */

    public static Oceano get(Supplier<? extends Oceano> s, List<Document> documentList) {
        Oceano oceano = s.get();
        try {
            Document document = documentList.get(0);
             /**
             * Entidad: Oceano
             * Oceano{
             *    // No tiene embedded ni @Referenced
             * }
             *
             * Nivel de Trabajo : 0
             * 
             * Esquema de Niveles:
             * | Nivel 0|
             *   Oceano
             * 
             */ 
            Jsonb jsonb = JsonbBuilder.create();
            oceano = jsonb.fromJson(document.toJson(), Oceano.class);
        } catch (Exception e) {
          Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }
        return oceano;

    }
// </editor-fold>
}
