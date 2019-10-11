package com.tobioyelami.authentication.controller;

import com.tobioyelami.authentication.entities.AppUser;
import com.tobioyelami.authentication.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by toyelami on 06/01/2019
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public AppUser signup(@RequestBody AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @GetMapping
    public List<AppUser> getAll(){
        return appUserRepository.findAll();
    }
}
