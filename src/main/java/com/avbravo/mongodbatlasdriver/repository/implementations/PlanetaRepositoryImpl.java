/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository.implementations;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Planeta;
import com.avbravo.mongodbatlasdriver.repository.PlanetaRepository;
import com.avbravo.mongodbatlasdriver.supplier.PlanetaSupplier;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.bson.Document;
import org.eclipse.microprofile.config.Config;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
//@Stateless
public class PlanetaRepositoryImpl implements PlanetaRepository {
    // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.ZERO;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="@Inject">

    @Inject
    private Config config;

    @Inject
    MongoClient mongoClient;
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Planeta> findAll()">

    @Override
    public List<Planeta> findAll() {

        List<Planeta> list = new ArrayList<>();
        try {

            MongoDatabase database = mongoClient.getDatabase("world");
     
            MongoCollection<Document> collection = database.getCollection("planeta");
    /**
             * Es una entidad de nivel 0
             * LookupSupplier.ZERO no usa lookup
             * 
             */
            MongoCursor<Document> cursor = collection.find().iterator();
            
            Jsonb jsonb = JsonbBuilder.create();
            try {
                while (cursor.hasNext()) {
                    Planeta planeta = PlanetaSupplier.get(Planeta::new,cursor.next());                   
                    list.add(planeta);
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
    public Optional<Planeta> findById(String id) {

        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("planeta");
            /**
             * Es una entidad de nivel 0
             * LookupSupplier.ZERO no usa lookup
             * 
             */
            Document doc = collection.find(eq("idplaneta", id)).first();
           
            Planeta planeta = PlanetaSupplier.get(Planeta::new,doc);

            return Optional.of(planeta);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public Planeta save(Planeta planeta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Planeta> findByPlaneta(String contry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
