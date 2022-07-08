/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository.implementations;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.mongodbatlasdriver.model.Corregimiento;
import com.avbravo.mongodbatlasdriver.model.Persona;
import com.avbravo.mongodbatlasdriver.model.Profesion;
import com.avbravo.mongodbatlasdriver.repository.PersonaRepository;
import com.avbravo.mongodbatlasdriver.supplier.PersonaSupplier;
import com.avbravo.mongodbatlasdriver.supplier.lookup.CorregimientoLookupSupplier;
import com.avbravo.mongodbatlasdriver.supplier.lookup.ProfesionLookupSupplier;
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
public class PersonaRepositoryImpl implements PersonaRepository {
    // <editor-fold defaultstate="collapsed" desc="level">

    static LookupSupplierLevel levelLocal = LookupSupplierLevel.FOUR;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="@Inject">

    @Inject
    private Config config;

    @Inject
    MongoClient mongoClient;
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Persona> findAll()">

    @Override
    public List<Persona> findAll() {

      List<Persona> list = new ArrayList<>();
        try {
        
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("persona");
            
            /**
             * persona es de nivel 3
             * Nivel 3           Nivel 2       Nivel 1   Nivel 0
             * persona --> provincia    --> pais --> planeta
             * persona --> provincia    --> pais --> oceano
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
            Referenced corregimientoReferenced = new Referenced() {
                @Override
                public String from() {
                    return "corregimiento";
                }

                @Override
                public String localField() {
                    return "corregimiento.idcorregimiento";
                }

                @Override
                public String foreignField() {
                    return "idcorregimiento";
                }

                @Override
                public String as() {
                    return "corregimiento";
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
             * por ejemplo persona-->pais-->planeta (Planeta tiene referenciados dentro de el 
             * asi que debe ser un parent para otros.
             * por eso se coloca alli
             */
            /**
             * Tiene @Referenceed a Provincia
             *        Si se analiza Provincia tiene @Referenced a Oceano y Planeta
             *        La busqueda del proximo nivel no aplica a pais.pais.idpais si no debe ser
             *        pais.idpais por lo que no pasa en false.
             */

            List<Bson> pipelineCorregimiento= CorregimientoLookupSupplier.get(Corregimiento::new, corregimientoReferenced, "corregimiento",levelLocal,false);

            if (pipelineCorregimiento.isEmpty() || pipelineCorregimiento.size() == 0) {
                Test.msg("pipeLineCorregimiento.isEmpty()");
            } else {
                pipelineCorregimiento.forEach(b -> {
                    lookup.add(b);
                });
            }

           
/**
 * Profesion
 */
Referenced profesionReferenced = new Referenced() {
                @Override
                public String from() {
                    return "profesion";
                }

                @Override
                public String localField() {
                    return "profesion.idprofesion";
                }

                @Override
                public String foreignField() {
                    return "idprofesion";
                }

                @Override
                public String as() {
                    return "profesion";
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
            
             List<Bson> pipelineProfesion= ProfesionLookupSupplier.get(Profesion::new, profesionReferenced, "profesion",levelLocal,false);

            if (pipelineProfesion.isEmpty() || pipelineProfesion.size() == 0) {
                Test.msg("pipeLineProfesion.isEmpty()");
            } else {
                pipelineProfesion.forEach(b -> {
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
                Test.box("lookup en PersonaRepositoyyImpl: "+lookup.toString());
                
                cursor = collection.aggregate(lookup).iterator();
      
            }

            try {
                while (cursor.hasNext()) {
                    Persona persona = PersonaSupplier.get(Persona::new, cursor.next());
                    list.add(persona);
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
    public Optional<Persona> findById(String id) {

        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("persona");
            Document doc = collection.find(eq("idpersona", id)).first();
           
            Persona persona = PersonaSupplier.get(Persona::new,doc);

            return Optional.of(persona);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public Persona save(Persona persona) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Persona> findByPersona(String contry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
