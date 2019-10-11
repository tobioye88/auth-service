package com.tobioyelami.authentication.config;

import com.tobioyelami.authentication.entities.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by toyelami on 04/10/2019
 */
public class AppTokenEnhancer implements TokenEnhancer {
    Logger logger = LoggerFactory.getLogger(AppTokenEnhancer.class);

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        AppUser user = (AppUser) authentication.getUserAuthentication().getPrincipal();
        logger.debug(user.toString());
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("username", user.getUsername());
        additionalInfo.put("roles", user.getRoles());
        additionalInfo.put("isActive", user.isEnabled());
        additionalInfo.put("isAccountLocked", user.isAccountNonLocked());
        additionalInfo.put("authorities", user.getAuthorities().toString());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
}