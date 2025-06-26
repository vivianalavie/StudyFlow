package com.studyflow.auth;

import com.clerk.backend_api.helpers.security.AuthenticateRequest;
import com.clerk.backend_api.helpers.security.models.AuthenticateRequestOptions;
import com.clerk.backend_api.helpers.security.models.RequestState;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.*;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String CLERK_TOKEN;
    static {
        String token = null;
        try {
            token = io.github.cdimascio.dotenv.Dotenv.load().get("CLERK_SECRET_KEY");
        } catch (Exception e) {
            token = System.getenv("CLERK_SECRET_KEY");
        }
        CLERK_TOKEN = token;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, org.springframework.web.bind.support.WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        // Header extrahieren wie vorher
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

        RequestState state = AuthenticateRequest.authenticateRequest(
                headers,
                AuthenticateRequestOptions
                        .secretKey(CLERK_TOKEN)
                        .build()
        );

        if (!state.isSignedIn()) {
            throw new RuntimeException("Unauthorized");
        }

        return state.claims()
                .map(claims -> claims.get("sub"))
                .orElseThrow(() -> new RuntimeException("Token claims fehlen"));

    }
}
