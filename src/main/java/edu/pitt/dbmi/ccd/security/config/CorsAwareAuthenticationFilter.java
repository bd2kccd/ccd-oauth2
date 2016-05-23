package edu.pitt.dbmi.ccd.security.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public class CorsAwareAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    static final String ORIGIN = "Origin";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (request.getHeader(ORIGIN) != null) {
            String origin = request.getHeader(ORIGIN);
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Method", "GET, POST, PATCH, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        }
        if (request.getMethod() == "OPTIONS") {
            try {
                response.getWriter().print("OK");
                response.getWriter().flush();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.attemptAuthentication(request, response);
    }
}
