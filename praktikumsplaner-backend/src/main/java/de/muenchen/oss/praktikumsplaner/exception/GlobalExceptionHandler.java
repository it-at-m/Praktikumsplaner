package de.muenchen.oss.praktikumsplaner.exception;

import jakarta.validation.ValidationException;
import java.io.IOException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
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
    public String validationException(ValidationException ex) {
        return ex.getMessage();
    }

    // Is thrown when there is faulty data in the dataset
    @ExceptionHandler(ExcelImportException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String ExcelImportException(ExcelImportException ex) {
        return ex.getExceptionInfos().toString();
    }

    // Is thrown when a non Excel file is Imported
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String ioException(IOException ex) {
        return ex.getMessage();
    }

    // Is thrown when an Excel file that isn't in the correct format is imported
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String IllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    // Is thrown when the Database encounters an error
    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String PsqlException(PSQLException ex) {
        return ex.getMessage();
    }
}
