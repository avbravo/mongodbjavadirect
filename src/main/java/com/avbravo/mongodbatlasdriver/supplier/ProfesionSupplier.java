/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.supplier;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.DocumentUtil;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Grupoprofesion;
import com.avbravo.mongodbatlasdriver.model.Grupoprofesion;
import com.avbravo.mongodbatlasdriver.model.Profesion;
import com.avbravo.mongodbatlasdriver.repository.GrupoprofesionRepository;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
@RequestScoped
public class ProfesionSupplier implements Serializable{
       // <editor-fold defaultstate="collapsed" desc="graphics">

    /**
     * Profesion{
     *
     * @Referenced Grupopresion{ } }
     */
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="@Inject">
    @Inject
GrupoprofesionRepository grupoprofesionRepository;
    
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Profesion get(Supplier<? extends Profesion> s, Document document)">

    public  Profesion get(Supplier<? extends Profesion> s, Document document) {
        Profesion profesion = s.get();
        try {
            

            profesion.setIdprofesion(String.valueOf(document.get("idprofesion")));
            profesion.setProfesion(String.valueOf(document.get("profesion")));

            Referenced grupoprofesionReferenced = new Referenced() {
                @Override
                public String from() {
                    return "grupoprofesion";
                }

                @Override
                public String localField() {
                    return "grupoprofesion.idgrupoprofesion";
                }

                @Override
                public String foreignField() {
                    return "idgrupoprofesion";
                }

                @Override
                public String as() {
                    return "grupoprofesion";
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
            Boolean istListReferecendToGrupoprofesion = false;
            if (!istListReferecendToGrupoprofesion) {
                Optional<Grupoprofesion> grupoprofesionOptional = grupoprofesionFindPK(document, grupoprofesionReferenced);
                if (grupoprofesionOptional.isPresent()) {
                   profesion.setGrupoprofesion(grupoprofesionOptional.get());
                } else {
                    Test.warning("No tiene referencia a Grupoprofesion");
                }
            } else {

                /**
                 * Pasos para @Referenced List<>
                 * 1- Obtener la lista documento 2- Obtener un List<SDocument>
                 * de las llaves primarias
                 */
                List<Document> documentGrupoprofesionList = (List<Document>) document.get(grupoprofesionReferenced.from());
                List<Grupoprofesion> grupoprofesionList = new ArrayList<>();
                List<Document> documentGrupoprofesionPkList = DocumentUtil.getListValue(document, grupoprofesionReferenced);
                if (documentGrupoprofesionPkList == null || documentGrupoprofesionPkList.isEmpty()) {
                    Test.msg("No se pudo decomponer la lista de id referenced....");
                } else {
                    for (Document documentPk : documentGrupoprofesionPkList) {
                        Optional<Grupoprofesion> grupoprofesionOptional = grupoprofesionFindPK(documentPk, grupoprofesionReferenced);
                        if (grupoprofesionOptional.isPresent()) {
                            grupoprofesionList.add(grupoprofesionOptional.get());
                        } else {
                            Test.warning("No tiene referencia a " + grupoprofesionReferenced.from());
                        }
                    }
                }
              //profesion.setGrupoprofesion(grupoprofesionList);
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return profesion;

    }
// </editor-fold>
   
    
      // <editor-fold defaultstate="collapsed" desc="Optional<Grupoprofesion> grupoprofesionFindPK(Document document, Referenced grupoprofesionReferenced)">
    /**
     *
     * @param document
     * @param grupoprofesionReferenced
     * @return Devuelve un Optional del resultado de la busqueda por la llave
     * primaria Dependiendo si es entero o String
     */
    private Optional<Grupoprofesion> grupoprofesionFindPK(Document document, Referenced grupoprofesionReferenced) {
        try {
            Optional<Grupoprofesion> grupoprofesionOptional = Optional.empty();
            if (grupoprofesionReferenced.typeFieldkeyString()) {
              grupoprofesionOptional = grupoprofesionRepository.findById(DocumentUtil.getIdValue(document, grupoprofesionReferenced));
            } else {
                //     grupoprofesionOptional = grupoprofesionRepository.findById(Integer.parseInt(DocumentUtil.getIdValue(document, grupoprofesionReferenced)));
            }

            if (grupoprofesionOptional.isPresent()) {
                return grupoprofesionOptional;
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
// </editor-fold>
}
