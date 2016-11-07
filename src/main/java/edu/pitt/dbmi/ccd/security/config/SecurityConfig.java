package edu.pitt.dbmi.ccd.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import edu.pitt.dbmi.ccd.security.filter.CrossOriginRequestTokenFilter;
import edu.pitt.dbmi.ccd.security.userDetails.UserAccountDetailsService;

/**
 * Configures web security
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required=true)
    private UserAccountDetailsService userAccountDetailsService;

    @Autowired(required=true)
    private PasswordEncoder passwordEncoder;

    @Autowired(required=true)
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAccountDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // login
        http.formLogin()
            .and()
            .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        // csrf
        http.csrf()
                .csrfTokenRepository(csrfTokenRepository())
            .and()
            .addFilterAfter(new CrossOriginRequestTokenFilter(), CsrfFilter.class);

        // requests
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").anonymous();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/docs/**")
            .and().ignoring().antMatchers("/**/info/")
            .and().ignoring().antMatchers("/**/health/");
    }

    protected CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
