package com.tutorat.dao;

import com.tutorat.model.ApprenantActivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprenantActiviteDao extends JpaRepository<ApprenantActivite, Long> {
}
