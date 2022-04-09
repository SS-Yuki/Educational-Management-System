package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByAccount(String account);
}
