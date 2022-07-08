/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository;

import com.avbravo.jmoordb.core.annotation.Query;
import com.avbravo.jmoordb.core.annotation.Repository;
import com.avbravo.mongodbatlasdriver.model.Pais;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@Repository(entity = Pais.class, jakarta = false)
public interface PaisRepository {
    @Query("select * from pais")
    public List<Pais> findAll();
    @Query("select * from pais where idoais = :id")
    public Optional<Pais> findById(String id);
    @Query("select * from pais where pais = :pais")
    public List<Pais> findByPais(String pais);
    public Pais save(Pais pais);
    public void deleteById(String id);
}
