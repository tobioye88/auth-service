package com.tobioyelami.authentication.repository;

import com.tobioyelami.authentication.entities.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by toyelami on 03/10/2019
 */
@Repository
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, Long> {
    Optional<OauthClientDetails> findByClientName(String clientName);

    OauthClientDetails findOneByClientId(String clientId);

    void deleteByClientId(String clientId);

    @Modifying
    @Query("UPDATE OauthClientDetails o SET o.clientSecret = :clientSecret WHERE o.clientId= :clientId")
    void updateClientSecret(@Param("clientSecret") String clientId, @Param("clientId") String secret);

}
