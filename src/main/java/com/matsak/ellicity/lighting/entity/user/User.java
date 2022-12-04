package com.matsak.ellicity.lighting.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.security.AuthProvider;
import com.matsak.ellicity.lighting.security.Authority;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "[User]", schema = "user_db")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="name")
    private String name;

    @Column(name="imageUrl")
    private String imageUrl;

    @Column(name="emailVerified")
    private boolean emailVerified = false;

    @JsonIgnore
    @Column(name="password")
    private String password;

    @Transient
    List<System> systems;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    private String providerId;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<System> getUserSystems() {
        return systems;
    }

    public void setUserSystems(List<System> userSystems) {
        this.systems = userSystems;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public Long getId() {
        return id;
    }
}
