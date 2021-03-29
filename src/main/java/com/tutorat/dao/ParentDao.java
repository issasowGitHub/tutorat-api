package com.tutorat.dao;

import com.tutorat.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentDao extends JpaRepository<Parent, Long> {
}
