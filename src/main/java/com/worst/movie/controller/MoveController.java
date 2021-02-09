package com.worst.movie.controller;


import com.worst.movie.service.MovieService;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
public class MoveController {

  private final MovieService movieService;

  @Autowired
  public MoveController(final MovieService movieService) {
    this.movieService = movieService;
  }

  @PostMapping(value = "/move/csv", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity save(@RequestParam(value = "file", required = true) final MultipartFile multipartFile) {

    try {
      movieService.createMovie(multipartFile);

    } catch (final IOException e) {

      log.log(Level.ERROR, "Error create Movie.", e);

      ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    return ResponseEntity
        .status(HttpStatus.CREATED).build();
  }

}
