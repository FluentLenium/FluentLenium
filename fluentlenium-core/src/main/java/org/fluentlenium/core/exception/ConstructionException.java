package org.fluentlenium.core.exception;


public class ConstructionException extends RuntimeException {

    public ConstructionException(String s) {
        super(s);
    }

    public ConstructionException(String s, Throwable t) {
        super(s, t);
    }
}
