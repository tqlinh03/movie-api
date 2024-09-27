package com.tqlinh.movie.modal.user;

//import com.tqlinh.movieId.modal.role.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tqlinh.movie.modal.checkinLog.CheckinLog;
import com.tqlinh.movie.modal.episodeAccess.EpisodeAccess;
import com.tqlinh.movie.modal.movie.Movie;
import com.tqlinh.movie.modal.point.Point;
import com.tqlinh.movie.modal.token.Token;
import com.tqlinh.movie.modal.vip.Vip;
import com.tqlinh.movie.modal.watchlist.Watchlist;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Integer streak;
    private boolean accountLocked;
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<Token> token;

    @OneToOne()
    @JoinColumn(name = "point_id", referencedColumnName = "id")
    private Point point;

    @OneToOne()
    @JoinColumn(name = "episodeAccess_id", referencedColumnName = "id")
    private EpisodeAccess episodeAccess;

    @OneToOne()
    @JoinColumn(name = "vip_id", referencedColumnName = "id")
    private Vip vip;

    @OneToMany(mappedBy = "owner")
    private List<Movie> movie;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne()
    @JoinColumn(name = "watchlist_id", referencedColumnName = "id")
    private Watchlist watchlist;

    @OneToMany(mappedBy = "user")
    private List<CheckinLog> checkinLogs;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return lastName;
    }
}
