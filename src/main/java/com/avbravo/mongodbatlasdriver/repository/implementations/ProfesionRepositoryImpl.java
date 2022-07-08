/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository.implementations;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.mongodbatlasdriver.model.Grupoprofesion;
import com.avbravo.mongodbatlasdriver.model.Profesion;
import com.avbravo.mongodbatlasdriver.repository.ProfesionRepository;
import com.avbravo.mongodbatlasdriver.supplier.ProfesionSupplier;
import com.avbravo.mongodbatlasdriver.supplier.lookup.GrupoprofesionLookupSupplier;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.eclipse.microprofile.config.Config;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
//@Stateless
public class ProfesionRepositoryImpl implements ProfesionRepository {
    // <editor-fold defaultstate="collapsed" desc="level">

 LookupSupplierLevel levelLocal = LookupSupplierLevel.THREE;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="@Inject">

    @Inject
    private Config config;

    @Inject
    MongoClient mongoClient;
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Profesion> findAll()">

    @Override
    public List<Profesion> findAll() {

      List<Profesion> list = new ArrayList<>();
        try {
        
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("profesion");
            
            /**
             * profesion es de nivel 3
             * Nivel 3           Nivel 2       Nivel 1   Nivel 0
             * profesion --> provincia    --> pais --> planeta
             * profesion --> provincia    --> pais --> oceano
             *                   provincia.idprovincia -->provincia.pais.idpais-->pais.planeta.idplaneta
             *                   provincia.idprovincia -->provincia.pais.idpais-->pais.ocenao.idoceano
             * Observe que cambia de nivel1  a nivel 9
             */
            /**
             * Analiza las referencias
             */
            /**
             *
             * @Referenced(from = "planeta",localField =
             * "idplaneta",foreignField = "idplaneta",as ="planeta") private
             * Planeta planeta;
             */
//             Bson pipeline = lookup("planeta", "planeta.idplaneta", "idplaneta", "planeta");

            /**
             * Aqui lee la entidad Provincia y busca todas las references
             *
             * @Referenced(from = "planeta",localField =
             * "idplaneta",foreignField = "idplaneta",as ="planeta") private
             * Planeta planeta;
             *
             * Encontro Planeta asi que obtiene la clase y la referencia
             *
             */
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
                    return false;
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            };

           
            List<Bson> lookup = new ArrayList<>();

         
            /**
             * Tengo que analizar la clase y saber que hay dentro de el otras referencias
             * por ejemplo profesion-->pais-->planeta (Planeta tiene referenciados dentro de el 
             * asi que debe ser un parent para otros.
             * por eso se coloca alli
             */
            /**
             * Tiene @Referenceed a Provincia
             *        Si se analiza Provincia tiene @Referenced a Oceano y Planeta
             *        La busqueda del proximo nivel no aplica a pais.pais.idpais si no debe ser
             *        pais.idpais por lo que no pasa en false.
             */

            List<Bson> pipelineGrupoprofesion= GrupoprofesionLookupSupplier.get(Grupoprofesion::new, grupoprofesionReferenced, "grupoprofesion",levelLocal,false);

            if (pipelineGrupoprofesion.isEmpty() || pipelineGrupoprofesion.size() == 0) {
                Test.msg("pipeLineProvincia.isEmpty()");
            } else {
            pipelineGrupoprofesion.forEach(b -> {
                    lookup.add(b);
                });
            }

           

            /**
             * Ejecuta la consulta
             */
            MongoCursor<Document> cursor;
            if (lookup.isEmpty() || lookup.size() == 0) {
                cursor = collection.find().iterator();

            } else {
                Test.box("lookup en ProfesionRepositoyyImpl: "+lookup.toString());
                
                cursor = collection.aggregate(lookup).iterator();
      
            }

            try {
                while (cursor.hasNext()) {
                    Profesion profesion = ProfesionSupplier.get(Profesion::new, cursor.next());
                    list.add(profesion);
                }
            } finally {
                cursor.close();
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }
        return list;
    }
// </editor-fold>
    @Override
    public Optional<Profesion> findById(String id) {

        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("profesion");
            Document doc = collection.find(eq("idprofesion", id)).first();
           
            Profesion profesion = ProfesionSupplier.get(Profesion::new,doc);

            return Optional.of(profesion);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public Profesion save(Profesion profesion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Profesion> findByProfesion(String contry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
