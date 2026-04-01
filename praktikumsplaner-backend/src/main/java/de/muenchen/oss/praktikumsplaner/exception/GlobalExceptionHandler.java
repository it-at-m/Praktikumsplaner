package de.muenchen.oss.praktikumsplaner.exception;

import jakarta.validation.ValidationException;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Is thrown when there is faulty data in the dataset
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String validationException(final ValidationException ex) {
        return ex.getMessage();
    }

    // Is thrown when there is faulty data in the dataset
    @ExceptionHandler(ExcelImportException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String excelImportException(final ExcelImportException ex) {
        final StringBuilder formattedExceptionInfos = new StringBuilder();
        for (final ExcelImportException.ExcelImportExceptionInfo exceptionInfo : ex.getExceptionInfos()) {
            formattedExceptionInfos.append("Zeile: ").append(exceptionInfo.row() + 1).append(" - ")
                    .append("Spalte: ").append(exceptionInfo.columName()).append(" - ")
                    .append("Fehler: ").append(exceptionInfo.value()).append("\n");
        }
        return formattedExceptionInfos.toString();
    }

    // Is thrown when a non Excel file is Imported
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String ioException(final IOException ex) {
        return ex.getMessage();
    }

    // Is thrown when an Excel file that isn't in the correct format is imported
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String illegalArgumentException(final IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(final ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementException(final NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ResourceConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleConflictException(final ResourceConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
