/*
Below Queries taken from :-
https://docs.spring.io/spring-authorization-server/reference/guides/how-to-jpa.html
 */

package com.example.userservicefinal.security.repository;

import java.util.Optional;

import com.example.userservicefinal.security.models.AuthorizationConsent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {
    Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}