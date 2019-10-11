package com.tobioyelami.authentication.repository;

import com.tobioyelami.authentication.entities.AppAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by toyelami on 29/09/2019
 */
@Repository
public interface AppAuthoritiesRepository extends JpaRepository<AppAuthorities, Long> {
    Optional<List<AppAuthorities>> findAllByNameIn(List<String> lists);
}
