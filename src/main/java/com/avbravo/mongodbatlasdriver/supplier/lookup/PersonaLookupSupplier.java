/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier.lookup;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.ConsoleUtil;
import com.avbravo.mongodbatlasdriver.model.Corregimiento;
import com.avbravo.mongodbatlasdriver.model.Persona;
import com.avbravo.mongodbatlasdriver.supplier.lookup.interfaces.LookupSupplier;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.bson.conversions.Bson;

/**
 *
 * @author avbravo
 */
public class PersonaLookupSupplier {
    // <editor-fold defaultstate="collapsed" desc="level">

    static LookupSupplierLevel levelLocal = LookupSupplierLevel.FOUR;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="graphics">

    /**
     * Persona{
     *
     * @Referenced Corregimiento{
     * @Referenced Provincia{
     * @Referenced Pais{
     * @Referenced Planeta
     * @Referenced Oceano
     * @Embedded Idioma idioma;
     * @Embedded List<Musica>; } } }
     * @Referenced Profesion{
     * @Referenced Grupopresion{ } }
     */
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<Bson> get(Supplier<? extends Planeta> s, Document document, String parent, LookupSupplierLevel level,Boolean applyFromNextLevel)">
    /**
     * Como es una clase que no tiene padres se puede implmentar JSON-B para
     * convertirlo directamente a Objeto.
     *
     * @param s
     * @param document
     * @param applyFromNextLevell : true Aplica al siguiente nivel y a todos los
     * superiores : false aplica al superior del nivel superior
     * @return
     */
    public static List<Bson> get(Supplier<? extends Persona> s, Referenced referenced, String parent, LookupSupplierLevel level, Boolean... applyFromThisLevel) {
        List<Bson> list = new ArrayList<>();
        Bson pipeline;
        try {
            ConsoleUtil.info(Test.nameOfClassAndMethod() + "parent[" + parent + "] LookupSupplierLevel level = [0" + level + "] levelLocal[" + levelLocal + "]");
            Boolean apply = true;
            if (applyFromThisLevel.length != 0) {
                apply = applyFromThisLevel[0];

            }

            list.add(LookupSupplier.get(referenced, parent, level, apply));

            /*
             *
             * Cada supplier debe verificar las clases @Referenciadas e invocar
             * la superior
             *
             * Esta aplica en false del lookup ya que genera a partir del
             * siguiente Aplica para el siguiente
             */
            Referenced corregimientoReferenced = new Referenced() {
                @Override
                public String from() {
                    return "corregimiento";
                }

                @Override
                public String localField() {
                    return "corregimiento.idcorregimiento";
                }

                @Override
                public String foreignField() {
                    return "idcorregimiento";
                }

                @Override
                public String as() {
                    return "corregimiento";
                }

                @Override
                public boolean lazy() {
                    return false;
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            };

            Referenced profesionReferenced = new Referenced() {
                @Override
                public String from() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public String localField() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public String foreignField() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public String as() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public boolean lazy() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

            };
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
                if (Test.diference(level, levelLocal) >= 2) {
                    ConsoleUtil.info("cambiando level and parent");
                    level = Test.decrement(level);
                    parent = referenced.from();
                }

            }
            List<Bson> pipelineCorregimiento = CorregimientoLookupSupplier.get(Corregimiento::new, corregimientoReferenced, parent, level, apply);
            if (pipelineCorregimiento.isEmpty() || pipelineCorregimiento.size() == 0) {

            } else {
                pipelineCorregimiento.forEach(b -> {
                    list.add(b);
                });
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return list;

    }
// </editor-fold>

}
