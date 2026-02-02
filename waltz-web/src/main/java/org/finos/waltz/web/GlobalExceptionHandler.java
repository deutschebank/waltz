package org.finos.waltz.web;

import org.finos.waltz.common.exception.DuplicateKeyException;
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.common.exception.NotFoundException;
import org.finos.waltz.common.exception.UpdateFailedException;
import org.finos.waltz.model.ImmutableWebError;
import org.finos.waltz.model.WebError;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.NoDataFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<WebError> notFoundExceptionException(NotFoundException e) {
        String message = "Not found exception" + e.getMessage();
        LOG.error(message, e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(message)
                .id(e.getCode())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(webError);
    }

    @ExceptionHandler(NoDataFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<WebError> noDataFoundException(NoDataFoundException e) {
        String message = "Not found exception" + e.getMessage();
        LOG.error(message, e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(message)
                .id("NO_DATA")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(webError);
    }

    @ExceptionHandler(UpdateFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WebError> updateFailedException(UpdateFailedException e) {
        String message = "Update failed exception:" + e.getMessage();
        LOG.error(message, e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(message)
                .id(e.getCode())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(webError);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<WebError> duplicateKeyException(DuplicateKeyException e) {
        String message = "Duplicate detected: " + e.getMessage();
        LOG.error(message, e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(message)
                .id("DUPLICATE")
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(webError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<WebError> dataIntegrityViolationException(DataIntegrityViolationException e) {
        String message = "Data integrity violation detected: " + e.getMessage();
        LOG.error(message, e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(message)
                .id("DATA_INTEGRITY")
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(webError);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WebError> dataAccessException(DataAccessException e) {
        String message = format("Data Access Exception: %s [%s]", e.getCause(), e.getClass().getName());
        LOG.error(message, e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(message)
                .id(e.sqlState())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(webError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WebError> illegalArgumentException(IllegalArgumentException e) {
        String message = "Illegal Argument Exception: " + e.getMessage();
        LOG.error(message, e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(message)
                .id("ILLEGAL ARGUMENT")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(webError);
    }

    @ExceptionHandler(WebException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<WebError> webException(WebException e) {
        String message = "Web exception: " + e.getMessage();
        LOG.error(message, e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(message)
                .id(e.getCode())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(webError);
    }

    @ExceptionHandler({ AuthorizationDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<WebError> authorizationDeniedException(AuthorizationDeniedException e) {
        LOG.error("Access denied: ", e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message("You do not have permission to perform that operation")
                .id("NOT_AUTHORIZED")
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(webError);
    }

    @ExceptionHandler({NotAuthorizedException.class,InsufficientPrivelegeException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<WebError> accessException(Exception e) {
        LOG.error("Access denied", e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(e.getMessage())
                .id("NOT_AUTHORIZED")
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(webError);
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<WebError> genericException(Exception e) {
        String message = "Generic Exception: " + e.getMessage() + " / " + e.getClass().getCanonicalName();
        LOG.error(message, e);
        ImmutableWebError webError = ImmutableWebError.builder()
                .message(message)
                .id("unknown")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(webError);
    }

}
