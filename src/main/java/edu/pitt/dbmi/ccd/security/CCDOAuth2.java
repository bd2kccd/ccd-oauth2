package edu.pitt.dbmi.ccd.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;

/**
 * OAuth 2.0 package for CCD applications
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@SpringBootApplication
@Import({CCDDatabaseApplication.class})
@EnableResourceServer
public class CCDOAuth2 {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(CCDOAuth2.class, args);
    }
}
