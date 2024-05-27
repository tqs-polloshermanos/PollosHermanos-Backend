package com.github.tqspolloshermanos.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleBadCredentialsException() {
        BadCredentialsException exception = mock(BadCredentialsException.class);
        when(exception.getMessage()).thenReturn("Invalid username or password");
        ProblemDetail problemDetail = exceptionHandler.handleSecurityException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), problemDetail.getStatus());
        assertEquals("Invalid username or password", problemDetail.getDetail());
    }

    @Test
    public void testHandleLockedException() {
        LockedException exception = mock(LockedException.class);
        when(exception.getMessage()).thenReturn("Account is locked");
        ProblemDetail problemDetail = exceptionHandler.handleSecurityException(exception);

        assertEquals(HttpStatus.FORBIDDEN.value(), problemDetail.getStatus());
        assertEquals("Account is locked", problemDetail.getDetail());
    }

    @Test
    public void testHandleUnknownErrorException() {
        Exception exception = mock(Exception.class);
        when(exception.getMessage()).thenReturn("Unknown error");
        ProblemDetail problemDetail = exceptionHandler.handleSecurityException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problemDetail.getStatus());
        assertEquals("Unknown error", problemDetail.getDetail());
        assertEquals("Unknown internal server error.", problemDetail.getProperties().get("description"));
    }

    @Test
    public void testHandleAccessDeniedException() {
        AccessDeniedException exception = mock(AccessDeniedException.class);
        when(exception.getMessage()).thenReturn("Access denied");
        ProblemDetail problemDetail = exceptionHandler.handleSecurityException(exception);

        assertEquals(HttpStatus.FORBIDDEN.value(), problemDetail.getStatus());
        assertEquals("Access denied", problemDetail.getDetail());
    }

    @Test
    public void testHandleSignatureException() {
        SignatureException exception = mock(SignatureException.class);
        when(exception.getMessage()).thenReturn("Invalid JWT signature");
        ProblemDetail problemDetail = exceptionHandler.handleSecurityException(exception);

        assertEquals(HttpStatus.FORBIDDEN.value(), problemDetail.getStatus());
        assertEquals("Invalid JWT signature", problemDetail.getDetail());
    }

    @Test
    public void testHandleExpiredJwtException() {
        ExpiredJwtException exception = mock(ExpiredJwtException.class);
        when(exception.getMessage()).thenReturn("JWT token expired");
        ProblemDetail problemDetail = exceptionHandler.handleSecurityException(exception);

        assertEquals(HttpStatus.FORBIDDEN.value(), problemDetail.getStatus());
        assertEquals("JWT token expired", problemDetail.getDetail());
    }
}
