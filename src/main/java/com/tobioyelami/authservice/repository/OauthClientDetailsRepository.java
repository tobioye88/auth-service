package com.tobioyelami.authservice.repository;

import com.tobioyelami.authservice.models.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, Long> {
    OauthClientDetails findOneByClientId(String clientId);

    boolean deleteByClientId(String clientId);
}
