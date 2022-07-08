/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.ConsoleUtil;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Corregimiento;
import com.avbravo.mongodbatlasdriver.model.Provincia;
import java.util.List;
import java.util.function.Supplier;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class CorregimientoSupplier {
  // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.THREE;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="grephics">
    /**
     *
     * Corregimiento{
     *
     * @Referenced Provincia{
     * @Referenced Pais{
     * @Referenced Planeta
     * @Referenced Oceano
     * @Embedded Idioma idioma;
     * @Embedded List<Musica>; } } }
     *
     *
     */
    // <editor-fold defaultstate="collapsed" desc="Corregimiento get(Supplier<? extends Corregimiento> s, Document document)">
    public static Corregimiento get(Supplier<? extends Corregimiento> s, Document document) {
        Corregimiento corregimiento = s.get();
        try {
ConsoleUtil.info(Test.nameOfClassAndMethod() + "Document.toJson()  "+document.toJson());

           corregimiento.setIdcorregimiento(String.valueOf(document.get("idcorregimiento")));
          corregimiento.setCorregimiento(String.valueOf(document.get("corregimiento")));

          Boolean istListReferecendToProvincia = false;
            /**
             * Esto aplica para nivel 2 donde hay que conocer los padres que
             * tiene Se debe conocer de la entidad de siguente nivel Pais cuales
             * son sus referencias para pasarlos como List<Document>
             * Corregimiento{
             *    @Referenced Provincia{
             *                @Referenced Pais {
             *                            @Referenced Planeta planeta;
             *                            @Referenced List<Oceano> oceano;
             *                            @Embedded Idioma idioma;
             *                            @Embedded List<Musica> musica; 
             *                        }            
             *             }         
             * }
             * 
             * Se puede observar que hay referencias de nivel 3:
             * Nivel3              Nivel 2        Nivel 1    Nivel 0
             * Corregimiento --> @R Provincia--> @Pais   -->@R Planeta
             * Corregimiento --> @R Provincia--> @Pais   -->@R (List<Oceano>
             * Corregimiento --> @R Provincia--> @Pais   -->@E (Idioma)
             * Corregimiento --> @R Provincia--> @Pais   -->@E (List<Music>
             *
             */
                     

            List<Document> documentProvinciaList = (List<Document>) document.get("provincia");
            List<Document> documentPaisList = (List<Document>) document.get("pais");
            List<Document> documentPlanetaList = (List<Document>) document.get("planeta");
            List<Document> documentOceanoList = (List<Document>) document.get("oceano");

            Document docPais;
            if (! istListReferecendToProvincia ) {
             corregimiento.setProvincia(ProvinciaSupplier.get(Provincia::new, documentProvinciaList, documentPaisList, documentPlanetaList, documentOceanoList));
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
//                List<Provincia> provinciaList = new ArrayList<>();
//                if (documentProvinciaList.isEmpty() || documentProvinciaList.size() == 0) {
//                    Test.warning("No hay registros de pais");
//                } else {
//                    documentProvinciaList.forEach(varDoc -> {
//                      provinciaList.add(ProvinciaSupplier.get(Provincia::new, varDoc));
//                    });
//                }
//                corregimiento.setProvincia(provinciasList);
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return corregimiento ;

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Corregimiento get(Supplier<? extends Corregimiento> s, Document document)">
    public static Corregimiento get(Supplier<? extends Corregimiento> s, List<Document> documentCorregimientoList, List<Document> documentProvinciaList,
            List<Document> documentPaisList,List<Document> documentPlanetaList ,List<Document> documentOceanoList ) {
        Corregimiento corregimiento = s.get();
        try {

Document document = documentCorregimientoList.get(0);
ConsoleUtil.info(Test.nameOfClassAndMethod() + "Document.toJson()  "+document.toJson());
           corregimiento.setIdcorregimiento(String.valueOf(document.get("idcorregimiento")));
          corregimiento.setCorregimiento(String.valueOf(document.get("corregimiento")));

          Boolean istListReferecendToProvincia = false;
            /**
             * Esto aplica para nivel 2 donde hay que conocer los padres que
             * tiene Se debe conocer de la entidad de siguente nivel Pais cuales
             * son sus referencias para pasarlos como List<Document>
             * Corregimiento{
             *    @Referenced Provincia{
             *                @Referenced Pais {
             *                            @Referenced Planeta planeta;
             *                            @Referenced List<Oceano> oceano;
             *                            @Embedded Idioma idioma;
             *                           @Embedded List<Musica> musica; 
             *                        }            
             *             }         
             * }
             * 
             * Se puede observar que hay referencias de nivel 3:
             * Nivel3              Nivel 2        Nivel 1    Nivel 0
             * Corregimiento --> @R Provincia--> @Pais   -->@R Planeta
             * Corregimiento --> @R Provincia--> @Pais   -->@R (List<Oceano>
             * Corregimiento --> @R Provincia--> @Pais   -->@E (Idioma)
             * Corregimiento --> @R Provincia--> @Pais   -->@E (List<Music>
             *
             */
                     
//
//            List<Document> documentProvinciaList = (List<Document>) document.get("provincia");
//            List<Document> documentPaisList = (List<Document>) document.get("pais");
//            List<Document> documentPlanetaList = (List<Document>) document.get("planeta");
//            List<Document> documentOceanoList = (List<Document>) document.get("oceano");

            Document docPais;
            if (! istListReferecendToProvincia ) {
             corregimiento.setProvincia(ProvinciaSupplier.get(Provincia::new, documentProvinciaList, documentPaisList, documentPlanetaList, documentOceanoList));
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
//                List<Provincia> provinciaList = new ArrayList<>();
//                if (documentProvinciaList.isEmpty() || documentProvinciaList.size() == 0) {
//                    Test.warning("No hay registros de pais");
//                } else {
//                    documentProvinciaList.forEach(varDoc -> {
//                      provinciaList.add(ProvinciaSupplier.get(Provincia::new, varDoc));
//                    });
//                }
//                corregimiento.setProvincia(provinciasList);

            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return corregimiento ;

    }
// </editor-fold>

}
