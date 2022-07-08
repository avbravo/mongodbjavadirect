/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.ConsoleUtil;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Corregimiento;
import com.avbravo.mongodbatlasdriver.model.Persona;
import com.avbravo.mongodbatlasdriver.model.Profesion;
import java.util.List;
import java.util.function.Supplier;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class PersonaSupplier {
    // <editor-fold defaultstate="collapsed" desc="level">

    LookupSupplierLevel levelLocal = LookupSupplierLevel.THREE;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="graphics">

    /**
     * Persona{
     *
     * @Referenced Corregimiento{
     * @Referenced Provincia{
     * @Referenced Pais{
     * @Referenced Planeta{}
     * @Referenced Oceano{}
     * @Embedded Idioma idioma;
     * @Embedded List<Musica>; } } }
     * @Referenced Profesion{
     * @Referenced Grupopresion{ } }
     */
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Persona get(Supplier<? extends Persona> s, Document document)">
    public static Persona get(Supplier<? extends Persona> s, Document document) {
        Persona persona = s.get();
        try {
            ConsoleUtil.info(Test.nameOfClassAndMethod() + "Document.toJson()  " + document.toJson());

            persona.setIdpersona(String.valueOf(document.get("idpersona")));
            persona.setNombre(String.valueOf(document.get("nombre")));

            Boolean istListReferecendToCorregimiento = false;
            Boolean istListReferecendToProfesion = false;

            List<Document> documentCorregimientoList = (List<Document>) document.get("corregimiento");
            List<Document> documentProvinciaList = (List<Document>) document.get("provincia");
            List<Document> documentPaisList = (List<Document>) document.get("pais");
            List<Document> documentPlanetaList = (List<Document>) document.get("planeta");
            List<Document> documentOceanoList = (List<Document>) document.get("oceano");
            /**
             * persona-->prefesion-->grupoprofesion
             */
            List<Document> documentProfesionList = (List<Document>) document.get("profesion");
            List<Document> documentGrupoprofesionList = (List<Document>) document.get("grupoprofesion");
            Document docPais;
            if (!istListReferecendToCorregimiento) {
                persona.setCorregimiento(CorregimientoSupplier.get(Corregimiento::new, documentCorregimientoList, documentProvinciaList, documentPaisList, documentPlanetaList, documentOceanoList));
            } else {
                Test.warning(Test.nameOfClassAndMethod() + " No se permite @Referenced List<>");
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
//                List<Corregimiento> corregimientoList = new ArrayList<>();
//                if (documentCorregimientoList.isEmpty() || documentCorregimientoList.size() == 0) {
//                    Test.warning("No hay registros de pais");
//                } else {
//                    documentCorregimientoList.forEach(varDoc -> {
//                       paisList.add(CorregimientoSupplier.get(Corregimiento::new, varDoc));
//                    });
//                }
//               persona.setCorregimiento(corregimientoList);
            }
            
            
            
           if (!istListReferecendToProfesion) {
                persona.setProfesion(ProfesionSupplier.get(Profesion::new, documentProfesionList, documentGrupoprofesionList));
           } else {
//                Test.warning(Test.nameOfClassAndMethod() + " No se permite @Referenced List<>");
//                /**
//                 * En nivel 2 no se permiten @Referenced List<Nivel1>
//                 * de una entidad de nivel 1 ya que complica la evaluación
//                 *
//                 */
//
//                /**
//                 * Lista de Referenciados Recorre cada elemento y lo carga en un
//                 * List<Entidad>
//                 * Luego lo asigna al atributo de la entidad superior
//                 */
////                List<Corregimiento> corregimientoList = new ArrayList<>();
////                if (documentCorregimientoList.isEmpty() || documentCorregimientoList.size() == 0) {
////                    Test.warning("No hay registros de pais");
////                } else {
////                    documentCorregimientoList.forEach(varDoc -> {
////                       paisList.add(CorregimientoSupplier.get(Corregimiento::new, varDoc));
////                    });
////                }
////               persona.setCorregimiento(corregimientoList);
           }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return persona;

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Persona get(Supplier<? extends Persona> s, List<Document> documentPersonaList,List<Document> documentCorregimientoList,List<Document> documentProvinciaList, List<Document> documentPaisList, List<Document> documentPlanetaList,    List<Document> documentOceanoList)">

    public static Persona get(Supplier<? extends Persona> s, List<Document> documentPersonaList, List<Document> documentCorregimientoList, List<Document> documentProvinciaList, List<Document> documentPaisList, List<Document> documentPlanetaList, List<Document> documentOceanoList, List<Document> documentProfesionList, List<Document> documentGrupoprofesionList) {
        Persona persona = s.get();
        try {
            Document document = documentPersonaList.get(0);
            ConsoleUtil.info(Test.nameOfClassAndMethod() + "Document.toJson()  " + document.toJson());

            persona.setIdpersona(String.valueOf(document.get("idpersona")));
            persona.setNombre(String.valueOf(document.get("nombre")));

            Boolean istListReferecendToCorregimiento = false;
            Boolean istListReferecendToProfesion = false;

//            List<Document> documentCorregimientoList = (List<Document>) document.get("corregimiento");
//            List<Document> documentProvinciaList = (List<Document>) document.get("provincia");
//            List<Document> documentPaisList = (List<Document>) document.get("pais");
//            List<Document> documentPlanetaList = (List<Document>) document.get("planeta");
//            List<Document> documentOceanoList = (List<Document>) document.get("oceano");
            /**
             * persona-->prefesion-->grupoprofesion
             */
//            List<Document> documentProfesionList = (List<Document>) document.get("profesion");
//            List<Document> documentGrupoprofesionList = (List<Document>) document.get("grupoprofesion");
            Document docPais;
            if (!istListReferecendToCorregimiento) {
                persona.setCorregimiento(CorregimientoSupplier.get(Corregimiento::new, documentCorregimientoList, documentProvinciaList, documentPaisList, documentPlanetaList, documentOceanoList));
            } else {
                Test.warning(Test.nameOfClassAndMethod() + " No se permite @Referenced List<>");
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
//                List<Corregimiento> corregimientoList = new ArrayList<>();
//                if (documentCorregimientoList.isEmpty() || documentCorregimientoList.size() == 0) {
//                    Test.warning("No hay registros de pais");
//                } else {
//                    documentCorregimientoList.forEach(varDoc -> {
//                       paisList.add(CorregimientoSupplier.get(Corregimiento::new, varDoc));
//                    });
//                }
//               persona.setCorregimiento(corregimientoList);
            }
//            if (!istListReferecendToProfesion) {
//                persona.setProfesion(ProfesionSupplier.get(Profesion::new, documentProfesionList, documentGrupoprofesionList));
//            } else {
//                Test.warning(Test.nameOfClassAndMethod() + " No se permite @Referenced List<>");
//                /**
//                 * En nivel 2 no se permiten @Referenced List<Nivel1>
//                 * de una entidad de nivel 1 ya que complica la evaluación
//                 *
//                 */
//
//                /**
//                 * Lista de Referenciados Recorre cada elemento y lo carga en un
//                 * List<Entidad>
//                 * Luego lo asigna al atributo de la entidad superior
//                 */
////                List<Profesion> profesionList = new ArrayList<>();
////                if (documentProfesionList.isEmpty() || documentProfesionList.size() == 0) {
////                    Test.warning("No hay registros de pais");
////                } else {
////                    documentProfesionList.forEach(varDoc -> {
////                       profesionList.add(ProfesionSupplier.get(Profesion::new, varDoc));
////                    });
////                }
////               persona.setCorregimiento(corregimientoList);
//            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return persona;

    }
// </editor-fold>

}
