package com.tobioyelami.authservice.repository;

import com.tobioyelami.authservice.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser getByEmail(String email);

    AppUser getByUsername(String username);
}
