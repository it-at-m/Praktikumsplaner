/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2023
 */
package de.muenchen.oss.praktikumsplaner.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

/**
 * Utilities zu Authentifizierungsdaten.
 *
 * @author michael.prankl
 *
 */
public final class AuthUtils {

    public static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

    private static final String TOKEN_USER_NAME = "user_name";

    private AuthUtils() {
    }

    /**
     * Extrahiert den Usernamen aus dem vorliegenden Spring Security Context via
     * {@link SecurityContextHolder}.
     *
     * @return der Username
     */
    public static String getUsername() {
        if (getAuthentication() instanceof JwtAuthenticationToken jwtAuth) {
            return (String) jwtAuth.getTokenAttributes().getOrDefault(TOKEN_USER_NAME, null);
        } else if (getAuthentication() instanceof UsernamePasswordAuthenticationToken usernameAuth) {
            return usernameAuth.getName();
        } else {
            return NAME_UNAUTHENTICATED_USER;
        }
    }

    /**
     * Extrahiert die Mail Adresse aus dem vorliegenden Spring Security Context via
     * {@link SecurityContextHolder}.
     *
     * @return Mail-Adresse
     */
    public static String getMailFromUser() {

        if (getAuthentication() instanceof JwtAuthenticationToken jwtAuth) {
            final String email = jwtAuth.getToken().getClaimAsString("email");
            if (email == null || email.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing email claim in JWT token");
            }
            return email;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expected JWT authentication");
        }
    }

    /**
     * Prüft ob der User aus dem vorliegenden Spring Security Context via
     * {@link SecurityContextHolder} ein AUSBILDER ist.
     *
     * @return true, wenn User die Rolle AUSBILDER hat, sonst false
     */
    public static boolean isAusbilder() {
        return getAuthentication().getAuthorities()
                .stream()
                .anyMatch(authority -> ("ROLE_" + Authorities.AuthoritiesEnum.AUSBILDER.name()).equals(authority.getAuthority()));
    }

    /**
     * Prüft ob der User aus dem vorliegenden Spring Security Context via
     * {@link SecurityContextHolder} ein AUSBILDUNGSLEITUNG ist.
     *
     * @return true, wenn User die Rolle AUSBILDUNGSLEITUNG hat, sonst false
     */
    public static boolean isAusbildungsleitung() {
        return getAuthentication().getAuthorities()
                .stream()
                .anyMatch(authority -> ("ROLE_" + Authorities.AuthoritiesEnum.AUSBILDUNGSLEITUNG.name()).equals(authority.getAuthority()));
    }

    private static Authentication getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authentication found");
        }
        return authentication;
    }

}
