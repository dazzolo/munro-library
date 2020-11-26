package com.davidebelpanno.munrolibrary.exceptions;

public class NoMunrosFoundException extends RuntimeException {

    public NoMunrosFoundException() {
        super("No munros found with selected filters");
    }

}
