/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.DocumentUtil;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Pais;
import com.avbravo.mongodbatlasdriver.model.Provincia;
import com.avbravo.mongodbatlasdriver.model.Planeta;
import com.avbravo.mongodbatlasdriver.repository.PaisRepository;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class ProvinciaSupplier {

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
   // <editor-fold defaultstate="collapsed" desc="@Inject">
    @Inject
    PaisRepository paisRepository;
   
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Pais get(Supplier<? extends Pais> s, Document document)">
    public Provincia get(Supplier<? extends Provincia> s, Document document) {
        Provincia provincia = s.get();
        try {

            /**
             * --------------------------------------------------------
             *
             * Step:0
             * <Attributes and @Embedded>
             * --------------------------------------------------------
             */
            provincia.setIdprovincia(String.valueOf(document.get("idprovincia")));
            provincia.setProvincia(String.valueOf(document.get("provincia")));

            /**
             * ---------------------------------------------
             *
             * @Embedded simple ----------------------------------------------
             */
           
            /**
             * --------------------------------------------------
             *
             * @Embedded List<>
             * Debe utilizar una lista temporal para almacenar los valores
             * --------------------------------------------------
             */
          

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
           
            Referenced paisReferenced = new Referenced() {
                @Override
                public String from() {
                    return "pais";
                }

                @Override
                public String localField() {
                    return "pais.idpais";
                }

                @Override
                public String foreignField() {
                    return "idpais";
                }

                @Override
                public String as() {
                    return "pais";
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
            Boolean istListReferecendToPais = false;
            if (!istListReferecendToPais) {
                Optional<Pais> paisOptional = paisFindPK(document, paisReferenced);
                if (paisOptional.isPresent()) {
                    provincia.setPais(paisOptional.get());
                } else {
                    Test.warning("No tiene referencia a Planeta");
                }
            } else {

                /**
                 * Pasos para @Referenced List<>
                 * 1- Obtener la lista documento 2- Obtener un List<SDocument>
                 * de las llaves primarias
                 */
                List<Document> documentPaisList = (List<Document>) document.get(paisReferenced.from());
                List<Pais> paisList = new ArrayList<>();
                List<Document> documentPaisPkList = DocumentUtil.getListValue(document, paisReferenced);
                if (documentPaisPkList == null || documentPaisPkList.isEmpty()) {
                    Test.msg("No se pudo decomponer la lista de id referenced....");
                } else {
                    for (Document documentPk : documentPaisPkList) {
                        Optional<Pais> paisOptional = paisFindPK(documentPk, paisReferenced);
                        if (paisOptional.isPresent()) {
                            paisList.add(paisOptional.get());
                        } else {
                            Test.warning("No tiene referencia a " + paisReferenced.from());
                        }
                    }
                }
              //provincia.setPais(paisList);
            }
           
           

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return provincia;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<Pais> planetaFindPK(Document document, Referenced paisReferenced)">
    /**
     *
     * @param document
     * @param planetaReferenced
     * @return Devuelve un Optional del resultado de la busqueda por la llave
     * primaria Dependiendo si es entero o String
     */
    private Optional<Pais> paisFindPK(Document document, Referenced paisReferenced) {
        try {
            Optional<Pais> paisOptional = Optional.empty();
            if (paisReferenced.typeFieldkeyString()) {
                paisOptional = paisRepository.findById(DocumentUtil.getIdValue(document, paisReferenced));
            } else {
                //     paisOptional = paisRepository.findById(Integer.parseInt(DocumentUtil.getIdValue(document, paisReferenced)));
            }

            if (paisOptional.isPresent()) {
                return paisOptional;
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
// </editor-fold>
  

}
