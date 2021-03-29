package com.tutorat.dao;

import com.tutorat.model.Jour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourDao extends JpaRepository<Jour, Long> {
}
