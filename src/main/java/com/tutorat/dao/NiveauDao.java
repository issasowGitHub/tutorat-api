package com.tutorat.dao;

import com.tutorat.model.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NiveauDao extends JpaRepository<Niveau, Long> {
}
