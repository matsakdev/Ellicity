package com.matsak.ellicity.lighting.security;

import com.matsak.ellicity.lighting.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements OAuth2User, UserDetails {
    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        Authority authority = user.getAuthority();

        Set<Authority> lowerAuthorities = getLowerAuthorities(authority);

        List<GrantedAuthority> authorities = lowerAuthorities
                .stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority(authority.name()));

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    private static Set<Authority> getLowerAuthorities(Authority authority) {
        Set<Authority> lowerAuthorities = new HashSet<>();
        if (authority.equals(Authority.ROLE_ADMIN)) {
            lowerAuthorities.add(Authority.ROLE_MANAGER);
            lowerAuthorities.add(Authority.ROLE_USER);
        }
        if (authority.equals(Authority.ROLE_MANAGER)) {
            lowerAuthorities.add(Authority.ROLE_USER);
        }
        return lowerAuthorities;
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
