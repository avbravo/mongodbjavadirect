/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository;

import com.avbravo.jmoordb.core.annotation.Query;
import com.avbravo.jmoordb.core.annotation.Repository;
import com.avbravo.mongodbatlasdriver.model.Planeta;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@Repository(entity = Planeta.class,jakarta = false)
public interface PlanetaRepository {            
    @Query(value="select * from planeta")
    public List<Planeta> findAll();    
    @Query(value="select * from planeta where idplaneta = :id")
    public Optional<Planeta> findById(String id);    
    @Query(value="select * from planeta where planeta = :planeta")
    public List<Planeta> findByPlaneta(String planeta);
    public Planeta save(Planeta planeta);
    public void deleteById(String id);
}
