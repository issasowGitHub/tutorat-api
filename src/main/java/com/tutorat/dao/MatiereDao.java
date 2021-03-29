package com.tutorat.dao;

import com.tutorat.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatiereDao extends JpaRepository<Matiere, Long> {
}
