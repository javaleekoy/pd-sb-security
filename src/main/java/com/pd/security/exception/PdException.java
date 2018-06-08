package com.pd.security.exception;

/**
 * @author peramdy on 2018/6/6.
 *
 */
public class PdException extends Exception {

    public PdException() {
        super();
    }

    public PdException(String message) {
        super(message);
    }

    public PdException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdException(Throwable cause) {
        super(cause);
    }

    protected PdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
