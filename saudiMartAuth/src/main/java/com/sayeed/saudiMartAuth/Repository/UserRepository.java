package com.sayeed.saudiMartAuth.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sayeed.saudiMartAuth.Model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    @Query("SELECT u FROM Users u WHERE u.email = ?1")
    Optional<Users> findByEmail(String email);

    // Using a query to ensure full entity loading
    @Query("SELECT u FROM Users u WHERE u.userId = ?1")
    Optional<Users> findByUserId(String userId);
}
