/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository.implementations;

import com.avbravo.jmoordb.core.util.Test;
import com.avbravo.mongodbatlasdriver.model.Oceano;
import com.avbravo.mongodbatlasdriver.repository.OceanoRepository;
import com.avbravo.mongodbatlasdriver.supplier.OceanoSupplier;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.eclipse.microprofile.config.Config;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
//@Stateless
public class OceanoRepositoryImpl implements OceanoRepository {

    // <editor-fold defaultstate="collapsed" desc="@Inject">
    @Inject
    private Config config;

    @Inject
    MongoClient mongoClient;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Supplier">
    @Inject
    OceanoSupplier oceanoSupplier;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Oceano> findAll()">
    @Override
    public List<Oceano> findAll() {

        List<Oceano> list = new ArrayList<>();
        try {

            MongoDatabase database = mongoClient.getDatabase("world");

            MongoCollection<Document> collection = database.getCollection("oceano");

            /**
             * Es una entidad de nivel 0 LookupSupplier.ZERO no usa lookup
             *
             */
            MongoCursor<Document> cursor = collection.find().iterator();

            try {
                while (cursor.hasNext()) {

                    list.add(oceanoSupplier.get(Oceano::new, cursor.next()));
                }
            } finally {
                cursor.close();
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return list;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<Oceano> findById(String id) ">
    @Override
    public Optional<Oceano> findById(String id) {

        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("oceano");
            Document doc = collection.find(eq("idoceano", id)).first();

            Oceano oceano = oceanoSupplier.get(Oceano::new, doc);

            return Optional.of(oceano);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return Optional.empty();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Optional<Oceano> save(Oceano oceano)">

    @Override
    public Optional<Oceano> save(Oceano oceano) {
        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("oceano");

            if (findById(oceano.getIdoceano()).isPresent()) {
                Test.warning("Eciste un registro con ese id");
                return Optional.empty();
            }

            Jsonb jsonb = JsonbBuilder.create();

            InsertOneResult insertOneResult = collection.insertOne(Document.parse(jsonb.toJson(oceano)));
            if (insertOneResult.getInsertedId() != null) {
                return Optional.of(oceano);
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return Optional.empty();

    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean update(Oceano oceano) ">

    @Override
    public Boolean update(Oceano oceano) {
        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("oceano");

            if (!findById(oceano.getIdoceano()).isPresent()) {
                Test.warning("Eciste un registro con ese id");
                return Boolean.FALSE;
            }
            Bson filter = Filters.empty();
            filter = Filters.eq("idoceano", oceano.getIdoceano());

            Jsonb jsonb = JsonbBuilder.create();

            UpdateResult result = collection.updateOne(filter, Document.parse(jsonb.toJson(oceano)));
            System.out.println("Matched document count: " + result.getMatchedCount());
            System.out.println("Modified document count: " + result.getModifiedCount());
            if (result.getModifiedCount() > 0) {
                return Boolean.TRUE;
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return Boolean.FALSE;

    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="Boolean delete(String id) "> 
    @Override
    public Boolean delete(String id) {
        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("oceano");
            Bson filter = Filters.eq("idoceano", id);
            DeleteResult deleteResult = collection.deleteOne(filter);

            System.out.println("Modified document count: " + deleteResult.getDeletedCount());
            if (deleteResult.getDeletedCount() > 0) {
                return Boolean.TRUE;
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return Boolean.FALSE;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="List<Oceano> findByOceano(String contry)">
    @Override
    public List<Oceano> findByOceano(String oceano) {
        List<Oceano> list = new ArrayList<>();
        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("oceano");

            MongoCursor<Document> cursor = collection.find(eq("oceano", oceano)).iterator();

            try {
                while (cursor.hasNext()) {

                    list.add(oceanoSupplier.get(Oceano::new, cursor.next()));
                }
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return list;
    }
    // </editor-fold>

    @Override
    public List<Oceano> jsonQuery(Document filter, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        List<Oceano> list = new ArrayList<>();
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            MongoDatabase database = mongoClient.getDatabase("world");

            MongoCollection<Document> collection = database.getCollection("oceano");

            MongoCursor<Document> cursor = collection.find(filter)
                    .skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0)
                    .limit(rowsForPage)
                    .sort(sortQuery).iterator();

            try {
                while (cursor.hasNext()) {

                    list.add(oceanoSupplier.get(Oceano::new, cursor.next()));
                }
            } finally {
                cursor.close();
            }

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return list;
    }
}
