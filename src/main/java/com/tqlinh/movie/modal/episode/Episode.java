package com.tqlinh.movie.modal.episode;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.movie.Movie;
import com.tqlinh.movie.modal.view.View;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Episode extends BaseEntity {
    private String title;
    private Integer episodeNumber;
    private LocalDateTime releaseDateTime;
    private String movieUrl;
    private Integer point;
    private LocalDateTime payUntil;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonBackReference
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "view_id", referencedColumnName = "id")
    @JsonManagedReference
    private View view;
}
