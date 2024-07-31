package com.tqlinh.movie.modal.user;

//import com.tqlinh.movie.modal.role.Role;
import com.tqlinh.movie.modal.point.Point;
import com.tqlinh.movie.modal.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean accountLocked;
    private Boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<Token> token;
    @OneToOne()
    @JoinColumn(name = "point_id", referencedColumnName = "id")
    private Point point;


    @Enumerated(EnumType.STRING)
    private Role role;

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

    public String getUserEmail() {
        return email;
    }

    public String fullName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
