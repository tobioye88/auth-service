package com.tobioyelami.authservice.controller;

import com.tobioyelami.authservice.models.AppUser;
import com.tobioyelami.authservice.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("users")
    public List<AppUser> getAllUser(){
        return appUserService.getAllUsers(1, 10);
    }

    @GetMapping("api/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello World";
    }

}
