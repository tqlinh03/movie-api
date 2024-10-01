package com.tqlinh.movie.modal.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ViewRepository extends JpaRepository<View, Integer> {
    @Query("""
        SELECT v
        FROM View v
        JOIN v.users u
        JOIN Episode e ON e.id = v.episode.id
        WHERE u.id = :userId AND e.id = :episodeId
        
""")
    View findViewEpisodeByUserId(@Param("userId") Integer userId, @Param("episodeId") Integer episodeId);
}

