package com.tobioyelami.authentication.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by toyelami on 30/09/2019
 */
@Entity(name = "oauth_code")
public class OauthCode {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "authentication")
    private String authentication;

    public OauthCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
