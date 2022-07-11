/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.DocumentUtil;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Corregimiento;
import com.avbravo.mongodbatlasdriver.model.Provincia;
import com.avbravo.mongodbatlasdriver.repository.ProvinciaRepository;
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
public class CorregimientoSupplier {

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
    // <editor-fold defaultstate="collapsed" desc="@Inject">
    @Inject
    ProvinciaRepository provinciaRepository;
   
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Provincia get(Supplier<? extends Provincia> s, Document document)">
    public Corregimiento get(Supplier<? extends Corregimiento> s, Document document) {
       Corregimiento corregimiento = s.get();
        try {

            /**
             * --------------------------------------------------------
             *
             * Step:0
             * <Attributes and @Embedded>
             * --------------------------------------------------------
             */
         corregimiento.setIdcorregimiento(String.valueOf(document.get("idcorregimiento")));
          corregimiento.setCorregimiento(String.valueOf(document.get("corregimiento")));

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
           
            Referenced provinciaReferenced = new Referenced() {
                @Override
                public String from() {
                    return "provincia";
                }

                @Override
                public String localField() {
                    return "provincia.idprovincia";
                }

                @Override
                public String foreignField() {
                    return "idprovincia";
                }

                @Override
                public String as() {
                    return "provincia";
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
            Boolean istListReferecendToProvincia = false;
            if (!istListReferecendToProvincia) {
                Optional<Provincia> provinciaOptional = provinciaFindPK(document, provinciaReferenced);
                if (provinciaOptional.isPresent()) {
                   corregimiento.setProvincia(provinciaOptional.get());
                } else {
                    Test.warning("No tiene referencia a Planeta");
                }
            } else {

                /**
                 * Pasos para @Referenced List<>
                 * 1- Obtener la lista documento 2- Obtener un List<SDocument>
                 * de las llaves primarias
                 */
                List<Document> documentProvinciaList = (List<Document>) document.get(provinciaReferenced.from());
                List<Provincia> provinciaList = new ArrayList<>();
                List<Document> documentProvinciaPkList = DocumentUtil.getListValue(document, provinciaReferenced);
                if (documentProvinciaPkList == null || documentProvinciaPkList.isEmpty()) {
                    Test.msg("No se pudo decomponer la lista de id referenced....");
                } else {
                    for (Document documentPk : documentProvinciaPkList) {
                        Optional<Provincia> provinciaOptional = provinciaFindPK(documentPk, provinciaReferenced);
                        if (provinciaOptional.isPresent()) {
                            provinciaList.add(provinciaOptional.get());
                        } else {
                            Test.warning("No tiene referencia a " + provinciaReferenced.from());
                        }
                    }
                }
              //provincia.setPais(paisList);
            }
           
           

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return corregimiento;

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
    private Optional<Provincia> provinciaFindPK(Document document, Referenced provinciaReferenced) {
        try {
            Optional<Provincia> provinciaOptional = Optional.empty();
            if (provinciaReferenced.typeFieldkeyString()) {
                provinciaOptional = provinciaRepository.findById(DocumentUtil.getIdValue(document, provinciaReferenced));
            } else {
                //    provinciaOptional = provinciaRepository.findById(Integer.parseInt(DocumentUtil.getIdValue(document, provinciaReferenced)));
            }

            if (provinciaOptional.isPresent()) {
                return provinciaOptional;
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
// </editor-fold>
  

}
