package edu.pitt.dbmi.ccd.security.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import edu.pitt.dbmi.ccd.security.userDetails.CustomUserDetailsService;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * Number of rounds when performing BCrypt on passwords (Default is 10)
     */
    public static final int BCRYPT_ROUNDS = 10;

    @Autowired(required=true)
    private DataSource dataSource;

    @Autowired(required=true)
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired(required=true)
    private CustomUserDetailsService userDetailsService;

    /**
     * OAuth token store
     * 
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * Token configuration
     * 
     * @return DefaultTokenServices
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore());
        return tokenServices;
    }

    /**
     * Create {@value #BCRYPT_ROUNDS} round BCrypt password encoder
     * 
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_ROUNDS);
    }

    /**
     * Enables querying by current authenticated user (@AuthenticationPrincipal)
     * 
     * @return  SecurityEvaluationContextExtension
     * @see org.springframework.security.core.Authentication#getPrincipal()
     */
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    /**
     * Endpoints configuration
     * 
     * @see org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(tokenStore())
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
    }

    /**
     * Client store configuration
     * 
     * @see org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // clients stored in memory
        clients
            .inMemory()
                    .withClient("curl")
                        .authorizedGrantTypes("password", "refresh_token")
                        .authorities("ROLE_USER", "ROLE_ADMIN")
                        .scopes("read", "write");

        // clients stored in database
        // clients
        //     .jdbc(dataSource)
        //         .passwordEncoder(passwordEncoder());
    }
}