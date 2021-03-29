package com.tutorat.dao;

import com.tutorat.model.BonActivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonActiviteDao extends JpaRepository<BonActivite, Long> {
}
