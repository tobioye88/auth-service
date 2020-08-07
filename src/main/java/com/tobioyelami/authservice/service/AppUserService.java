package com.tobioyelami.authservice.service;

import com.tobioyelami.authservice.models.AppAuthority;
import com.tobioyelami.authservice.models.AppUser;
import com.tobioyelami.authservice.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //add user
    //block user
    //get user by email
    //get user by id
    //get user by username
    //get all users paged
    //count all users
    //add authorities

    public AppUser addUser(AppUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public boolean blockUser(AppUser user){
        user.setBlocked(true);
        return repository.save(user) != null;
    }

    public AppUser getUserByEmail(String email){
        return repository.getByEmail(email);
    }

    public AppUser getUserById(Long id){
        return repository.getOne(id);
    }

    public AppUser getUserByUsername(String username){
        return repository.getByUsername(username);
    }

    public List<AppUser> getAllUsers(int start, int size){
        List<AppUser> all = repository.findAll();
//        if(!all.isEmpty()){
//            return all.getContent();
//        }else {
//            return Collections.emptyList();
//        }
        return all;
    }

    public AppUser addAuthoritiesToUser(AppUser user, List<AppAuthority> appAuthorities){
        user.setAuthorities(new HashSet<>(appAuthorities));
        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}
