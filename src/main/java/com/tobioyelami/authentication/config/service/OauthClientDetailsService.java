package com.tobioyelami.authentication.config.service;

import com.tobioyelami.authentication.entities.OauthClientDetails;
import com.tobioyelami.authentication.repository.OauthClientDao;
import com.tobioyelami.authentication.repository.OauthClientDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.provider.*;


/**
 * Created by toyelami on 04/10/2019
 */
@Service
public class OauthClientDetailsService implements ClientDetailsService,
        ClientRegistrationService {
    Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    OauthClientDetailsRepository oauthClientDetailRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OauthClientDao oauthClientDao;

    @Override
    public void addClientDetails(ClientDetails client) {
        try {
            OauthClientDetails clientData = (OauthClientDetails) client;
            OauthClientDetails oauthClientDetails = oauthClientDetailRepository.findOneByClientId(client.getClientId());
            if (oauthClientDetails != null) {
                throw new ClientAlreadyExistsException("Client already exists");
            }
            clientData.setClientSecret(passwordEncoder.encode(clientData.getClientSecret()));
            oauthClientDao.bulkPersist(clientData);
        } catch (Exception e) {
            logger.error("error----", e);
        }
    }

    @Override
    public List<ClientDetails> listClientDetails() {

        List<OauthClientDetails> cosmosOAuthClients = null;

        cosmosOAuthClients = oauthClientDetailRepository.findAll();
        List<ClientDetails> details = new ArrayList<>();
        details.addAll(cosmosOAuthClients);
        return details;
    }

    @Override
    public void removeClientDetails(String clientId) {
        oauthClientDetailRepository.deleteByClientId(clientId);
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) {

        OauthClientDetails oauthClientDetails = (OauthClientDetails) clientDetails;
        OauthClientDetails existingDetails = (OauthClientDetails) loadClientByClientId(oauthClientDetails.getClientId());

        //load id of existing client details so object will be updated
        if (existingDetails != null) {
            oauthClientDetails.setId(existingDetails.getId());
        } else {
            logger.error("Unable to update existing Client details. Client ID not found");
            return;
        }

        oauthClientDetails.setClientSecret(passwordEncoder.encode(oauthClientDetails.getClientSecret()));

        oauthClientDao.bulkUpdate(oauthClientDetails);

    }

    @Override
    public void updateClientSecret(String clientId, String clientSecret) {
        String hashedSecret = passwordEncoder.encode(clientSecret);
        oauthClientDetailRepository.updateClientSecret(clientId, hashedSecret);

    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return oauthClientDetailRepository.findOneByClientId(clientId);
    }

}

