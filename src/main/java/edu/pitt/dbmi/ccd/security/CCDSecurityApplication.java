package edu.pitt.dbmi.ccd.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.ApplicationContext;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@SpringBootApplication
public class CCDSecurityApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(CCDSecurityApplication.class, args);
    }
}