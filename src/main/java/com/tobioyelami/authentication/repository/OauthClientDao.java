package com.tobioyelami.authentication.repository;

import com.tobioyelami.authentication.entities.OauthClientDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * Created by toyelami on 05/10/2019
 */
@Repository
public class OauthClientDao {
    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void bulkUpdate(OauthClientDetails oauthClientDetails) {

        jdbcTemplate.update("update oauth_client_details set " +
                        "access_token_validity=?, " +
                        "authorities=?, " +
                        "authorized_grant_types=?, " +
                        "autoapprove=?, " +
                        "client_id=?, " +
                        "client_name=?, " +
                        "client_secret=?, " +
                        "refresh_token_validity=?, " +
                        "resource_ids=?, " +
                        "scope=?, " +
                        "web_server_redirect_uri=? " +
                        "where id=?",
                oauthClientDetails.getAccessTokenValiditySeconds(),
                oauthClientDetails.getAuthoritiesAsString(),
                oauthClientDetails.getAuthorizedGrantTypesAsString(),
                oauthClientDetails.getAutoApproveScopes(),
                oauthClientDetails.getClientId(),
                oauthClientDetails.getClientName(),
                oauthClientDetails.getClientSecret(),
                oauthClientDetails.getRefreshTokenValiditySeconds(),
                oauthClientDetails.getResourceIdsAsString(),
                oauthClientDetails.getScopeAsString(),
                oauthClientDetails.getWebServerRedirectUris(),
                oauthClientDetails.getId()
        );

    }

    @Transactional
    public void bulkPersist(OauthClientDetails oauthClientDetails) {

        jdbcTemplate.update("insert into oauth_client_details(" +
                        "access_token_validity," +
                        "authorities," +
                        "authorized_grant_types," +
                        "autoapprove," +
                        "client_id," +
                        "client_name," +
                        "client_secret," +
                        "refresh_token_validity," +
                        "resource_ids," +
                        "scope," +
                        "web_server_redirect_uri) values(?,?,?,?,?,?,?,?,?,?,?)",
                oauthClientDetails.getAccessTokenValiditySeconds(),
                oauthClientDetails.getAuthoritiesAsString(),
                oauthClientDetails.getAuthorizedGrantTypesAsString(),
                oauthClientDetails.getAutoApproveScopes(),
                oauthClientDetails.getClientId(),
                oauthClientDetails.getClientName(),
                oauthClientDetails.getClientSecret(),
                oauthClientDetails.getRefreshTokenValiditySeconds(),
                oauthClientDetails.getResourceIdsAsString(),
                oauthClientDetails.getScopeAsString(),
                oauthClientDetails.getWebServerRedirectUris()
        );

    }
}

