package com.lx.agent.exception;

/**
 * @version createTime：Apr 23, 2013 3:48:40 PM
 */
public class PermissionForbiddenException extends RuntimeException {

    /**
     * permission forbidden exception
     */
    private static final long serialVersionUID = 111112312123L;

    public PermissionForbiddenException(String message) {
        super(message);
    }

    public PermissionForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
