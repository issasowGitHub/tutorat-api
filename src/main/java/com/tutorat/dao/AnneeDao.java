package com.tutorat.dao;

import com.tutorat.model.Annee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnneeDao extends JpaRepository<Annee, Long> {
}
