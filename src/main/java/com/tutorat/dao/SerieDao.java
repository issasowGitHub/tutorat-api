package com.tutorat.dao;

import com.tutorat.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieDao extends JpaRepository<Serie, Long> {
}
