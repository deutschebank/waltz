package org.finos.waltz.web;

import org.finos.waltz.common.exception.DuplicateKeyException;
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.common.exception.NotFoundException;
import org.finos.waltz.common.exception.UpdateFailedException;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.NoDataFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/not-found")
    public String throwNotFoundException() {
        throw new NotFoundException("ASRM-NF", "Flow Classification Rule not found");
    }

    @GetMapping("/test/no-data-found")
    public String throwNoDataFoundException() {
        throw new NoDataFoundException(" for testing");
    }

    @GetMapping("/test/update-failed")
    public String throwUpdateFailedException() {
        throw new UpdateFailedException("E1"," for testing");
    }
    @GetMapping("/test/duplicate-Key")
    public String throwDuplicateKeyException() {
        throw new DuplicateKeyException(" for testing");
    }

    @GetMapping("/test/dataIntegrity-violation")
    public String throwDataIntegrityViolationException() {
        throw new DataIntegrityViolationException(" for testing");
    }

    @GetMapping("/test/data-access")
    public String throwDataAccessException() {
        throw new DataAccessException(" for testing",new Throwable("for testing"));
    }

    @GetMapping("/test/illegal-argument")
    public String throwIllegalArgumentException() {
        throw new IllegalArgumentException(" for testing");
    }

    @GetMapping("/test/web")
    public String throwWebException() {
        throw new WebException("E1"," for testing");
    }

    @GetMapping("/test/not-authorized")
    public String throwNotAuthorizedException() {
        throw new NotAuthorizedException("Not Authorized Exception for testing");
    }
    @GetMapping("/test/insufficient-privilege")
    public String throwInsufficientPrivilegeException() throws Exception {
        throw new InsufficientPrivelegeException("User cannot modify note");
    }

    @GetMapping("/test/generic-exception")
    public String throwGenericException() throws Exception {
        throw new Exception("Generic Exception");
    }
    @GetMapping("/test/not-found/security")
    @PreAuthorize("hasRole('ADMIN')")
    public String throwIllegalArgumentExceptionWithRole() {
        throw new NotFoundException("ASRM-NF", "Flow Classification Rule not found");
    }

}