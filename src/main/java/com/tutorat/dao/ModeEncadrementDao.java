package com.tutorat.dao;

import com.tutorat.model.ModeEncadrement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeEncadrementDao extends JpaRepository<ModeEncadrement, Long> {
}
