package com.tobioyelami.authentication.entities;

import javax.persistence.*;

/**
 * Created by toyelami on 30/09/2019
 */
@Entity(name = "oauth_refresh_token")
public class OauthRefreshToken {
    //drop table if exists oauth_refresh_token;
    //create table oauth_refresh_token (
    //token_id VARCHAR(255),
    //token bytea,
    //authentication bytea
    //);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token_id", unique = true)
    private String tokenId;
    @Column(name = "token", length=100000)
    private byte[] token;
    @Column(name = "authentication")
    private String authentication;


    public OauthRefreshToken() {
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
