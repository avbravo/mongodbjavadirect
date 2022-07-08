/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.jmoordb.core.util;

import com.avbravo.jmoordb.core.annotation.Referenced;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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
    public static String getIdValue(Document document, Referenced referenced) {
        String result = "";
        try {

            String data = document.get(referenced.from()).toString();
            data = data.replace("Document{{", "");
            data = data.replace("}}", "");
            data = data.replace(referenced.foreignField(), "");
            data = data.replace("=", "");
            result = data.trim();

        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String getIdListValue(Document document, Referenced referenced)">

    /**
     *
     * @param document
     * @param referenced
     * @return Obtiene el valor del id referenciado
     */
    public static List<Document> getIdListValue(Document document, Referenced referenced) {
        List<Document> result = new ArrayList<>();
        try {
            ConsoleUtil.info(Test.nameOfClassAndMethod() + " llevo a procesar la lista");
            Test.msg("Document " + document);
            Test.msg("Referenced " + referenced.localField());

            String data = document.get(referenced.from()).toString();
            Test.msg("data " + data);
            data = data.replace("Document{{", "");
            Test.msg("remove Document{{ " + data);
            data = data.replace("}}", "");
            Test.msg("remove }} " + data);
            data = data.replace(referenced.foreignField(), "");
            Test.msg("remove foreignFiel " + data);
            data = data.replace("=", "");
            Test.msg("remove =" + data);
            data = data.replace("[", "");
            Test.msg("remove [" + data);
            data = data.trim();
            data = data.replace("[", "");
            Test.msg("remove [" + data);
            data = data.replace("]", "");
            Test.msg("remove ]" + data);
            data = data.trim();
            StringTokenizer st = new StringTokenizer(data, ",");
            while (st.hasMoreTokens()) {
                Document doc = new Document();
                if (referenced.typeFieldkeyString()) {
                    doc.append(referenced.foreignField(), st.nextToken().trim());
                } else {
                    Integer value = Integer.parseInt(st.nextToken().trim());
                    doc.append(referenced.foreignField(), value);
                }
                result.add(doc);

            }
            Test.msg("result " + result);
        } catch (Exception e) {
            Test.error(Test.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

}
