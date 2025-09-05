package com.hang.blogservice.repository;

import com.hang.blogservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmailAndIdNot(String email, UUID id);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsernameOrEmail(String username, String email);

    @Query("SELECT a FROM Account a WHERE a.isActive = true AND (a.username = :identifier OR a.email = :identifier)")
    Optional<Account> findActiveAccountByUsernameOrEmail(String identifier);

}

