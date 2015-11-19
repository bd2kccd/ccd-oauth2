package edu.pitt.dbmi.ccd.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.context.ApplicationContext;
import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@SpringBootApplication
@Import({CCDDatabaseApplication.class})
@EnableResourceServer
public class CCDSecurityApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(CCDSecurityApplication.class, args);
    }
}