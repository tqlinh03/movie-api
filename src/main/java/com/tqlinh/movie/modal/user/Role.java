package com.tqlinh.movie.modal.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tqlinh.movie.modal.user.Permisstion.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.EMPTY_SET),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE
            )
    ),
    MANAGER(
            Set.of(
                MANAGER_CREATE,
                MANAGER_DELETE,
                MANAGER_UPDATE,
                MANAGER_READ
            )
    );

    @Getter
    private final Set<Permisstion> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permisstion -> new SimpleGrantedAuthority(permisstion.getPermisstion()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
