/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.mongodbatlasdriver.repository;

import com.avbravo.jmoordb.core.annotation.Query;
import com.avbravo.jmoordb.core.annotation.Repository;
import com.avbravo.mongodbatlasdriver.model.Persona;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@Repository(entity = Persona.class, jakarta = false)
public interface PersonaRepository {
    @Query("select * from persona")
    public List<Persona> findAll();
    @Query("select * from persona where idoais = :id")
    public Optional<Persona> findById(String id);
    @Query("select * from persona where persona = :persona")
    public List<Persona> findByPersona(String persona);
    public Persona save(Persona persona);
    public void deleteById(String id);
}
