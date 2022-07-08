/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier.lookup;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.ConsoleUtil;
import com.avbravo.mongodbatlasdriver.model.Planeta;
import static com.avbravo.mongodbatlasdriver.supplier.lookup.CorregimientoLookupSupplier.levelLocal;
import static com.avbravo.mongodbatlasdriver.supplier.lookup.ProvinciaLookupSupplier.levelLocal;
import com.avbravo.mongodbatlasdriver.supplier.lookup.interfaces.LookupSupplier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.bson.conversions.Bson;

/**
 *
 * @author avbravo
 */
public class PlanetaLookupSupplier {
    // <editor-fold defaultstate="collapsed" desc="level">

    static LookupSupplierLevel levelLocal = LookupSupplierLevel.ZERO;
// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="graphics">
    /**
             * Planeta{      
             *        }
             */ 
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<Bson> get(Supplier<? extends Planeta> s, Document document, String parent, LookupSupplierLevel level,Boolean... applyFromThisLevel)">

    /**
     * Como es una clase que no tiene padres se puede implmentar JSON-B para
     * convertirlo directamente a Objeto.
     *
     *
     * @param s
     * @param document
     * @param applyFromNextLevell : true Aplica al siguiente nivel y a todos los
     * superiores : false aplica al superior del nivel superior
     * @return
     */
    public static List<Bson> get(Supplier<? extends Planeta> s, Referenced referenced, String parent, LookupSupplierLevel level, Boolean... applyFromThisLevel) {
        List<Bson> list = new ArrayList<>();
        Bson pipeline;
        try {
            ConsoleUtil.info(Test.nameOfClassAndMethod() + "parent[" + parent + "] LookupSupplierLevel level = [0" + level + "] levelLocal[" + levelLocal + "]");
            Boolean apply = true;
            if (applyFromThisLevel.length != 0) {
                apply = applyFromThisLevel[0];

            }

            list.add(LookupSupplier.get(referenced, parent, level, apply));
            /**
             *Planeta{
             * }
             */
            /**
             *
             * Cada supplier debe verificar las clases @Referenciadas e invocar
             * la superior
             *
             * Esta aplica en false del lookup ya que genera a partir del
             * siguiente Aplica para el siguiente
             */
            if (!apply) {
                apply = Boolean.TRUE;
            }
            /**
             * Valida el nivel antes de invocar los referenciados
             */
            if (level == LookupSupplierLevel.ZERO || level == LookupSupplierLevel.ONE || level == LookupSupplierLevel.TWO) {
                /**
                 * Niveles 0, 1, 2 no se produce cambio
                 */
            } else {
                ConsoleUtil.normal("Test.diference(level, levelLocal) " + Test.diference(level, levelLocal));
                if (Test.diference(level, levelLocal) >= 2) {
                    level = Test.decrement(level);
                    parent = referenced.from();
                }

            }
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return list;

    }
// </editor-fold>
}
