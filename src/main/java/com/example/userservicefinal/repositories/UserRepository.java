package com.example.userservicefinal.repositories;

import com.example.userservicefinal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value= "SELECT * from user where email=:emailId", nativeQuery = true)
    Optional<User> findByEmail(@Param("emailId")String email);
    Optional<User> findById(Long userId);
}
