package com.tobioyelami.authservice.repository;

import com.tobioyelami.authservice.models.AppAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppAuthorityRepository extends JpaRepository<AppAuthority, Long> {
    @Query("SELECT a FROM AppAuthority a WHERE name IN (:names)")
    List<AppAuthority> findByAuthorityNames(@Param("names") List<String> names);
}
