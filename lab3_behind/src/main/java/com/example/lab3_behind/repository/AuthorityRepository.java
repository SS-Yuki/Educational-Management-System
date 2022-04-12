package com.example.lab3_behind.repository;

import com.example.lab3_behind.common.AuthorityName;
import com.example.lab3_behind.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthorityName(AuthorityName authorityName);
}
