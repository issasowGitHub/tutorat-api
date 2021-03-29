package com.tutorat.dao;

import com.tutorat.model.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantDao extends JpaRepository<Enseignant, Long> {
}
