package com.marvelcomicsapi.service.exception;

public class DuplicatedEntryException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    public DuplicatedEntryException(String message){
        super(message);
    }

    public DuplicatedEntryException(String message, Throwable anException){
        super(message, anException);
    }
}

