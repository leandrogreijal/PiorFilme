package com.worst.studio.repository;

import com.worst.studio.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

  Studio findByNameIgnoreCase(String name);
}
