package com.core.platform.web.site.exception;

import com.core.platform.web.site.layout.ModelContext;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Single place to build model needed to render error page for web site only
 */
public class ErrorPageModelBuilder {
    static final String ERROR_MESSAGE_NULL_POINTER_EXCEPTION = "null pointer exception";
    private ModelContext modelContext;

    public Map<String, Object> buildErrorPageModel(Throwable exception) {
        return buildErrorPageModel(getErrorMessage(exception), exception);
    }

    String getErrorMessage(Throwable exception) {
        if (exception instanceof NullPointerException) return ERROR_MESSAGE_NULL_POINTER_EXCEPTION;
        return exception.getMessage();
    }

    public Map<String, Object> buildErrorPageModel(String errorMessage, Throwable exception) {
        Map<String, Object> model = new HashMap<>();

        model.put("exception", new ExceptionInfo(errorMessage, exception));

        modelContext.mergeToModel(model);
        return model;
    }

    @Inject
    public void setModelContext(ModelContext modelContext) {
        this.modelContext = modelContext;
    }
}