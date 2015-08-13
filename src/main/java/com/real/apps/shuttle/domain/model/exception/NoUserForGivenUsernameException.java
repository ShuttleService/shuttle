package com.real.apps.shuttle.domain.model.exception;

/**
 * Created by zorodzayi on 15/06/13.
 */
public class NoUserForGivenUsernameException extends RuntimeException {
    public NoUserForGivenUsernameException(String username) {
        super(username);
    }
}
