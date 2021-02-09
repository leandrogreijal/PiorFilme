package com.worst.studio.service;

import com.worst.studio.model.Studio;
import com.worst.studio.repository.StudioRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class StudioService {

  private final StudioRepository studioRepository;

  @Autowired
  public StudioService(final StudioRepository studioRepository) {
    this.studioRepository = studioRepository;
  }

  @Transactional
  public Set<Studio> createStudio(final CSVRecord record) {

    log.log(Level.INFO, "Creating new studio from CSV record. {}", record);

    final Set<Studio> studioSet = new HashSet<>();
    final String studiosStr = record.get("studios");
    final List<String> studiosStrList = Arrays.asList(studiosStr.split(","));

    for (final String name : studiosStrList) {

      if (StringUtils.isBlank(name)) {
        continue;
      }

      final Studio studio = findByName(name);

      studioSet.add(studio);
    }

    return studioSet;
  }

  private Studio save(final Studio studio) {
    log.log(Level.INFO, "Saving studio. {}", studio);
    return studioRepository.save(studio);
  }

  private Studio findByName(final String name) {
    return studioRepository.findByNameIgnoreCase(name.trim());
  }

}
