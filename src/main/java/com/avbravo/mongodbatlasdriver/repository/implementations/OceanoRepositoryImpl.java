/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository.implementations;

import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.mongodbatlasdriver.model.Oceano;
import com.avbravo.mongodbatlasdriver.repository.OceanoRepository;
import com.avbravo.mongodbatlasdriver.supplier.OceanoSupplier;
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
import org.bson.Document;
import org.eclipse.microprofile.config.Config;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
//@Stateless
public class OceanoRepositoryImpl implements OceanoRepository {
       // <editor-fold defaultstate="collapsed" desc="level">
        LookupSupplierLevel levelLocal= LookupSupplierLevel.ZERO;
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="@Inject">

    @Inject
    private Config config;

    @Inject
    MongoClient mongoClient;
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Oceano> findAll()">

    @Override
    public List<Oceano> findAll() {

        List<Oceano> list = new ArrayList<>();
        try {

            MongoDatabase database = mongoClient.getDatabase("world");
     
            MongoCollection<Document> collection = database.getCollection("oceano");

               /**
             * Es una entidad de nivel 0
             * LookupSupplier.ZERO no usa lookup
             * 
             */
            
            MongoCursor<Document> cursor = collection.find().iterator();
            
          
            try {
                while (cursor.hasNext()) {
               
                    list.add(OceanoSupplier.get(Oceano::new,cursor.next()));
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
    public Optional<Oceano> findById(String id) {

        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("oceano");
            Document doc = collection.find(eq("idoceano", id)).first();
           
            Oceano oceano = OceanoSupplier.get(Oceano::new,doc);

            return Optional.of(oceano);
        } catch (Exception e) {
          Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public Oceano save(Oceano oceano) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Oceano> findByOceano(String contry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
