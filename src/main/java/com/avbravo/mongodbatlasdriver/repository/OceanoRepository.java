/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository;

import com.avbravo.jmoordb.core.annotation.Query;
import com.avbravo.jmoordb.core.annotation.Repository;
import com.avbravo.mongodbatlasdriver.model.Oceano;
import java.util.List;
import java.util.Optional;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
@Repository(entity = Oceano.class, jakarta = false)
public interface OceanoRepository {

    @Query(value = "select * from oceano")
    public List<Oceano> findAll();

    @Query(value = "select * from oceano where idoceano = :id")
    public Optional<Oceano> findById(String id);

    @Query(value = "select * from oceano where oceano = :oceano")
    public List<Oceano> findByOceano(String oceano);

    @Query(value = "select * from oceano where filter := filter pageNumber :=pageNumber rowsForPage := rowsForPage sort :=docSort")
    public List<Oceano> jsonQuery(Document filter, Integer pageNumber, Integer rowsForPage, Document... docSort);

    public Optional<Oceano> save(Oceano oceano);

    public Boolean update(Oceano oceano);

    public Boolean delete(String id);
}
