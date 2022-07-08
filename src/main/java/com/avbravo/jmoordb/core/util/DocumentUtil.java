/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.jmoordb.core.util;

import com.avbravo.jmoordb.core.annotation.Referenced;
import java.io.DataInput;
import org.bson.Document;

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
    public static String getIdValue(Document document, Referenced referenced){
        String result="";
        try {
            ConsoleUtil.info(Test.nameOfClassAndMethod()+ " llevo a procesar ");
            Test.msg("Document "+document);
            Test.msg("Referenced "+referenced.localField());
            
            String data = document.get(referenced.from()).toString();            
            Test.msg("data "+data);
            data =data.replace("Document{{", "");
            Test.msg("remove Document{{ "+data);
            data = data.replace("}}", "");           
            Test.msg("remove }} "+data);
            data = data.replace(referenced.foreignField(), "");
            Test.msg("remove foreignFiel "+data);
            data = data.replace("=","");
            Test.msg("remove ="+data);
            result = data.trim();
            Test.msg("result "+result);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + "error: "+e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    
}
