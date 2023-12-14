package de.muenchen.oss.praktikumsplaner.exception;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testValidationException() {
        ValidationException ex = new ValidationException("Fehlermeldung");
        String response = handler.validationException(ex);
        assertEquals("Fehlermeldung", response);
    }

    @Test
    void testIoException() {
        IOException ex = new IOException("Fehlermeldung");
        String response = handler.ioException(ex);
        assertEquals("Fehlermeldung", response);
    }

    @Test
    void testIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Fehlermeldung");
        String response = handler.IllegalArgumentException(ex);
        assertEquals("Fehlermeldung", response);
    }
}
