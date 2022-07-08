/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository.implementations;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.mongodbatlasdriver.model.Oceano;
import com.avbravo.mongodbatlasdriver.model.Pais;
import com.avbravo.mongodbatlasdriver.model.Planeta;
import com.avbravo.mongodbatlasdriver.repository.PaisRepository;
import com.avbravo.mongodbatlasdriver.supplier.PaisSupplier;
import com.avbravo.mongodbatlasdriver.supplier.lookup.OceanoLookupSupplier;
import com.avbravo.mongodbatlasdriver.supplier.lookup.PlanetaLookupSupplier;
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
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
//@Stateless
public class PaisRepositoryImpl implements PaisRepository {
    // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.ONE;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="  @Inject">
    @Inject
    private Config config;

    @Inject
    @ConfigProperty(name = "mongodb.uri")
    private String mongodburi;

    @Inject
    MongoClient mongoClient;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="  public List<Pais> findAll() {">
    @Override
    public List<Pais> findAll() {

        List<Pais> list = new ArrayList<>();
        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("pais");
            
            /**
             * PAIS es de nivel 1
             * Nivel 1    Nivel 0
             * Pais    -->Planeta
             * Pais    -->Oceano
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
                    return false;
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            };

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
                    return false;
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            };

            List<Bson> lookup = new ArrayList<>();

     
            List<Bson> pipelinePlaneta = PlanetaLookupSupplier.get(Planeta::new, planetaReferenced, "",levelLocal);

            if (pipelinePlaneta.isEmpty() || pipelinePlaneta.size() == 0) {
                Test.msg("pipeLinePlaneta.isEmpty()");
            } else {
                pipelinePlaneta.forEach(b -> {
                    lookup.add(b);
                });
            }

            /*
            Invocando OceanoLookup
             */
       
            List<Bson> pipelineOceano = OceanoLookupSupplier.get(Oceano::new, oceanoReferenced, "",levelLocal);

            if (pipelineOceano.isEmpty() || pipelineOceano.size() == 0) {
                Test.msg("pipeLineOceano.isEmpty()");
            } else {
                pipelineOceano.forEach(b -> {
                    lookup.add(b);
                });
            }

            /**
             * Ejecuta la consulta
             */
            MongoCursor<Document> cursor;
            if (lookup.isEmpty() || lookup.size() == 0) {

                Test.msgTab("[execute find().]");
                cursor = collection.find().iterator();

            } else {
                Test.msgTab("execute aggregate");
                Test.msgTab("lookup "+lookup);
                
                cursor = collection.aggregate(lookup).iterator();
            }

            try {
                while (cursor.hasNext()) {
                
                    Pais pais = PaisSupplier.get(Pais::new, cursor.next());
                    list.add(pais);
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
    // <editor-fold defaultstate="collapsed" desc="  public List<Pais> findAllSimple() {">

    public List<Pais> findAllSimple() {

        List<Pais> list = new ArrayList<>();
        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("pais");
            MongoCursor<Document> cursor = collection.find().iterator();

            try {
                while (cursor.hasNext()) {
                    Pais pais = PaisSupplier.get(Pais::new, cursor.next());
                    list.add(pais);
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
    public Optional<Pais> findById(String id) {

        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("pais");
            Document doc = collection.find(eq("idpais", id)).first();

            Pais pais = PaisSupplier.get(Pais::new, doc);
//            Jsonb jsonb = JsonbBuilder.create();
//            Pais pais = jsonb.fromJson(doc.toJson(), Pais.class);
            return Optional.of(pais);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public Pais save(Pais pais) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Pais> findByPais(String contry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
