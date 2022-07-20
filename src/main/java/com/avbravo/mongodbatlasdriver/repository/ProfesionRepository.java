/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository;

import com.avbravo.jmoordb.core.annotation.Query;
import com.avbravo.jmoordb.core.annotation.RepositoryMongoDB;
import com.avbravo.mongodbatlasdriver.model.Profesion;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@RepositoryMongoDB(entity = Profesion.class, jakarta = false)
public interface ProfesionRepository {
    @Query()
    public List<Profesion> findAll();
    @Query(where ="idoais = :id")
    public Optional<Profesion> findById(String id);
    @Query(where = "profesion = :profesion")
    public List<Profesion> findByProfesion(String profesion);
    public Profesion save(Profesion profesion);
    public Boolean deleteById(String id);
}
