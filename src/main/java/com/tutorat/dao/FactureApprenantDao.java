package com.tutorat.dao;

import com.tutorat.model.FactureApprenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureApprenantDao extends JpaRepository<FactureApprenant, Long> {
}
