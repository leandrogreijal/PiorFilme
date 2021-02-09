package com.worst.movie.repository;

import com.worst.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveRepository extends JpaRepository<Movie, Long> {

  Movie findByTitleIgnoreCase(String title);
  
}
