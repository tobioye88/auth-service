package com.tobioyelami.authentication.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * Created by toyelami on 29/09/2019
 */
@Entity(name = "app_authorities")
public class AppAuthorities implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    @JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<AppUser> appUsers;

    public AppAuthorities() { }

    public AppAuthorities(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return name != null ? name.toLowerCase() : null;
    }

    public List<AppUser> getAppUser() {
        return appUsers;
    }

    public void setAppUser(List<AppUser> appUser) {
        this.appUsers = appUser;
    }

    @Override
    public String toString() {
        return "{\n"+
                "\"name\":\"" + name + "\",\n" +
                "\"description\":\"" + description + "\"\n" +
                "}";
    }
}
