package com.tqlinh.movie.modal.episodeAccess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tqlinh.movie.modal.episode.Episode;
import com.tqlinh.movie.modal.genre.Genre;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EpisodeAccess {
    @Id
    @GeneratedValue
    private Integer id;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "episodeAccess_episodess",
            joinColumns = @JoinColumn(name = "episodeAccess_id"),
            inverseJoinColumns = @JoinColumn(name = "episode_id"))
    @JsonIgnoreProperties("episodeAccess")
    private List<Episode> episodes;

}
