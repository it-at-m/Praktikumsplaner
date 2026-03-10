package de.muenchen.oss.praktikumsplaner.exception;

@SuppressWarnings("PMD.MissingSerialVersionUID")
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(final String message) {
        super(message);
    }
}
