/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Idioma;
import com.avbravo.mongodbatlasdriver.model.Musica;
import com.avbravo.mongodbatlasdriver.model.Oceano;
import com.avbravo.mongodbatlasdriver.model.Pais;
import com.avbravo.mongodbatlasdriver.model.Planeta;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class PaisSupplier {
    // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.ONE;
// </editor-fold>
         // <editor-fold defaultstate="collapsed" desc="graphics">

    /**
     * Pais{
     *
     * @Referenced Planeta
     * @Referenced Oceano
     * @Embedded Idioma idioma;
     * @Embedded List<Musica>; }
     *
     */
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Pais get(Supplier<? extends Pais> s, Document document)">
    public static Pais get(Supplier<? extends Pais> s, Document document) {
        Pais pais = s.get();
        try {

            /**
             * Entidad:
             * Pais{
             *
             *     @Embedded Idioma idioma;
             *     @Embedded List<Musica>;
             *     @Referenced Planeta planeta;
             *     @Referenced List<Oceano>; 
             * }
             * 
             * Nivel de Trabajo : 1
             * 
             * Esquema de Niveles:
             * |Nivel 1  |  Nivel 0
             * Pais    -->  @E(Idioma)
             * Pais    -->  @E(List<Musica>
             * Pais    -->  @R(Planeta)
             * Pais    -->  @R(List<Planeta>
             * 
             */
            pais.setIdpais(String.valueOf(document.get("idpais")));
            pais.setPais(String.valueOf(document.get("pais")));

            /**
             * ---------------------------------------------
             *
             * @Embedded simple ----------------------------------------------
             */
            Document doc = (Document) document.get("idioma");
            Jsonb jsonb = JsonbBuilder.create();
            Idioma idioma = jsonb.fromJson(doc.toJson(), Idioma.class);
            pais.setIdioma(idioma);

            /**
             * --------------------------------------------------
             *
             * @Embedded List<Musica>
             * Debe utilizar una lista temporal para almacenar los valores *
             * --------------------------------------------------
             */
            List<Musica> musicaList = new ArrayList<>();
            List<Document> musicDoc = (List) document.get("musica");
            for (Document docm : musicDoc) {
                Musica musica = jsonb.fromJson(docm.toJson(), Musica.class);
                musicaList.add(musica);
            }
            pais.setMusica(musicaList);

            /**
             * @Referenced Nota: Las referencias generadas mediante lookup nos
             * devuelven un List<>
             * Por lo tanto debemos verificar si es una entidad simple o un
             * @Referenced List<Entidad>
             */
            /*
            -------------------------------------------------------
              /**
             * --------------------------------------------------
             * @Referenced Planeta planeta;
             * Es una referencia simple no usa List
             * --------------------------------------------------
             */
            Boolean istListReferecendToPlaneta = false;
            List<Document> docPlanetaList = (List<Document>) document.get("planeta");
            Document docPlaneta;
            if (!istListReferecendToPlaneta) {
                if (docPlanetaList.isEmpty() || docPlanetaList.size() == 0) {
                } else {
                    docPlaneta = docPlanetaList.get(0);
                    pais.setPlaneta(PlanetaSupplier.get(Planeta::new, docPlaneta));
                }

            } else {
                /**
                 * Lista de Referenciados Recorre cada elemento y lo carga en un
                 * List<Entidad>
                 * Luego lo asigna al atributo de la entidad superior Ejemplo de
                 * Implementación
                 */

//                 List<Planeta> planetaList = new ArrayList<>();
//                 if (docPlanetaList.isEmpty() || docPlanetaList.size() == 0) {
//                    Test.warning("No hay registros de  planeta");
//                } else {
//                     docPlanetaList.forEach(varDoc -> {
//                         planetaList.add(PlanetaSupplier.get(Planeta::new, varDoc));
//                    });
//                 }
//                pais.setPlaneta(planetaList);
            }

            /**
             * *
             * Verifica la siguiente Referecncia
             *
             * @Referenced List<Ocenano> oceano; Determina que es una lista
             * referenciada
             */
            Boolean istListReferecendToOceano = true;
            List<Document> docOceanoList = (List<Document>) document.get("oceano");
            Document docOceano;
            if (!istListReferecendToOceano) {

//                if (docOceanoList.isEmpty() || docOceanoList.size() == 0) {
//                    Test.warning("No hay registros de oceano");
//                } else {
//                    docOceano = docOceanoList.get(0);
//                    pais.setOceano(OceanoSupplier.get(Oceano::new, docOceano));
//                    Test.msgTab("docOceano obtenido:" + docOceano);
//                }
            } else {
                /**
                 * Lista de Referenciados Recorre cada elemento y lo carga en un
                 * List<Entidad>
                 * Luego lo asigna al atributo de la entidad superior
                 */
                List<Oceano> oceanoList = new ArrayList<>();
                if (docOceanoList.isEmpty() || docOceanoList.size() == 0) {
                    Test.warning("No hay registros de oceano");
                } else {
                    docOceanoList.forEach(varDoc -> {
                        oceanoList.add(OceanoSupplier.get(Oceano::new, varDoc));
                    });
                }
                pais.setOceano(oceanoList);

            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return pais;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Pais get(Supplier<? extends Pais> s, Document document)">
    /**
     * 
     * @param s
     * @param documentPaisList
     * @param documentPlanetaList
     * @param documentOceanoList
     * @return  Se invoca desde otro Supplier
     */
    public static Pais get(Supplier<? extends Pais> s, List<Document> documentPaisList, List<Document> documentPlanetaList, List<Document> documentOceanoList) {
        Pais pais = s.get();
        try {
            /**
             * Cuando se usan list se obtiene el primer elemento para procesarlo
             */

            /**
             * Entidad: Pais
             * 
             * Pais{
             *      @Embedded Idioma idioma;
             *      @Embedded List<Musica>;
             *      @Referenced Planeta planeta;
             *      @Referenced List<Oceano>; 
             * }
             * 
             * Nivel de Trabajo : 1
             * 
             * Esquema de Niveles:
             * |Nivel 1  |  Nivel 0
             * Pais    -->  @E(Idioma)
             * Pais    -->  @E(List<Musica>
             * Pais    -->  @R(Planeta)
             * Pais    -->  @R(List<Planeta>
             * 
             */
            Document document = documentPaisList.get(0);

            pais.setIdpais(String.valueOf(document.get("idpais")));
            pais.setPais(String.valueOf(document.get("pais")));

            /**
             * ---------------------------------------------
             *
             * @Embedded simple ----------------------------------------------
             */
            Document doc = (Document) document.get("idioma");
            Jsonb jsonb = JsonbBuilder.create();
            Idioma idioma = jsonb.fromJson(doc.toJson(), Idioma.class);
            pais.setIdioma(idioma);

            /**
             * --------------------------------------------------
             *
             * @Embedded List<Musica>
             * Debe utilizar una lista temporal para almacenar los valores *
             * --------------------------------------------------
             */
            List<Musica> musicaList = new ArrayList<>();
            List<Document> musicDoc = (List) document.get("musica");
            for (Document docm : musicDoc) {
                Musica musica = jsonb.fromJson(docm.toJson(), Musica.class);
                musicaList.add(musica);
            }
            pais.setMusica(musicaList);

            /**
             * @Referenced Nota: Las referencias generadas mediante lookup nos
             * devuelven un List<>
             * Por lo tanto debemos verificar si es una entidad simple o un
             * @Referenced List<Entidad>
             */
            /*
            -------------------------------------------------------
              /**
             * --------------------------------------------------
             * @Referenced Planeta planeta;
             * Es una referencia simple no usa List
             * --------------------------------------------------
             */
            Boolean istListReferecendToPlaneta = false;
            Document docPlaneta;
            if (!istListReferecendToPlaneta) {
                if (documentPlanetaList.isEmpty() || documentPlanetaList.size() == 0) {
                } else {
                    docPlaneta = documentPlanetaList.get(0);
                    pais.setPlaneta(PlanetaSupplier.get(Planeta::new, docPlaneta));
                }

            } else {
                /**
                 * Lista de Referenciados Recorre cada elemento y lo carga en un
                 * List<Entidad>
                 * Luego lo asigna al atributo de la entidad superior Ejemplo de
                 * Implementación
                 */

//                 List<Planeta> planetaList = new ArrayList<>();
//                 if (docPlanetaList.isEmpty() || docPlanetaList.size() == 0) {
//                    Test.warning("No hay registros de  planeta");
//                } else {
//                     docPlanetaList.forEach(varDoc -> {
//                         planetaList.add(PlanetaSupplier.get(Planeta::new, varDoc));
//                    });
//                 }
//                pais.setPlaneta(planetaList);
            }

            /**
             * *
             * Verifica la siguiente Referecncia
             *
             * @Referenced List<Ocenano> oceano; Determina que es una lista
             * referenciada
             */
            Boolean istListReferecendToOceano = true;
//            List<Document> docOceanoList = (List<Document>) document.get("oceano");
            Document docOceano;
            if (!istListReferecendToOceano) {

//                if (docOceanoList.isEmpty() || docOceanoList.size() == 0) {
//                    Test.warning("No hay registros de oceano");
//                } else {
//                    docOceano = docOceanoList.get(0);
//                    pais.setOceano(OceanoSupplier.get(Oceano::new, docOceano));
//                    Test.msgTab("docOceano obtenido:" + docOceano);
//                }
            } else {
                /**
                 * Lista de Referenciados Recorre cada elemento y lo carga en un
                 * List<Entidad>
                 * Luego lo asigna al atributo de la entidad superior
                 */
                List<Oceano> oceanoList = new ArrayList<>();
                if (documentOceanoList.isEmpty() || documentOceanoList.size() == 0) {
                    Test.warning("No hay registros de oceano");
                } else {
                    documentOceanoList.forEach(varDoc -> {
                        oceanoList.add(OceanoSupplier.get(Oceano::new, varDoc));
                    });
                }
                pais.setOceano(oceanoList);

            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return pais;

    }
// </editor-fold>

    
}
