package com.tutorat.dao;

import com.tutorat.model.PaiementApprenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementApprenantDao extends JpaRepository<PaiementApprenant, Long> {
}
