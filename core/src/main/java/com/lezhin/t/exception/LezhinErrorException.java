package com.lezhin.t.exception;

public class LezhinErrorException extends RuntimeException {

    public LezhinErrorException() {
        super("에러");
    }

    public LezhinErrorException(String message) {
        super(message);
    }
}
