/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.ConsoleUtil;
import com.avbravo.jmoordb.core.util.DocumentUtil;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Idioma;
import com.avbravo.mongodbatlasdriver.model.Musica;
import com.avbravo.mongodbatlasdriver.model.Oceano;
import com.avbravo.mongodbatlasdriver.model.Pais;
import com.avbravo.mongodbatlasdriver.model.Planeta;
import com.avbravo.mongodbatlasdriver.repository.OceanoRepository;
import com.avbravo.mongodbatlasdriver.repository.PlanetaRepository;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
@RequestScoped
public class PaisSupplier implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="level">
    LookupSupplierLevel levelLocal = LookupSupplierLevel.ONE;
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
    // <editor-fold defaultstate="collapsed" desc="@Inject">
    @Inject
    PlanetaRepository planetaRepository;
    @Inject
    OceanoRepository oceanoRepository;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Pais get(Supplier<? extends Pais> s, Document document)">
    public Pais get(Supplier<? extends Pais> s, Document document) {
        Pais pais = s.get();
        try {
            ConsoleUtil.info(Test.nameOfClassAndMethod() + " Dpcument " + document.toJson());
            /**
             * --------------------------------------------------------
             *
             * Step:0
             * <Attributes and @Embedded>
             * --------------------------------------------------------
             */
            pais.setIdpais(String.valueOf(document.get("idpais")));
            pais.setPais(String.valueOf(document.get("pais")));
            ConsoleUtil.info("--->voy a procesar <----");
            /**
             * ---------------------------------------------
             *
             * @Embedded simple ----------------------------------------------
             */
            Document doc = (Document) document.get("idioma");
            Jsonb jsonb = JsonbBuilder.create();
            Idioma idioma = jsonb.fromJson(doc.toJson(), Idioma.class);
            pais.setIdioma(idioma);
            ConsoleUtil.info("--->pase Idioma <----");
            /**
             * --------------------------------------------------
             *
             * @Embedded List<Musica>
             * Debe utilizar una lista temporal para almacenar los valores
             * --------------------------------------------------
             */
            List<Musica> musicaList = new ArrayList<>();
            List<Document> musicDoc = (List) document.get("musica");
            for (Document docm : musicDoc) {
                Musica musica = jsonb.fromJson(docm.toJson(), Musica.class);
                musicaList.add(musica);
            }
            pais.setMusica(musicaList);
            ConsoleUtil.info("--->pase Musica <----");
            /**
             * ------------------------------------------------
             *
             * Step 1:
             * <@Referemced>
             * Generar las interfaces Referenced 
             * Verificar si es List<> o una Referencia simple
             * Obtener el valor de la llave primaria mediatne DocumentUtil.getIdValue(...)
             * 
             */
            /* --------------------------------------------------
             * @Referenced Planeta planeta;
             * Es una referencia simple no usa List
             * Leer la anotacion  @Referenced
             * @Referenced(from = "planeta",localField = "planeta.idplaneta",foreignField = "idplaneta",as ="planeta")
             *  private Planeta planeta;
             * --------------------------------------------------
             */
            Referenced planetaReferenced = new Referenced() {
                @Override
                public String from() {
                    return "planeta";
                }

                @Override
                public String localField() {
                    return "planeta.idplaneta";
                }

                @Override
                public String foreignField() {
                    return "idplaneta";
                }

                @Override
                public String as() {
                    return "planeta";
                }

                @Override
                public boolean lazy() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public boolean typeFieldkeyString() {
                    return true;
                }
            };

            ConsoleUtil.info("--> document.get(planeta) " + document.get(planetaReferenced.from()).toString());
            ConsoleUtil.info("invocare el proceso del id");
            /**
             * Crear una variable del tipo y valor de Referenced.foreigndFiel()
             * Verificar el tipo keyString()
             *
             */

            ConsoleUtil.redBackground("idplaneta obtenido " + DocumentUtil.getIdValue(document, planetaReferenced));
            Boolean istListReferecendToPlaneta = false;
            if (!istListReferecendToPlaneta) {
                Optional<Planeta> planetaOptional = Optional.empty();
                if (planetaReferenced.typeFieldkeyString()) {
                    planetaOptional = planetaRepository.findById(DocumentUtil.getIdValue(document, planetaReferenced));
                } else {
                    //planetaOptional = planetaRepository.findById(Integer.parseInt(DocumentUtil.getId(document, planetaReferenced)));
                }

                if (planetaOptional.isPresent()) {
                    pais.setPlaneta(planetaOptional.get());
                } else {
                    Test.warning("No tiene referencia a Planeta");
                }
            } else {
                List<Document> documentPlanetaList = (List<Document>) document.get("planeta");
                List<Planeta> planetaList = new ArrayList<>();
                for (Document docPlaneta : documentPlanetaList) {
                    Optional<Planeta> planetaOptional = planetaRepository.findById(String.valueOf(docPlaneta.get("planeta")));
                    if (planetaOptional.isPresent()) {
                        planetaList.add(planetaOptional.get());
                    } else {
                        Test.warning("No tiene referencia a Planeta ");
                    }
                }
                /**
                 * Si fuera referenciado se elimina el comentario
                 */
                //pais.setPlaneta(planetaList);
            }
            /* --------------------------------------------------
             * @Referenced List<Oceano> oceano;
             * Es una List<> referenciada
             * --------------------------------------------------
             */
            Boolean istListReferecendToOceano = false;
            if (!istListReferecendToOceano) {
                Optional<Oceano> oceanoOptional = oceanoRepository.findById(String.valueOf(document.get("oceano")));
                if (oceanoOptional.isPresent()) {
                    //pais.setOceano(oceanoOptional.get());
                } else {
                    Test.warning("No tiene referencia a Oceano ");
                }
            } else {
                List<Document> documentOceanoList = (List<Document>) document.get("oceano");
                List<Oceano> oceanoList = new ArrayList<>();
                for (Document docOceano : documentOceanoList) {
                    Optional<Oceano> oceanoOptional = oceanoRepository.findById(String.valueOf(docOceano.get("oceano")));
                    if (oceanoOptional.isPresent()) {
                        oceanoList.add(oceanoOptional.get());
                    } else {
                        Test.warning("No tiene referencia a Planeta ");
                    }
                }
                /**
                 * Si fuera referenciado se elimina el comentario
                 */
                pais.setOceano(oceanoList);
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return pais;

    }
// </editor-fold>

}
