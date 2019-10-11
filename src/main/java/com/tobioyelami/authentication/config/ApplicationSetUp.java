package com.tobioyelami.authentication.config;

import com.tobioyelami.authentication.entities.AppAuthorities;
import com.tobioyelami.authentication.entities.AppUser;
import com.tobioyelami.authentication.entities.OauthClientDetails;
import com.tobioyelami.authentication.repository.AppAuthoritiesRepository;
import com.tobioyelami.authentication.repository.AppUserRepository;
import com.tobioyelami.authentication.repository.OauthClientDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by toyelami on 03/10/2019
 */
@Configuration
public class ApplicationSetUp {
    Logger logger = LoggerFactory.getLogger(ApplicationSetUp.class);

    @Value("${app.client.id}")
    private String clientId;
    @Value("${app.client.name}")
    private String clientName;
    @Value("${app.client.secret}")
    private String clientSecret;
    @Value("${app.client.redirect.uris:http:://localhost:8888}")
    private String redirectUris;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppAuthoritiesRepository appAuthoritiesRepository;


    @PostConstruct
    private void setUp(){
        clientSetUp();
        authoritiesSetUp();
        adminSetUp();
    }

    private void clientSetUp(){
        logger.debug("Setting Up Oauth Client...");
        //add an auth server client
        OauthClientDetails newClientDetails = new OauthClientDetails();

        newClientDetails.setClientId(clientId);
        newClientDetails.setClientSecret(passwordEncoder.encode(clientSecret));
        newClientDetails.setAuthorities("ROLE_CLIENT");
        newClientDetails.setAuthorizedGrantTypes("implicit,client_credentials,authorization_code,password");
        newClientDetails.setClientName(clientName);
        newClientDetails.setResourceIds(clientId);
        newClientDetails.setScope("profile");
        newClientDetails.setWebServerRedirectUris(redirectUris);

        Optional<OauthClientDetails> existingClientOption = oauthClientDetailsRepository.findByClientName(clientName);

        if(!existingClientOption.isPresent()){
            logger.debug("Adding Client");
            oauthClientDetailsRepository.save(newClientDetails);
        }else{
            logger.debug("Client already exist: Updating...");
            OauthClientDetails existingClientDetails = existingClientOption.get();
            newClientDetails.setId(existingClientDetails.getId());
            oauthClientDetailsRepository.save(newClientDetails);
        }

    }

    private void authoritiesSetUp(){
        logger.debug("Setting Up Authorities...");
        List<AppAuthorities> appAuthorities = Arrays.asList(new AppAuthorities("ADMIN", ""), new AppAuthorities("USER", ""));
        Optional<List<AppAuthorities>> optionalAppAuthorities = appAuthoritiesRepository.findAllByNameIn(appAuthorities.stream().map(AppAuthorities::getName).collect(Collectors.toList()));
        if(!optionalAppAuthorities.isPresent()) {
            appAuthoritiesRepository.saveAll(appAuthorities);
        }
    }

    private void adminSetUp(){
        logger.debug("Setting Up Admin...");
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername("admin");
        if(!appUserOptional.isPresent()){
            AppUser admin = new AppUser();
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setUsername("admin");
            admin.setAccountLocked(false);
            admin.setActive(true);
            admin.setAuthorities(appAuthoritiesRepository.findAll());
            admin.setRoles("ROLE_ADMIN");

            appUserRepository.save(admin);
        }

    }
}
