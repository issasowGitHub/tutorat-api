package com.tutorat.dao;

import com.tutorat.model.ApprenantAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprenantAbonnementDao extends JpaRepository<ApprenantAbonnement, Long> {
}
