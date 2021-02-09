package com.worst.movie.service;

import com.worst.movie.model.Movie;
import com.worst.movie.repository.MoveRepository;
import com.worst.producer.model.Producer;
import com.worst.producer.service.ProducerService;
import com.worst.studio.model.Studio;
import com.worst.studio.service.StudioService;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
public class MovieService {

  private final StudioService studioService;
  private final ProducerService producerService;
  private final MoveRepository moveRepository;

  @Autowired
  public MovieService(final StudioService studioService,
      final ProducerService producerService,
      final MoveRepository moveRepository) {

    this.studioService = studioService;
    this.producerService = producerService;
    this.moveRepository = moveRepository;
  }

  @Transactional
  public void createMovie(final MultipartFile csvFile) throws IOException {

    log.log(Level.INFO, "Creating new movies FILE_NAME: {}", csvFile.getOriginalFilename());

    final Reader reader = new InputStreamReader(csvFile.getInputStream());
    final List<CSVRecord> records = CSVFormat.
        EXCEL
        .withDelimiter(';')
        .withFirstRecordAsHeader()
        .parse(reader)
        .getRecords();
    reader.close();

    records.forEach(this::createMovie);

    log.log(Level.INFO, "Movies created FILE_NAME: {}", csvFile.getName());

  }

  private void createMovie(final CSVRecord record) {

    log.log(Level.INFO, "Creating new movie from CSV record. {}", record);

    final String movieTitle = record.get("title").trim();
    final String movieYear = record.get("year").trim();
    final boolean isWinner = record.get("winner").trim().equalsIgnoreCase("yes");

    Movie movie = moveRepository.findByTitleIgnoreCase(movieTitle);

    if (movie != null) {
      log.log(Level.WARN, "Record skipped. Movies already exists. {}", record);
    }

    final Set<Studio> studioSet = studioService.createStudio(record);
    final Set<Producer> producerSet = producerService.createProducer(record);

    movie = Movie.builder()
        .producers(producerSet)
        .studios(studioSet)
        .title(movieTitle)
        .year(movieYear)
        .winner(isWinner)
        .build();

    save(movie);
  }

  private Movie save(final Movie movie) {
    log.log(Level.INFO, "Saving movie, {}", movie);
    return moveRepository.save(movie);
  }

}
