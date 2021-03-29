package com.tutorat.dao;

import com.tutorat.model.EnseignantBon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantBonDao extends JpaRepository<EnseignantBon, Long> {
}
