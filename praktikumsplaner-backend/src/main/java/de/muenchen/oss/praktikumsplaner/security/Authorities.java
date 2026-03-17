package de.muenchen.oss.praktikumsplaner.security;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Each possible authority in this project is represented by a constant in this class.
 * The constants are used within the {@link org.springframework.stereotype.Controller} or
 * {@link org.springframework.stereotype.Service} classes in the method security annotations
 * (e.g. {@link PreAuthorize}).
 */
@SuppressWarnings("PMD.DataClass")
public class Authorities {
    public static final String HAS_ROLE_NWK = "hasRole('NWK')";
    public static final String HAS_ROLE_AUSBILDUNGSLEITUNG = "hasRole('AUSBILDUNGSLEITUNG')";
    public static final String HAS_ANY_ROLE_AUSBILDUNGSLEITUNG_AUSBILDER = "hasAnyRole('AUSBILDUNGSLEITUNG', 'AUSBILDER')";

    public enum AuthoritiesEnum {
        AUSBILDUNGSLEITUNG,
        AUSBILDER,
        NWK
    }

    private Authorities() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
