package com.pd.security.exception;

/**
 * @author peramdy on 2018/6/8.
 */
public class PdRuntimeException extends RuntimeException {

    public PdRuntimeException() {
        super();
    }

    public PdRuntimeException(String message) {
        super(message);
    }

    public PdRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdRuntimeException(Throwable cause) {
        super(cause);
    }

    protected PdRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
