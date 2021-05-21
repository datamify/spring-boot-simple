package com.datamify.spring.boot.simple.api;

import com.datamify.spring.boot.simple.api.dto.ErrorDto;
import com.datamify.spring.boot.simple.api.dto.ErrorsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleNotFound(EntityNotFoundException e) {
        log.debug("Entity not found.", e);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorsDto handleConstraintViolations(ConstraintViolationException e) {
        final List<ErrorDto> errors = e.getConstraintViolations()
                .stream()
                .map(v -> ErrorDto.builder()
                        .message(v.getMessage())
                        .path(v.getPropertyPath().toString())
                        .value(v.getInvalidValue())
                        .build())
                .collect(toList());

        return new ErrorsDto(errors);
    }

}
