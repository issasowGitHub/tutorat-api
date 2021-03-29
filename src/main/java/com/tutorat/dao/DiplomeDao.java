package com.tutorat.dao;

import com.tutorat.model.Diplome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiplomeDao extends JpaRepository<Diplome, Long> {
}
