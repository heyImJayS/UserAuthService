/*
Below Queries taken from :-
https://docs.spring.io/spring-authorization-server/reference/guides/how-to-jpa.html
 */
package com.example.userservicefinal.security.repository;

import java.util.Optional;
import com.example.userservicefinal.security.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}