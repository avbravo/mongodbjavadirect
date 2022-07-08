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

/**
 *
 * @author avbravo
 */
@Repository(entity = Oceano.class,jakarta = false)
public interface OceanoRepository {            
    @Query(value="select * from oceano")
    public List<Oceano> findAll();    
    @Query(value="select * from oceano where idoceano = :id")
    public Optional<Oceano> findById(String id);    
    @Query(value="select * from oceano where oceano = :oceano")
    public List<Oceano> findByOceano(String oceano);
    public Oceano save(Oceano oceano);
    public void deleteById(String id);
}
