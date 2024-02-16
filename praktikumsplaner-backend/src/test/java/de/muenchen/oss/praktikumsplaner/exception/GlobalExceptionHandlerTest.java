package de.muenchen.oss.praktikumsplaner.exception;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void testValidationException() {
        ValidationException ex = new ValidationException("Fehlermeldung");
        String response = handler.validationException(ex);
        assertEquals("Fehlermeldung", response);
    }

    @Test
    public void testExcelImportException() {
        ExcelImportException.ExcelImportExceptionInfo exceptionInfo = new ExcelImportException.ExcelImportExceptionInfo(1, "ColumnName", "InvalidValue");
        ExcelImportException excelImportException = new ExcelImportException(List.of(exceptionInfo));
        String response = handler.ExcelImportException(excelImportException);
        String formattedExceptionInfos = "Zeile: " + (exceptionInfo.getRow() + 1)  + " - Spalte: " + exceptionInfo.getColumName() + " - Fehler: " + exceptionInfo.getValue() + "\n";
        assertEquals(formattedExceptionInfos, response);
    }

    @Test
    public void testIoException() {
        IOException ex = new IOException("Fehlermeldung");
        String response = handler.ioException(ex);
        assertEquals("Fehlermeldung", response);
    }

    @Test
    public void testIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Fehlermeldung");
        String response = handler.IllegalArgumentException(ex);
        assertEquals("Fehlermeldung", response);
    }

    @Test
    public void testHandleNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Fehlermeldung");
        ResponseEntity<String> response = handler.handleNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Fehlermeldung", response.getBody());
    }

    @Test
    public void testHandleNoSuchElementException() {
        NoSuchElementException ex = new NoSuchElementException("Fehlermeldung");
        ResponseEntity<String> response = handler.handleNoSuchElementException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Fehlermeldung", response.getBody());
    }

    @Test
    public void testHandleConflictException() {
        ResourceConflictException ex = new ResourceConflictException("Fehlermeldung");
        ResponseEntity<String> response = handler.handleConflictException(ex);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Fehlermeldung", response.getBody());
    }
}
