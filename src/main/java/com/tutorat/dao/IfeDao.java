package com.tutorat.dao;

import com.tutorat.model.Ife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IfeDao extends JpaRepository<Ife, Long> {
}
