package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun.security.util.Password;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByAccount(String account);

}
