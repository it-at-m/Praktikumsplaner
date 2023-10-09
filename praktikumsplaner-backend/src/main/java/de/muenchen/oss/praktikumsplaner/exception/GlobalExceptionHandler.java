package de.muenchen.oss.praktikumsplaner.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Is thrown when there is a faulty data in the dataset
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String validationException(ValidationException ex) {
        return ex.getMessage();
    }

    // Is thrown when a non Excel File is Imported
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String ioException(IOException ex) {
        return ex.getMessage();
    }

    // Is thrown when an Excel file that is in the right format is imported
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String nullPointerException(NullPointerException ex) {
        return ex.getMessage();
    }
}