package com.worst.producer.service;

import com.worst.producer.model.Producer;
import com.worst.producer.repository.ProducerRepository;
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
public class ProducerService {

  private final ProducerRepository producerRepository;

  @Autowired
  public ProducerService(final ProducerRepository producerRepository) {
    this.producerRepository = producerRepository;
  }

  @Transactional
  public Set<Producer> createProducer(final CSVRecord record) {

    log.log(Level.INFO, "Creating new producer from CSV record. {}", record);

    final Set<Producer> producerSet = new HashSet<>();
    final String studiosStr = record.get("producers");
    final String studiosStrReplace = studiosStr.replace(" and ", ",");
    final List<String> studiosStrList = Arrays.asList(studiosStrReplace.split(","));

    for (final String name : studiosStrList) {

      if (StringUtils.isBlank(name)) {
        continue;
      }

      Producer producer = findByName(name);

      if (producer == null) {
        producer = save(new Producer(name.trim()));
      }
      producerSet.add(producer);
    }

    return producerSet;
  }

  private Producer save(final Producer producer) {
    log.log(Level.INFO, "Saving producer. {}", producer);
    return producerRepository.save(producer);
  }

  private Producer findByName(final String name) {
    return producerRepository.findByNameIgnoreCase(name.trim());
  }
}
