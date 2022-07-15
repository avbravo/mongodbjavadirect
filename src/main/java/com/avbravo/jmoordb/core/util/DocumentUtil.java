/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.jmoordb.core.util;

import com.avbravo.jmoordb.core.annotation.Referenced;
import com.avbravo.jmoordb.core.annotation.enumerations.TypePK;
import com.mongodb.client.MongoClient;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.bson.BsonDocument;
import static org.bson.BsonDocumentWrapper.asBsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author avbravo
 */
public class DocumentUtil {
    // <editor-fold defaultstate="collapsed" desc="String getIdValue(Document document, Referenced referenced)">

    /**
     *
     * @param document
     * @param referenced
     * @return Obtiene el valor del id referenciado
     */
    public static String getIdValue(Document document, Referenced referenced) {
        String result = "";
        try {
           
            String data = "";
            if (document.get(referenced.from()) != null) {
                data = document.get(referenced.from()).toString();                
                data = data.replace("Document{{", "");                
                data = data.replace("}}", "");                
                data = data.replace(referenced.foreignField(), "");                
                data = data.replace("=", "");                
                result = data.trim();
            } else {
                // Cuando se pasa desde un List<Document> de llaves primarias
                data = document.toJson();
                data = data.replace("{", "");               
                data = data.replace("}", "");                
                data = data.replace("\"", "");
                data = data.replace(referenced.foreignField(), "");                
                data = data.replace(":", "");
                result = data.trim();
                
            }
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String getListValue(Document document, Referenced referenced)">

    /**
     *
     * @param document
     * @param referenced
     * @return List<Document> con las referencias de un @Referenced List<>
     */
    public static List<Document> getListValue(Document document, Referenced referenced) {
        List<Document> result = new ArrayList<>();
        try {

           
            String data = document.get(referenced.from()).toString();
                       data = data.replace("Document{{", "");
           
            data = data.replace("}}", "");
           
            data = data.replace(referenced.foreignField(), "");
           
            data = data.replace("=", "");
           
            data = data.replace("[", "");
           
            data = data.trim();
            data = data.replace("[", "");
            
            data = data.replace("]", "");
           
            data = data.trim();
            StringTokenizer st = new StringTokenizer(data, ",");
            while (st.hasMoreTokens()) {
                Document doc = new Document();
                if (referenced.typePK().equals(TypePK.STRING)) {
                    doc.append(referenced.foreignField(), st.nextToken().trim());
                } else {
                    Integer value = Integer.parseInt(st.nextToken().trim());
                    doc.append(referenced.foreignField(), value);
                }
                result.add(doc);

            }
           
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    
        // <editor-fold defaultstate="collapsed" desc="Document jsonToDocument(String json)">
    /**
     * Convierte un Json a Document
     * @param json
     * @return 
     */
    public static Document jsonToDocument(String json){
        return Document.parse(json.toString());
    }
    // </editor-fold>

    
     // <editor-fold defaultstate="collapsed" desc="Bson createBsonBetweenDateWithoutHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {">
    public static String bsonToJson(Bson filter){
        String json="";
        try {
//            BsonDocument asBsonDocument =filter.toBsonDocument(BsonDocument.class,  MongoClient.getDefaultCodecRegistry());
            BsonDocument asBsonDocument2 =filter.toBsonDocument(BsonDocument.class,  MongoClient.getDefaultCodecRegistry());
json = asBsonDocument.toJson();
        } catch (Exception e) {
            
        }
        return json;
    }
    
 // </editor-fold>
}
