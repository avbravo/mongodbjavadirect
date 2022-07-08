/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository;

import com.avbravo.jmoordb.core.annotation.Query;
import com.avbravo.jmoordb.core.annotation.Repository;
import com.avbravo.mongodbatlasdriver.model.Provincia;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@Repository(entity = Provincia.class, jakarta = false)
public interface ProvinciaRepository {
    @Query("select * from provincia")
    public List<Provincia> findAll();
    @Query("select * from provincia where idoais = :id")
    public Optional<Provincia> findById(String id);
    @Query("select * from provincia where provincia = :provincia")
    public List<Provincia> findByProvincia(String provincia);
    public Provincia save(Provincia provincia);
    public void deleteById(String id);
}
