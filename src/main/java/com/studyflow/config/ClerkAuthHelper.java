package com.studyflow.config;

import com.clerk.backend_api.helpers.security.AuthenticateRequest;
import com.clerk.backend_api.helpers.security.models.AuthenticateRequestOptions;
import com.clerk.backend_api.helpers.security.models.RequestState;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

public class ClerkAuthHelper {

    private static final String CLERK_TOKEN;
    static {
        String token = null;
        try {
            token = io.github.cdimascio.dotenv.Dotenv.load().get("CLERK_TOKEN");
        } catch (Exception e) {
            token = System.getenv("CLERK_TOKEN");
        }
        CLERK_TOKEN = token;
    }

    public static RequestState verifyRequest(HttpServletRequest request) {
        Map<String, List<String>> headers = extractHeaders(request);

        return AuthenticateRequest.authenticateRequest(
                headers,
                AuthenticateRequestOptions
                        .secretKey(CLERK_TOKEN)
                        .build()
        );
    }

    private static Map<String, List<String>> extractHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            Enumeration<String> values = request.getHeaders(name);

            List<String> valueList = new ArrayList<>();
            while (values.hasMoreElements()) {
                valueList.add(values.nextElement());
            }

            headers.put(name, valueList);
        }

        return headers;
    }
}
