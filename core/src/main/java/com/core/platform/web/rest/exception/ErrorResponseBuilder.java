package com.core.platform.web.rest.exception;


import com.core.platform.web.request.RequestContext;
import com.core.utils.ExceptionUtils;

import javax.inject.Inject;

public class ErrorResponseBuilder {
    private RequestContext requestContext;

    public ErrorResponse createErrorResponse(Throwable e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        error.setExceptionClass(e.getClass().getName());
        error.setRequestId(requestContext.getRequestId());
        error.setExceptionTrace(ExceptionUtils.stackTrace(e));
        return error;
    }

    @Inject
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }
}