package com.tobioyelami.authentication.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by toyelami on 06/01/2019
 */
@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    private String roles;
    private boolean isActive;
    private boolean isAccountLocked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<AppAuthorities> authorities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public void setAccountLocked(boolean accountLocked) {
        isAccountLocked = accountLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setAuthorities(Collection<AppAuthorities> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "{\n"+
                "\"username\":\"" + username + "\",\n" +
                "\"roles\":\"" + roles + "\",\n" +
                "\"isActive\":\"" + isActive + ",\n" +
                "\"isAccountLocked\":\"" + isAccountLocked + "\",\n" +
                "\"authorities\":\"" + authorities + "\"\n" +
                "}";
    }
}
