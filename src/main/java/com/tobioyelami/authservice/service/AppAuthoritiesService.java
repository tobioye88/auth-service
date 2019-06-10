package com.tobioyelami.authservice.service;

import com.tobioyelami.authservice.models.AppAuthority;
import com.tobioyelami.authservice.repository.AppAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppAuthoritiesService {

    //add Authorities
    //add All Auth
    //delete Auth
    //get Auth

    @Autowired
    private AppAuthorityRepository appAuthorityReposiory;

    public AppAuthority addAuthority(AppAuthority appAuthority){
        return appAuthorityReposiory.save(appAuthority);
    }

    public List<AppAuthority> addAllAuthorities(List<AppAuthority> authorities){
        return appAuthorityReposiory.saveAll(authorities);
    }

    public boolean deleteAuthority(AppAuthority appAuthority){
        appAuthorityReposiory.delete(appAuthority);
        return !appAuthorityReposiory.findOne(Example.of(appAuthority)).isPresent();
    }

    public AppAuthority getAuthorities(Long appAuthorityId){
        Optional<AppAuthority> optional = appAuthorityReposiory.findById(appAuthorityId);
        return optional.orElse(null);
    }

    public List<AppAuthority> getAuthoritiesByName(List<String> names){
        return appAuthorityReposiory.findByAuthorityNames(names);
    }
}
