package com.tqlinh.movie.modal.watchlist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.movie.Movie;
import com.tqlinh.movie.modal.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Watchlist {
    @Id
    @GeneratedValue
    private Integer id;
//
//    @ManyToMany(mappedBy = "watchlists")
//    @JsonIgnoreProperties("watchlists")
//    private List<Movie> movies;

    @ManyToMany
    @JoinTable(
            name = "watchlist_movie",
            joinColumns = @JoinColumn(name = "watchlist_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")

    )
    @JsonIgnoreProperties("watchlists")
    private List<Movie> movies;
}
