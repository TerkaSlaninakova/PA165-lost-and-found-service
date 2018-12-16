package cz.muni.fi.rest.controllers;

import cz.muni.fi.rest.ApiError;
import cz.muni.fi.rest.Exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.rest.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

/**
 *
 * @author Augustin Nemec
 */

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    ApiError handleException(ResourceAlreadyExistingException ex) {
        ApiError apiError = new ApiError();
        apiError.setErrors(Arrays.asList("the requested resource already exists - " + ex.getMessage()));
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ApiError handleException(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.setErrors(Arrays.asList("the requested resource not found - " + ex.getMessage()));
        return apiError;
    }

}
