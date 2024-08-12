package com.tqlinh.movie.modal.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.episode.Episode;
import com.tqlinh.movie.modal.genre.Genre;
import com.tqlinh.movie.modal.user.User;
import com.tqlinh.movie.modal.watchlist.Watchlist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Movie extends BaseEntity {
    private String title;
    private String description;
    private String thumbnailUrl;
    private String director;
    private String _cast;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;


    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnoreProperties("users")
    private List<Genre> genre;

//    @ManyToMany
//    @JoinTable(
//            name = "movie_watchlists",
//            joinColumns = @JoinColumn(name = "movie_id"),
//            inverseJoinColumns = @JoinColumn(name = "watchlist_id")
//
//    )
//    @JsonIgnoreProperties("movies")
//    private List<Watchlist> ;

    @ManyToMany(mappedBy = "movies")
    @JsonIgnoreProperties("Watchlist")
    private List<Watchlist> watchlists;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private List<Episode> episodes;
}
