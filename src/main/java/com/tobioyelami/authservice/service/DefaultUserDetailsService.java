package com.tobioyelami.authservice.service;

import com.tobioyelami.authservice.models.AppUser;
import com.tobioyelami.authservice.models.AppUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserService.getUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Username not Found");
        }
        return new AppUserPrincipal(user);
    }


////
////    private List<SimpleGrantedAuthority> getAuthority() {
////        return Collections.emptyList();
////    }
}
