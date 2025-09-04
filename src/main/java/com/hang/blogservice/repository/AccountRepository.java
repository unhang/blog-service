package com.hang.blogservice.repository;

import com.hang.blogservice.enity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmailAndIdNot(String email, UUID id);
    Optional<Account> findByEmail(String email);
}

//package com.pm.patientservice.repository;
//
//import com.pm.patientservice.entity.Patient;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.UUID;
//
//@Repository
//public interface PatientRepository extends JpaRepository<Patient, UUID> {
//    boolean existsByEmail(String email);
//    boolean existsByEmailAndIdNot(String email, UUID id);
//}
