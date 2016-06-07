package edu.pitt.dbmi.ccd.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CrossOriginRequestFilter extends OncePerRequestFilter {

    private static final String[] methods = {
            "GET",
            "POST",
            "PUT",
            "PATCH",
            "DELETE",
            "OPTIONS"
    };

    private static final String[] headers = {
            "Accept",
            "Accept-Encoding",
            "Accept-Language",
            "Authorization",
            "Cache-Control",
            "Connection",
            "Content-Length",
            "Content-Type",
            "Cookie",
            "Host",
            "Origin",
            "Pragma",
            "Referer",
            "User-Agent",
            "x-requested-with",
            "X-XSRF-TOKEN"
    };

    private static final Long maxAge = 3600L;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String origin = request.getHeader("Origin");

        // set response headers
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", String.join(",", methods));
        response.setHeader("Access-Control-Allow-Headers", String.join(",", headers));
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Max-Age", Long.toString(maxAge));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (!request.getMethod().equals("OPTIONS"))
            chain.doFilter(request, response);
    }
}
