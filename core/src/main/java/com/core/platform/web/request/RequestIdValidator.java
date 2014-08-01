package com.core.platform.web.request;

import com.core.platform.exception.InvalidRequestException;
import com.core.utils.StringUtils;

import java.util.regex.Pattern;

final class RequestIdValidator {
    static final Pattern PATTERN_REQUEST_ID = Pattern.compile("[a-zA-Z0-9\\-]+");
    static final int REQUEST_ID_MAX_LENGTH = 50;

    static void validateRequestId(String requestId) {
        if (!StringUtils.hasText(requestId)) return;
        if (requestId.length() > REQUEST_ID_MAX_LENGTH)
            throw new InvalidRequestException("the max length of requestId is " + REQUEST_ID_MAX_LENGTH);
        if (!PATTERN_REQUEST_ID.matcher(requestId).matches())
            throw new InvalidRequestException("the requestId must match " + PATTERN_REQUEST_ID.pattern());
    }
}
