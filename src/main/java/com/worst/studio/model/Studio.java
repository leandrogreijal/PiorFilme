package com.worst.studio.model;

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
public class Studio {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "name", unique = true, nullable = true)
  private String name;
  @ManyToMany(mappedBy = "studios")
  private Set<Movie> movies;

  public Studio(final String name) {
    super();
    this.name = name;
  }


}
