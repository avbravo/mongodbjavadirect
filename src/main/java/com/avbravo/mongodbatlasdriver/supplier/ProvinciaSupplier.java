/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.ConsoleUtil;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Provincia;
import com.avbravo.mongodbatlasdriver.model.Pais;
import java.util.List;
import java.util.function.Supplier;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class ProvinciaSupplier {
    // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.TWO;
// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="graphics">

    /**
     *
     * Provincia{
     *
     * @Referenced Pais{
     * @Referenced Planeta
     * @Referenced Oceano
     * @Embedded Idioma idioma;
     * @Embedded List<Musica>; } }
     *
     */
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Provincia get(Supplier<? extends Provincia> s, Document document)">
    public static Provincia get(Supplier<? extends Provincia> s, Document document) {
        Provincia provincia = s.get();
        try {
            

            provincia.setIdprovincia(String.valueOf(document.get("idprovincia")));
            provincia.setProvincia(String.valueOf(document.get("provincia")));

            Boolean istListReferecendToPais = false;
            /**
             * Esto aplica para nivel 2 donde hay que conocer los padres que
             * tiene Se debe conocer de la entidad de siguente nivel Pais cuales
             * son sus referencias para pasarlos como List<Document>
             * Provincia{
             *
             *      @Referenced Pais {
             *                  @Referenced Planeta planeta;
             *                  @Referenced List<Oceano> oceano;
             *                  @Embedded Idioma idioma;
             **                 @Embedded List<Musica> musica; }
             * }
             *
             * Se puede observar que hay referencias de nivel 2: Nivel 2 Nivel 1
             * Nivel 0 Provincia --> Pais -->@R Planeta Provincia --> Pais -->@R
             * (List<Oceano>
             * Provincia --> Pais -->@E (Idioma) Provincia --> Pais -->@E
             * (List<Music>
             *
             */

            List<Document> documentPaisList = (List<Document>) document.get("pais");
            List<Document> documentPlanetaList = (List<Document>) document.get("planeta");
            List<Document> documentOceanoList = (List<Document>) document.get("oceano");

            Document docPais;
            if (!istListReferecendToPais) {
                provincia.setPais(PaisSupplier.get(Pais::new, documentPaisList, documentPlanetaList, documentOceanoList));
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
//                List<Pais> paisList = new ArrayList<>();
//                if (documentPaisList.isEmpty() || documentPaisList.size() == 0) {
//                    Test.warning("No hay registros de pais");
//                } else {
//                    documentPaisList.forEach(varDoc -> {
//                       paisList.add(PaisSupplier.get(Pais::new, varDoc));
//                    });
//                }
//                provincia.setPais(paisList);
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return provincia;

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Provincia get(Supplier<? extends Provincia> s, List<Document> documentProvinciaList, List<Document> documentPaisList, List<Document> documentPlanetaList, List<Document> documentOceanoList)">
    public static Provincia get(Supplier<? extends Provincia> s, List<Document> documentProvinciaList, List<Document> documentPaisList, List<Document> documentPlanetaList, List<Document> documentOceanoList) {
        Provincia provincia = s.get();
        try {
            Document document =documentProvinciaList.get(0);

            provincia.setIdprovincia(String.valueOf(document.get("idprovincia")));
            provincia.setProvincia(String.valueOf(document.get("provincia")));

            Boolean istListReferecendToPais = false;
            /**
             * Esto aplica para nivel 2 donde hay que conocer los padres que
             * tiene Se debe conocer de la entidad de siguente nivel Pais cuales
             * son sus referencias para pasarlos como List<Document>
            * Provincia{
             *
             *      @Referenced Pais {
             *                  @Referenced Planeta planeta;
             *                  @Referenced List<Oceano> oceano;
             *                  @Embedded Idioma idioma;
             **                 @Embedded List<Musica> musica; }
             * }
             * Se puede observar que hay referencias de nivel 2: Nivel 2 Nivel 1
             * Nivel 0 Provincia --> Pais -->@R Planeta Provincia --> Pais -->@R
             * (List<Oceano>
             * Provincia --> Pais -->@E (Idioma) Provincia --> Pais -->@E
             * (List<Music>
             *
             */

//            List<Document> documentPaisList = (List<Document>) document.get("pais");
//            List<Document> documentPlanetaList = (List<Document>) document.get("planeta");
//            List<Document> docOceanoList = (List<Document>) document.get("oceano");

            Document docPais;
            if (!istListReferecendToPais) {
                provincia.setPais(PaisSupplier.get(Pais::new, documentPaisList, documentPlanetaList, documentOceanoList));
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
//                List<Pais> paisList = new ArrayList<>();
//                if (documentPaisList.isEmpty() || documentPaisList.size() == 0) {
//                    Test.warning("No hay registros de pais");
//                } else {
//                    documentPaisList.forEach(varDoc -> {
//                       paisList.add(PaisSupplier.get(Pais::new, varDoc));
//                    });
//                }
//                provincia.setPais(paisList);
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return provincia;

    }
// </editor-fold>

}
