package com.tobioyelami.authservice.config;

import com.tobioyelami.authservice.models.AppAuthority;
import com.tobioyelami.authservice.models.AppUser;
import com.tobioyelami.authservice.models.OauthClientDetails;
import com.tobioyelami.authservice.service.AppAuthoritiesService;
import com.tobioyelami.authservice.service.AppUserService;
import com.tobioyelami.authservice.service.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppAuthoritiesService appAuthoritiesService;

    @Autowired
    @Qualifier("myAuthenticationManager")
    private AuthenticationManager myAuthenticationManager;



    @PostConstruct
    public void addClientIfNotExist(){
        List<ClientDetails> clientDetails = oauthClientDetailsService.listClientDetails();
        List<ClientDetails> collect = clientDetails.stream().filter((clientDetail) -> clientDetail.getClientId().equals("auth-client")).collect(Collectors.toList());
        if(collect.isEmpty()){
            OauthClientDetails clientDetail = new OauthClientDetails();
            clientDetail.setClientSecret("secret");
            clientDetail.setAccessTokenValiditySeconds(36000);
            clientDetail.setRefreshTokenValiditySeconds(36000);
            clientDetail.setClientId("auth-client");
            clientDetail.setAuthorities("ROLE_CLIENT");
            clientDetail.setAuthorizedGrantTypes("implicit,client_credentials,authorization_code,password");
            clientDetail.setClientName("auth-client");
            clientDetail.setScope("profile");
            clientDetail.setResourceIds("auth-service");
            clientDetail.setWebServerRedirectUris("http://localhost:8080/home");
            oauthClientDetailsService.addClientDetails(clientDetail);
        }
    }

    @PostConstruct
    public void addUser(){
        AppUser admin = appUserService.getUserByUsername("admin");
        if(admin == null){
            List<AppAuthority> authorities = addAuthorities();
            addAdminUser(authorities);
            logger.info("Admin Created");
        }else{
            logger.info("Admin already Created");
        }
    }

    private void addAdminUser(List<AppAuthority> authorities) {
        AppUser user = new AppUser();
        user.setUsername("admin");
        user.setPassword("password");
        user.setEmail("admin@email.com");
        user.setActive(true);
        user.setBlocked(false);
        user.setAuthorities(new HashSet<>(authorities));

        appUserService.addUser(user);
    }

    private List<AppAuthority> addAuthorities() {
        List<AppAuthority> authorities = appAuthoritiesService.getAuthoritiesByName(Arrays.asList("Admin", "User"));
        if(authorities == null) {
            List<AppAuthority> newAuthorities = Arrays.asList(
                    new AppAuthority("Admin", "admin", "Admin Authorities"),
                    new AppAuthority("User", "user", "User Authorities"));
            return appAuthoritiesService.addAllAuthorities(newAuthorities);
        }else{
            return authorities;
        }
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(oauthClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).authenticationManager(myAuthenticationManager);
    }
}
