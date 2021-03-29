package com.tutorat.dao;

import com.tutorat.model.Apprenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprenantDao extends JpaRepository<Apprenant, Long> {
}
