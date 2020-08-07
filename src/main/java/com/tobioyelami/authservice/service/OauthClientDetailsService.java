package com.tobioyelami.authservice.service;

import com.tobioyelami.authservice.models.OauthClientDetails;
import com.tobioyelami.authservice.repository.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OauthClientDetailsService implements ClientDetailsService, ClientRegistrationService {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        try{
            return oauthClientDetailsRepository.findOneByClientId(clientId);
        }catch (Exception e){
            throw new ClientRegistrationException("Error trying to load client ");
        }
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        try{
            OauthClientDetails oauthClientDetails = (OauthClientDetails) clientDetails;
            OauthClientDetails oneByClientId = oauthClientDetailsRepository.findOneByClientId(oauthClientDetails.getClientId());
            if(oneByClientId != null){
                throw new ClientAlreadyExistsException("Client already exist");
            }
            oauthClientDetails.setClientSecret(passwordEncoder.encode(oauthClientDetails.getClientSecret()));
            oauthClientDetailsRepository.save(oauthClientDetails);
        }catch (Exception e){
            logger.warning("Error Adding Client");
        }
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {

    }

    @Override
    public void updateClientSecret(String s, String s1) throws NoSuchClientException {

    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        try{
            oauthClientDetailsRepository.deleteByClientId(clientId);
        }catch (Exception e){
            logger.warning(e.getMessage());
        }
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        List<OauthClientDetails> oauthClientDetails;
        try {
            oauthClientDetails = oauthClientDetailsRepository.findAll();
            return new ArrayList<>(oauthClientDetails);
        }catch (Exception e){
            logger.warning("Error getting list of Clients");
        }
        return new ArrayList<>();
    }
}
