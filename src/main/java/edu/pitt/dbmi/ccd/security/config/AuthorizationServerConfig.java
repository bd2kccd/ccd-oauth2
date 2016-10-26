package edu.pitt.dbmi.ccd.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import edu.pitt.dbmi.ccd.security.crypto.SHA256PasswordEncoder;
import edu.pitt.dbmi.ccd.security.userDetails.UserAccountDetailsService;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired(required=true)
    private DataSource dataSource;

    @Autowired(required=true)
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired(required=true)
    private UserAccountDetailsService userAccountDetailsService;

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
     * Create SHA-256 PasswordEncoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SHA256PasswordEncoder();
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
            .userDetailsService(userAccountDetailsService);
    }

    /**
     * Client store configuration
     *
     * @see org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        final int thirtyMinutes = 1800;     // 1,800 seconds
        final int oneHour = thirtyMinutes*2;
        final int twoHours = oneHour*2;
        final int oneDay = twoHours*12;
        final int oneWeek = oneDay*7;
        final int fourteenDays = 1209600;   // 1,209,600 seconds

        // clients stored in memory
        clients.inMemory()
            .withClient("curl")
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_USER", "ROLE_ADMIN")
                .scopes("read", "write")
                .accessTokenValiditySeconds(thirtyMinutes)
                .refreshTokenValiditySeconds(fourteenDays)
            .and()
            .withClient("causal-web")
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_USER")
                .scopes("read", "write")
                .accessTokenValiditySeconds(thirtyMinutes)
                .refreshTokenValiditySeconds(fourteenDays);

        // clients stored in database
        // clients.jdbc(dataSource)
        //     .passwordEncoder(passwordEncoder());
    }
}
