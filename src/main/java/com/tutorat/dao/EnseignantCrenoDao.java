package com.tutorat.dao;

import com.tutorat.model.EnseignantCreno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantCrenoDao extends JpaRepository<EnseignantCreno, Long> {
}
