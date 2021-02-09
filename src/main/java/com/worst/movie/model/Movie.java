package com.worst.movie.model;

import com.worst.producer.model.Producer;
import com.worst.studio.model.Studio;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "year", nullable = false)
  private String year;
  private boolean winner;

  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      name = "movies_studio",
      joinColumns = {@JoinColumn(name = "movie_id")},
      inverseJoinColumns = {@JoinColumn(name = "studio_id")}
  )
  private Set<Studio> studios;

  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      name = "movies_producer",
      joinColumns = {@JoinColumn(name = "movie_id")},
      inverseJoinColumns = {@JoinColumn(name = "producer_id")}
  )
  private Set<Producer> producers;


}
