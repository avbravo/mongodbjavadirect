/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository;

import com.avbravo.jmoordb.core.annotation.Query;
import com.avbravo.jmoordb.core.annotation.Repository;
import com.avbravo.mongodbatlasdriver.model.Corregimiento;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@Repository(entity = Corregimiento.class, jakarta = false)
public interface CorregimientoRepository {
    @Query()
    public List<Corregimiento> findAll();
    @Query(where="idoais = :id")
    public Optional<Corregimiento> findById(String id);
    @Query(where="corregimiento = :corregimiento")
    public List<Corregimiento> findByCorregimiento(String corregimiento);
    public Corregimiento save(Corregimiento corregimiento);
    public void deleteById(String id);
}
