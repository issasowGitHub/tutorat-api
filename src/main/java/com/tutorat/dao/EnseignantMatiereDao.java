package com.tutorat.dao;

import com.tutorat.model.EnseignantMatiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantMatiereDao extends JpaRepository<EnseignantMatiere, Long> {
}
