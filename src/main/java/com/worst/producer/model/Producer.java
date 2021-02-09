package com.worst.producer.model;

import com.worst.movie.model.Movie;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Producer {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "name", unique = true, nullable = true)
  private String name;
  @ManyToMany(mappedBy = "producers")
  private Set<Movie> movies;


  public Producer(final String name) {
    super();
    this.name = name;
  }
}
