package com.tutorat.dao;

import com.tutorat.model.PaiementEnseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementEnseignantDao extends JpaRepository<PaiementEnseignant, Long> {
}
