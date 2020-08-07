package com.tobioyelami.authservice.models;
import javax.persistence.*;

@Entity(name = "oauth_access_token")
public class OauthAccessToken {
    //drop table if exists oauth_access_token;
    //        create table oauth_access_token (
    //        token_id VARCHAR(255),
    //        token bytea,
    //        authentication_id VARCHAR(255),
    //        user_name VARCHAR(255),
    //        client_id VARCHAR(255),
    //        authentication bytea,
    //        refresh_token VARCHAR(255)
    //        );
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "token_id", unique = true)
    private String tokenId;
    @Lob
    @Column(name = "token", length = 100000)
    private byte[] token;
    @Column(name = "authentication_id")
    private String authenticationId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "client_id")
    private String clientId;
    @Lob
    @Column(name = "authentication", length = 100000)
    private byte[] authentication;
    @Column(name = "refresh_token")
    private String refreshToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}