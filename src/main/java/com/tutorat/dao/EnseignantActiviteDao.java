package com.tutorat.dao;

import com.tutorat.model.EnseignantActivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantActiviteDao extends JpaRepository<EnseignantActivite, Long> {
}
