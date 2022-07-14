/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository;

import com.avbravo.jmoordb.core.annotation.Count;
import com.avbravo.jmoordb.core.annotation.CountRegex;
import com.avbravo.jmoordb.core.annotation.Query;
import com.avbravo.jmoordb.core.annotation.QueryRegex;
import com.avbravo.jmoordb.core.annotation.Repository;
import com.avbravo.jmoordb.core.annotation.enumerations.ActivatePagination;
import com.avbravo.jmoordb.core.annotation.enumerations.ActivateSort;
import com.avbravo.jmoordb.core.pagination.Pagination;
import com.avbravo.mongodbatlasdriver.model.Oceano;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import com.avbravo.jmoordb.core.annotation.QueryJSON;
import com.avbravo.jmoordb.core.annotation.enumerations.CaseSensitive;
import com.avbravo.jmoordb.core.annotation.enumerations.TypeOrder;

/**
 *
 * @author avbravo
 */
@Repository(entity = Oceano.class, jakarta = false)
public interface OceanoRepository {

    @Query()
    public List<Oceano> findAll();

    @Query(where = "idoceano = :id")
    public Optional<Oceano> findById(String id);

    @Query(where = "oceano = :oceano")
    public List<Oceano> findByOceano(String oceano);
    
    @Query(where = "idoceano = :idoceano and oceano = :oceano")
    public List<Oceano> findByOceano(String idoceano, String oceano);
    
    /**
     * Implementa paginación
     * @param oceano
     * @param pagination
     * @return 
     */
    @Query(where = "oceano = :oceano", activatePagination = ActivatePagination.ON, activateSort = ActivateSort.ON)
    public List<Oceano> findByOceanoPagination(String oceano, Pagination pagination, Document sort);
    

    @QueryJSON(activatePagination = ActivatePagination.ON, activateSort = ActivateSort.ON)
    public List<Oceano> queryJSON(Document filter, Pagination pagination, Document... sort);
    
        @QueryRegex(field = "oceano",activatePagination = ActivatePagination.ON, caseSensitive = CaseSensitive.NO,  typeOrder = TypeOrder.ASC)
     public List<Oceano> findRegex(String value, Pagination pagination);
    
    @Count()
    public Integer count(Document...query);
    
    
    @CountRegex(field = "oceano", caseSensitive = CaseSensitive.NO)
    public Integer countRegex(String value);
    

    public Optional<Oceano> save(Oceano oceano);

    public Boolean update(Oceano oceano);

    public Boolean delete(String id);
}
