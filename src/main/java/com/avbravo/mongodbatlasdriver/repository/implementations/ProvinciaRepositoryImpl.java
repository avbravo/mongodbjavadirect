/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository.implementations;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.ConsoleUtil;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.mongodbatlasdriver.model.Pais;
import com.avbravo.mongodbatlasdriver.model.Provincia;
import com.avbravo.mongodbatlasdriver.repository.ProvinciaRepository;
import com.avbravo.mongodbatlasdriver.supplier.ProvinciaSupplier;
import com.avbravo.mongodbatlasdriver.supplier.lookup.PaisLookupSupplier;
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
public class ProvinciaRepositoryImpl implements ProvinciaRepository {
   // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.TWO;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="@Inject">

    @Inject
    private Config config;

    @Inject
    MongoClient mongoClient;
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Provincia> findAll()">

    @Override
    public List<Provincia> findAll() {

      List<Provincia> list = new ArrayList<>();
        try {
            ConsoleUtil.warning(Test.nameOfClassAndMethod()+ "findAll()");
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("provincia");
            /**
             * PROVINCIA es de nivel 2
             * Nivel 2  Nivel 1  Nivel 0
             * provincia --> pais--> planeta
             * provincia --> pais--> oceano
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
             * Aqui lee la entidad Pais y busca todas las references
             *
             * @Referenced(from = "planeta",localField =
             * "idplaneta",foreignField = "idplaneta",as ="planeta") private
             * Planeta planeta;
             *
             * Encontro Planeta asi que obtiene la clase y la referencia
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
             * por ejemplo provincia-->pais-->planeta (Planeta tiene referenciados dentro de el 
             * asi que debe ser un parent para otros.
             * por eso se coloca alli
             */
            /**
             * Tiene @Referenceed a Pais
             *        Si se analiza Pais tiene @Referenced a Oceano y Planeta
             *        La busqueda del proximo nivel no aplica a pais.pais.idpais si no debe ser
             *        pais.idpais por lo que no pasa en false.
             */

            List<Bson> pipelinePais= PaisLookupSupplier.get(Pais::new, paisReferenced, "pais",levelLocal,false);

            if (pipelinePais.isEmpty() || pipelinePais.size() == 0) {
                Test.msg("pipeLinePais.isEmpty()");
            } else {
                pipelinePais.forEach(b -> {
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
               
                
                cursor = collection.aggregate(lookup).iterator();
      
            }

            try {
                while (cursor.hasNext()) {
                    Provincia provincia = ProvinciaSupplier.get(Provincia::new, cursor.next());
                    list.add(provincia);
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
    public Optional<Provincia> findById(String id) {

        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("provincia");
            Document doc = collection.find(eq("idprovincia", id)).first();
           
            Provincia provincia = ProvinciaSupplier.get(Provincia::new,doc);

            return Optional.of(provincia);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public Provincia save(Provincia provincia) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Provincia> findByProvincia(String contry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
