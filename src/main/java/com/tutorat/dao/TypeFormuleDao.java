package com.tutorat.dao;

import com.tutorat.model.TypeFormule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFormuleDao extends JpaRepository<TypeFormule, Long> {
}
