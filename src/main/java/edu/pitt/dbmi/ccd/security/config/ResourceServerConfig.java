package edu.pitt.dbmi.ccd.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Configuration
@EnableResourceServer
@Profile("resourceServer")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * Security filter configuration
     */
    @Override
    public void configure(HttpSecurity http) throws Exception { }
}