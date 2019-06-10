package com.tobioyelami.authservice.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AppUserPrincipal implements UserDetails {

    private AppUser user;

    public AppUserPrincipal() {
    }

    public AppUserPrincipal(AppUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return  user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return  user.isActive();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
