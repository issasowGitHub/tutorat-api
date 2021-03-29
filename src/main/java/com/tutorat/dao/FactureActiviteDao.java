package com.tutorat.dao;

import com.tutorat.model.FactureActivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureActiviteDao extends JpaRepository<FactureActivite, Long> {
}
