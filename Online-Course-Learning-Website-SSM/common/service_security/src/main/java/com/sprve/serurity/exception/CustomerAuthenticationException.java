package com.sprve.serurity.exception;
import org.springframework.security.core.AuthenticationException;
public class CustomerAuthenticationException extends AuthenticationException {
    public CustomerAuthenticationException(String message) {
        super(message);
    }
}
