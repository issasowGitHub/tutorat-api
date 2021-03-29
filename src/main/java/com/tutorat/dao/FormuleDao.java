package com.tutorat.dao;

import com.tutorat.model.Formule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormuleDao extends JpaRepository<Formule, Long> {
}
