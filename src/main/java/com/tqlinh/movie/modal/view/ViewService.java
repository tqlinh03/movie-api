package com.tqlinh.movie.modal.view;

import com.tqlinh.movie.modal.episode.Episode;
import com.tqlinh.movie.modal.episode.EpisodeRepository;
import com.tqlinh.movie.modal.episode.EpisodeResponse;
import com.tqlinh.movie.modal.movie.Movie;
import com.tqlinh.movie.modal.movie.MovieResponse;
import com.tqlinh.movie.modal.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ViewService {
    public final ViewRepository viewRepository;
    public final EpisodeRepository episodeRepository;
    private final ViewMapper viewMapper;

    public Integer addView(ViewResquest viewResquest, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Episode episode = episodeRepository.findById(viewResquest.episode_id())
                .orElseThrow(() -> new RuntimeException("Episode not found"));
        View view = episode.getView();
        List<User> users = view.getUsers();

        View isExitsUser = viewRepository.findViewEpisodeByUserId(user.getId(), viewResquest.episode_id());
        if (isExitsUser != null) {
            throw new RuntimeException("User already exits in view");
        }
        users.add(user);
        view.setUsers(users);
        return viewRepository.save(view).getId();
    }

    public ViewResponse findById(Integer id) {
        View view = viewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found movieId rate"));
        return viewMapper.toResponse(view);
    }
}
