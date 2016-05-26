package edu.pitt.dbmi.ccd.security.config;

import static edu.pitt.dbmi.ccd.db.util.StringUtils.notNullOrEmpty;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

/**
*
* @author Mark Silvis (marksilvis@pitt.edu)
*/
public class CrossOriginRequestTokenFilter extends OncePerRequestFilter {

    // cookie name
    public static final String XSRF_TOKEN_COOKIE_NAME = "XSRF-TOKEN";

    // header name
    public static final String XSRF_TOKEN_HEADER_NAME = "X-XSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        if (notNullOrEmpty(csrf)) {
            String token = csrf.getToken();
            if (notNullOrEmpty(token)) {
                Cookie cookie = new Cookie(XSRF_TOKEN_COOKIE_NAME, token);
                cookie.setPath("/");
                response.addCookie(cookie);
                response.addHeader(XSRF_TOKEN_HEADER_NAME, token);
            }
        }
        chain.doFilter(request, response);
    }
}
