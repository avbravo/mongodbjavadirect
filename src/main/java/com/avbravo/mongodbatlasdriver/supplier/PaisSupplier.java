/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.annotation.Referenced;
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

            /**
             * --------------------------------------------------------
             *
             * Step:0
             * <Attributes and @Embedded>
             * --------------------------------------------------------
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

            /**
             * ------------------------------------------------
             *
             * Step 1:
             * <@Referemced>
             * Generar las interfaces Referenced Verificar si es List<> o una
             * Referencia simple Obtener el valor de la llave primaria mediatne
             * DocumentUtil.getIdValue(...)
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

            /**
             * Crear una variable del tipo y valor de Referenced.foreigndFiel()
             * Verificar el tipo keyString()
             *
             */
            Boolean istListReferecendToPlaneta = false;
            if (!istListReferecendToPlaneta) {
                Optional<Planeta> planetaOptional = planetaFindPK(document, planetaReferenced);
                if (planetaOptional.isPresent()) {
                    pais.setPlaneta(planetaOptional.get());
                } else {
                    Test.warning("No tiene referencia a Planeta");
                }
            } else {

                /**
                 * Pasos para @Referenced List<>
                 * 1- Obtener la lista documento 2- Obtener un List<SDocument>
                 * de las llaves primarias
                 */
                List<Document> documentPlanetaList = (List<Document>) document.get(planetaReferenced.from());
                List<Planeta> planetaList = new ArrayList<>();
                List<Document> documentPlanetaPkList = DocumentUtil.getListValue(document, planetaReferenced);
                if (documentPlanetaPkList == null || documentPlanetaPkList.isEmpty()) {
                    Test.msg("No se pudo decomponer la lista de id referenced....");
                } else {
                    for (Document documentPk : documentPlanetaPkList) {
                        Optional<Planeta> planetaOptional = planetaFindPK(documentPk, planetaReferenced);
                        if (planetaOptional.isPresent()) {
                            planetaList.add(planetaOptional.get());
                        } else {
                            Test.warning("No tiene referencia a " + planetaReferenced.from());
                        }
                    }
                }
              //pais.setPlaneta(planetaList);
            }
            /* --------------------------------------------------
             * @Referenced List<Oceano> oceano;
             * Es una List<> referenciada
             * --------------------------------------------------
             */
            Referenced oceanoReferenced = new Referenced() {
                @Override
                public String from() {
                    return "oceano";
                }

                @Override
                public String localField() {
                    return "oceano.idoceano";
                }

                @Override
                public String foreignField() {
                    return "idoceano";
                }

                @Override
                public String as() {
                    return "oceano";
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

            Boolean istListReferecendToOceano = true;
            if (!istListReferecendToOceano) {
               Optional<Oceano> oceanoOptional = oceanoFindPK(document, oceanoReferenced);
                
                if (oceanoOptional.isPresent()) {
                    //   pais.setOceano(oceanoOptional.get());
                } else {
                    Test.warning("No tiene referencia a " + oceanoReferenced.from());
                }
            } else {

                /**
                 * Pasos para @Referenced List<>
                 * 1- Obtener la lista documento 2- Obtener un List<SDocument>
                 * de las llaves primarias
                 */
                List<Document> documentOceanoList = (List<Document>) document.get(oceanoReferenced.from());
                List<Oceano> oceanoList = new ArrayList<>();
                List<Document> documentOceanoPkList = DocumentUtil.getListValue(document, oceanoReferenced);
                if (documentOceanoPkList == null || documentOceanoPkList.isEmpty()) {
                    Test.msg("No se pudo decomponer la lista de id referenced....");
                } else {
                    for (Document documentPk : documentOceanoPkList) {
                        Optional<Oceano> oceanoOptional = oceanoFindPK(documentPk, oceanoReferenced);
                        if (oceanoOptional.isPresent()) {
                            oceanoList.add(oceanoOptional.get());
                        } else {
                            Test.warning("No tiene referencia a " + oceanoReferenced.from());
                        }
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

    // <editor-fold defaultstate="collapsed" desc="Optional<Planeta> planetaFindPK(Document document, Referenced planetaReferenced)">
    /**
     *
     * @param document
     * @param planetaReferenced
     * @return Devuelve un Optional del resultado de la busqueda por la llave
     * primaria Dependiendo si es entero o String
     */
    private Optional<Planeta> planetaFindPK(Document document, Referenced planetaReferenced) {
        try {
            Optional<Planeta> planetaOptional = Optional.empty();
            if (planetaReferenced.typeFieldkeyString()) {
                planetaOptional = planetaRepository.findById(DocumentUtil.getIdValue(document, planetaReferenced));
            } else {
                //     planetaOptional = planetaRepository.findById(Integer.parseInt(DocumentUtil.getIdValue(document, planetaReferenced)));
            }

            if (planetaOptional.isPresent()) {
                return planetaOptional;
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Optional<Oceano> oceanoFindPK(Document document, Referenced planetaReferenced)">

    /**
     *
     * @param document
     * @param planetaReferenced
     * @return Devuelve un Optional del resultado de la busqueda por la llave
     * primaria Dependiendo si es entero o String
     */
    private Optional<Oceano> oceanoFindPK(Document document, Referenced oceanoReferenced) {
        try {
            Optional<Oceano> oceanoOptional = Optional.empty();
            if (oceanoReferenced.typeFieldkeyString()) {
                oceanoOptional = oceanoRepository.findById(DocumentUtil.getIdValue(document, oceanoReferenced));
            } else {
                //     oceanoOptional = oceanoRepository.findById(Integer.parseInt(DocumentUtil.getIdValue(document, oceanoReferenced)));
            }

            if (oceanoOptional.isPresent()) {
                return oceanoOptional;
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
// </editor-fold>
}
