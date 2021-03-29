package com.tutorat.dao;

import com.tutorat.model.TypeEncadrement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeEncadrementDao extends JpaRepository<TypeEncadrement, Long> {
}
