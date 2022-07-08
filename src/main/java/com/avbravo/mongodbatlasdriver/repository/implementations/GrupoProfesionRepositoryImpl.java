/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository.implementations;

import com.avbravo.jmoordb.core.lookup.enumerations.LookupSupplierLevel;
import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Grupoprofesion;
import com.avbravo.mongodbatlasdriver.repository.GrupoprofesionRepository;
import com.avbravo.mongodbatlasdriver.supplier.GrupoprofesionSupplier;
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
public class GrupoProfesionRepositoryImpl implements GrupoprofesionRepository {
      // <editor-fold defaultstate="collapsed" desc="level">
     LookupSupplierLevel levelLocal= LookupSupplierLevel.ZERO;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="@Inject">

    @Inject
    private Config config;

    @Inject
    MongoClient mongoClient;
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Grupoprofesion> findAll()">

    @Override
    public List<Grupoprofesion> findAll() {

        List<Grupoprofesion> list = new ArrayList<>();
        try {

            MongoDatabase database = mongoClient.getDatabase("world");
     
            MongoCollection<Document> collection = database.getCollection("grupoprofesion");

            MongoCursor<Document> cursor = collection.find().iterator();
            
            Jsonb jsonb = JsonbBuilder.create();
            try {
                while (cursor.hasNext()) {
                    Grupoprofesion grupoprofesion = GrupoprofesionSupplier.get(Grupoprofesion::new,cursor.next());                   
                    list.add(grupoprofesion);
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
    public Optional<Grupoprofesion> findById(String id) {

        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("grupoprofesion");
            Document doc = collection.find(eq("idgrupoprofesion", id)).first();
           
            Grupoprofesion grupoprofesion = GrupoprofesionSupplier.get(Grupoprofesion::new,doc);

            return Optional.of(grupoprofesion);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " "+e.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public Grupoprofesion save(Grupoprofesion grupoprofesion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Grupoprofesion> findByGrupoprofesion(String contry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
