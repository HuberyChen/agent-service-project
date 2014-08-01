package com.core.platform.web.site.handler;

import com.core.http.HTTPConstants;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectRequestHandler implements HttpRequestHandler {
    private final String redirectURL;

    public RedirectRequestHandler(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HTTPConstants.SC_MOVED_TEMPORARILY);
        response.setHeader(HTTPConstants.HEADER_REDIRECT_LOCATION, redirectURL);
    }
}
