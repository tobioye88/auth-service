package com.tobioyelami.authentication.service;

import com.tobioyelami.authentication.repository.AppUserRepository;
import com.tobioyelami.authentication.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by toyelami on 07/01/2019
 */
@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    AppUserRepository appUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
        if(appUserOptional.isPresent())
            return appUserOptional.get();
        else
            throw new UsernameNotFoundException("AppUser not found");
    }

    public UserDetails loadUserById(Long userId) {
        return  appUserRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("AppUser not found"));
    }
}
