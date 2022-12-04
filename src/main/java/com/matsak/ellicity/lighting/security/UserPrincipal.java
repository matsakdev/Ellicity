package com.matsak.ellicity.lighting.security;

import com.matsak.ellicity.lighting.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

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
        Set<GrantedAuthority> authorities = getAuthorities(user);

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }


    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    private static Set<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> userAuthorities = new HashSet<>();
        userAuthorities.add(new SimpleGrantedAuthority(user.getAuthority().name()));
        Authority[] systemAuthorities = Authority.values();
        findUserAuthorities(user, userAuthorities, systemAuthorities);
        return userAuthorities;
    }

    private static void findUserAuthorities(User user, Set<GrantedAuthority> userAuthorities, Authority[] systemAuthorities) {
        for (int i = systemAuthorities.length - 1; i >= 0; i--) {
            if (user.getAuthority().equals(systemAuthorities[i])) {
                fillUserAuthorities(i, userAuthorities, systemAuthorities);
                break;
            }
        }
    }

    private static void fillUserAuthorities(int index, Set<GrantedAuthority> userAuthorities, Authority[] systemAuthorities) {
        for (int j = 0; j <= index; j++) {
            userAuthorities.add(new SimpleGrantedAuthority(systemAuthorities[j].name()));
        }
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
