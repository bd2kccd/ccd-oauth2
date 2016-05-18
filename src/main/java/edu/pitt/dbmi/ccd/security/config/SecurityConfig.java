package edu.pitt.dbmi.ccd.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pitt.dbmi.ccd.security.userDetails.CustomUserDetailsService;

/**
 * Configures web security
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order(-1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required=true)
    private CustomUserDetailsService userDetailsService;

    @Autowired(required=true)
    private PasswordEncoder passwordEncoder;

    @Autowired(required=true)
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Order(-1)
    public void configure(HttpSecurity http) throws Exception {
        http
            // .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/api/oauth/**").anonymous()
            .and()
            .formLogin();
    }
}
