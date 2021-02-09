package com.worst.producer.repository;

import com.worst.producer.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

  Producer findByNameIgnoreCase(String name);

}
