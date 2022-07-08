/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier.lookup.interfaces;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import static com.mongodb.client.model.Aggregates.lookup;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author avbravo
 */
public class LookupSupplier {

    // <editor-fold defaultstate="collapsed" desc="Bson get(Referenced referenced,String parent,Boolean... applyFromThisLevel)">
   /**
    * 
    * @param referenced
    * @param parent
    * @param applyFromThisLevel : true aplica desde este nivel en adelante
    * @return                   : false aplica desde el siguiente nivel en adelante.
    */
    public static Bson get(Referenced referenced, String parent, LookupSupplierLevel level,Boolean... applyFromThisLevel) {
        Bson b = new Document();

        try {
            String localField = referenced.localField();
            Boolean apply = true;
            if (applyFromThisLevel.length != 0) {
                apply = applyFromThisLevel[0];

            }

            if (apply) {
                if (!parent.equals("")) {
                    parent += ".";
                    localField = parent + referenced.localField();
                }

            }

            return lookup(referenced.from(), localField, referenced.foreignField(), referenced.as());
        } catch (Exception e) {
           Test.error("LookupSupplier.get() "+e.getLocalizedMessage());
        }
        return lookup(referenced.from(), referenced.localField(), referenced.foreignField(), referenced.as());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String localFieldRecursive(Referenced referenced, String parent)">
}
