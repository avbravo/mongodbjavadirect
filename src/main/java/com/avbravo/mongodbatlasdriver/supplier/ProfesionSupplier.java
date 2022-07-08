/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.ConsoleUtil;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Grupoprofesion;
import com.avbravo.mongodbatlasdriver.model.Profesion;
import com.avbravo.mongodbatlasdriver.model.Provincia;
import java.util.List;
import java.util.function.Supplier;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class ProfesionSupplier {
    // <editor-fold defaultstate="collapsed" desc="level">

    LookupSupplierLevel levelLocal = LookupSupplierLevel.ONE;
// </editor-fold>
       // <editor-fold defaultstate="collapsed" desc="graphics">

    /**
     * Profesion{
     *
     * @Referenced Grupopresion{ } }
     */
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Profesion get(Supplier<? extends Profesion> s, Document document)">

    public static Profesion get(Supplier<? extends Profesion> s, Document document) {
        Profesion profesion = s.get();
        try {
            ConsoleUtil.info(Test.nameOfClassAndMethod() + "Document.toJson()  " + document.toJson());

            profesion.setIdprofesion(String.valueOf(document.get("idprofesion")));
            profesion.setProfesion(String.valueOf(document.get("profesion")));

            Boolean istListReferecendToGrupoprofesion = false;
            /**
             * Esto aplica para nivel 2 donde hay que conocer los padres que
             * tiene Se debe conocer de la entidad de siguente nivel Pais cuales
             * son sus referencias para pasarlos como List<Document>
             * Profesion{
             *
             * @Referenced Grupoprofesion{ } }
             *
             * Se puede observar que hay referencias de nivel 3: Nivel 1 -->
             * Nivel 0 Profesion -->@R Grupoprofesion
             *
             */

            List<Document> documentGrupoprofesionList = (List<Document>) document.get("grupoprofesion");

            Document docPais;
            if (!istListReferecendToGrupoprofesion) {
                profesion.setGrupoprofesion(GrupoprofesionSupplier.get(Grupoprofesion::new, documentGrupoprofesionList));
            } else {
                /**
                 * En nivel 2 no se permiten @Referenced List<Nivel1>
                 * de una entidad de nivel 1 ya que complica la evaluación
                 *
                 */

                /**
                 * Lista de Referenciados Recorre cada elemento y lo carga en un
                 * List<Entidad>
                 * Luego lo asigna al atributo de la entidad superior
                 */
//                List<Grupoprofesion> grupoprofesionList = new ArrayList<>();
//                if (documentGrupoprofesionList.isEmpty() || documentGrupoprofesionList.size() == 0) {
//                    Test.warning("No hay registros de grupoprofesion");
//                } else {
//                    documentGrupoprofesionList.forEach(varDoc -> {
//                       grupoprofesionList.add(GrupoprofesionSupplier.get(Grupoprofesion::new, varDoc));
//                    });
//                }
//                profesion.setGrupoprofesion(grupoprofesionList);
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return profesion;

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Profesion get(Supplier<? extends Profesion> s, Document document)">

    public static Profesion get(Supplier<? extends Profesion> s, List<Document> documentProfesionList, List<Document>  documentGrupoprofesionList) {
        Profesion profesion = s.get();
        try {
            Document document = documentProfesionList.get(0);
            ConsoleUtil.info(Test.nameOfClassAndMethod() + "Document.toJson()  " + document.toJson());

            profesion.setIdprofesion(String.valueOf(document.get("idprofesion")));
            profesion.setProfesion(String.valueOf(document.get("profesion")));

            Boolean istListReferecendToGrupoprofesion = false;
            /**
             * Esto aplica para nivel 2 donde hay que conocer los padres que
             * tiene Se debe conocer de la entidad de siguente nivel Pais cuales
             * son sus referencias para pasarlos como List<Document>
             * Profesion{
             *
             * @Referenced Grupoprofesion{ } }
             *
             * Se puede observar que hay referencias de nivel 3: Nivel 1 -->
             * Nivel 0 Profesion -->@R Grupoprofesion
             *
             */

//            List<Document> documentGrupoprofesionList = (List<Document>) document.get("grupoprofesion");

            Document docPais;
            if (!istListReferecendToGrupoprofesion) {
                profesion.setGrupoprofesion(GrupoprofesionSupplier.get(Grupoprofesion::new, documentGrupoprofesionList));
            } else {
                /**
                 * En nivel 2 no se permiten @Referenced List<Nivel1>
                 * de una entidad de nivel 1 ya que complica la evaluación
                 *
                 */

                /**
                 * Lista de Referenciados Recorre cada elemento y lo carga en un
                 * List<Entidad>
                 * Luego lo asigna al atributo de la entidad superior
                 */
//                List<Grupoprofesion> grupoprofesionList = new ArrayList<>();
//                if (documentGrupoprofesionList.isEmpty() || documentGrupoprofesionList.size() == 0) {
//                    Test.warning("No hay registros de grupoprofesion");
//                } else {
//                    documentGrupoprofesionList.forEach(varDoc -> {
//                       grupoprofesionList.add(GrupoprofesionSupplier.get(Grupoprofesion::new, varDoc));
//                    });
//                }
//                profesion.setGrupoprofesion(grupoprofesionList);
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return profesion;

    }
// </editor-fold>

}
